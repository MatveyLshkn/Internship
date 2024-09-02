package lma.parser;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import lma.client.PageAvByClient;
import lma.dto.BrandReadDto;
import lma.dto.ModelReadDto;
import lma.client.ApiAvByClient;
import lma.entity.Brand;
import lma.entity.Model;
import lma.entity.ModelCheck;
import lma.entity.Post;
import lma.mapper.BrandMapper;
import lma.mapper.ModelMapper;
import lma.repository.BrandRepository;
import lma.repository.ModelCheckRepository;
import lma.repository.ModelRepository;
import lma.repository.PostRepository;
import lma.service.KafkaConsumerService;
import lma.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Parser {

    private final ApiAvByClient apiAvByClient;

    private final PageAvByClient pageAvByClient;

    private final BrandRepository brandRepository;

    private final ModelRepository modelRepository;

    private final ModelCheckRepository modelCheckRepository;

    private final KafkaProducerService kafkaProducerService;

    private final BrandMapper brandMapper;

    private final ModelMapper modelMapper;

    private final PostRepository postRepository;

    public List<BrandReadDto> getAllBrands() throws IOException {
        return apiAvByClient.getBrands();
    }

    public List<ModelReadDto> getAllModelsByBrand(Long brandId) throws IOException {
        return apiAvByClient.getModels(brandId);
    }

    public Long getPageCount(Long brandId, Long modelId) throws IOException {

        String brandPage = pageAvByClient.getCarPageSortedAsc(brandId, modelId, 1L);
        Document document = Jsoup.parse(brandPage);
        Element element = document.getElementById("__NEXT_DATA__");

        String json = element.html();
        ObjectMapper jsonMapper = new ObjectMapper();
        JsonNode rootNode = jsonMapper.readTree(json);

        Long pageCount = rootNode.path("props")
                .path("initialState")
                .path("filter")
                .path("main")
                .path("pageCount").asLong();

        return pageCount;
    }

    public List<String> getAllPostsLinksByModel(Long brandId, Long modelId) throws IOException {

        Long pageCount = getPageCount(brandId, modelId);
        List<String> urls = new ArrayList<>();

        for (int i = 1; i <= pageCount; i++) {

            String page = pageAvByClient.getCarPageSortedAsc(brandId, modelId, Long.valueOf(i));
            Document document = Jsoup.parse(page);
            Element element = document.getElementById("__NEXT_DATA__");

            String json = element.html();
            ObjectMapper jsonMapper = new ObjectMapper();
            JsonNode rootNode = jsonMapper.readTree(json);

            JsonNode adverts = rootNode.path("props")
                    .path("initialState")
                    .path("filter")
                    .path("main")
                    .path("adverts");

            for (JsonNode advert : adverts) {
                JsonNode url = advert.path("publicUrl"); //TODO id
                urls.add(url.asText());
            }
        }

        return urls;
    }

    @Scheduled(initialDelay = 1000, cron = "0 0 0 * * SUN")
    public void checkForCarUpdates() throws IOException {
        List<BrandReadDto> allBrandsFromSite = getAllBrands();

        List<Long> brandIdsFromDatabase = brandRepository.findAll()
                .stream()
                .map(brand -> brand.getId())
                .toList();

        for (BrandReadDto brand : allBrandsFromSite) {
            if (!brandIdsFromDatabase.contains(brand.id())) {
                Brand brandEntity = brandMapper.map(brand);
                brandRepository.save(brandEntity);
            }

            List<ModelReadDto> models = getAllModelsByBrand(brand.id());
            List<Long> modelIdsFromDatabase = modelRepository.findAllByBrand_Name(brand.name())
                    .stream()
                    .map(model -> model.getId())
                    .toList();

            for (ModelReadDto model : models) {
                if (!modelIdsFromDatabase.contains(model.id())) {
                    Model modelEntity = modelMapper.map(model, brand);
                    modelRepository.save(modelEntity);
                }
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void checkForOutdatedPosts() {
        List<Post> allPosts = postRepository.findAll();

        for (Post post : allPosts) {
            Response postInfo = apiAvByClient.getPostInfo(post.getId());
            int status = postInfo.status();

            if (status >= 400 && status < 500) {
                postRepository.delete(post);
            }
        }
    }


    @Scheduled(fixedRate = 1000 * 60 * 60 * 3)
    public void checkForNewPostsByModel(Model model) throws IOException {

        Long modelId = model.getId();
        Long brandId = model.getBrand().getId();

        ModelCheck modelCheck = modelCheckRepository.findByModelId(modelId);
        ZonedDateTime lastCheck;

        if (modelCheck != null) {
            lastCheck = ZonedDateTime.from(modelCheck.getCheckDate().toLocalDateTime());
        } else {
            lastCheck = ZonedDateTime.from(LocalDateTime.MIN);
        }

        Long pageCount = getPageCount(brandId, modelId);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

        boolean needToStop = false;

        for (int i = 1; i <= pageCount; i++) {

            String page = pageAvByClient.getCarPageSortedAsc(brandId, modelId, Long.valueOf(i));
            Document document = Jsoup.parse(page);
            Element element = document.getElementById("__NEXT_DATA__");

            String json = element.html();
            ObjectMapper jsonMapper = new ObjectMapper();
            JsonNode rootNode = jsonMapper.readTree(json);

            JsonNode adverts = rootNode.path("props")
                    .path("initialState")
                    .path("filter")
                    .path("main")
                    .path("adverts");


            for (JsonNode advert : adverts) {
                JsonNode publishedAt = advert.path("publishedAt"); //TODO id

                ZonedDateTime zonedDateTime = ZonedDateTime.parse(publishedAt.asText(), formatter);

                if (zonedDateTime.isBefore(lastCheck)) {
                    needToStop = true;
                    break;
                } else {
                    Post post = Post.builder()
                            .id(advert.path("id").asLong())
                            .model(model)
                            .info(advert.path("price")
                                          .path("usd")
                                          .path("amount")
                                          .asDouble()
                                  + " " + advert.path("description").asText())
                            .url(advert.path("publicUrl").asText())
                            .build();

                    kafkaProducerService.sendMessage(advert.path("publicUrl").asText());
                }
            }
            if (needToStop) {
                break;
            }
        }

        modelCheck.setCheckDate(Timestamp.valueOf(LocalDateTime.now()));

    }
}
