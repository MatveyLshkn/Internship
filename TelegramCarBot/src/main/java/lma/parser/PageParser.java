package lma.parser;


import com.fasterxml.jackson.databind.JsonNode;
import feign.Response;
import lma.client.PageAvByClient;
import lma.dto.BrandReadDto;
import lma.dto.ModelReadDto;
import lma.client.ApiAvByClient;
import lma.dto.PostReadDto;
import lma.entity.Brand;
import lma.entity.Model;
import lma.entity.ModelCheck;
import lma.entity.Post;
import lma.mapper.BrandMapper;
import lma.mapper.ModelMapper;
import lma.mapper.PostMapper;
import lma.repository.BrandRepository;
import lma.repository.ModelCheckRepository;
import lma.repository.ModelRepository;
import lma.repository.PostRepository;
import lma.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static lma.constants.CommonConstants.AV_BY_JSON_TAG;
import static lma.constants.CommonConstants.CAR_UPDATE_CRON_EXPRESSION;
import static lma.constants.CommonConstants.CHECK_FOR_NEW_POSTS_RATE;
import static lma.constants.CommonConstants.DATE_TIME_FORMAT;
import static lma.constants.CommonConstants.INITIAL_CAR_UPDATE_DELAY;
import static lma.constants.CommonConstants.OUTDATED_POSTS_CRON_EXPRESSION;
import static lma.constants.JsonConstants.PUBLISHED_AT_JSON_NODE_NAME;

@Component
@RequiredArgsConstructor
public class PageParser {

    private final PostMapper postMapper;

    private final JsonParser jsonParser;

    private final BrandMapper brandMapper;

    private final ModelMapper modelMapper;

    private final ApiAvByClient apiAvByClient;

    private final PostRepository postRepository;

    private final PageAvByClient pageAvByClient;

    private final BrandRepository brandRepository;

    private final ModelRepository modelRepository;

    private final ModelCheckRepository modelCheckRepository;

    private final KafkaProducerService kafkaProducerService;

    public String getJsonFromPage(String page) throws IOException {

        Document document = Jsoup.parse(page);
        Element element = document.getElementById(AV_BY_JSON_TAG);
        return element.html();
    }

    public Long getPageCount(Long brandId, Long modelId) throws IOException {

        String brandPage = pageAvByClient.getCarPageSortedAsc(brandId, modelId, 1L);
        String json = getJsonFromPage(brandPage);
        return jsonParser.parsePageCount(json);
    }

    public List<String> getAllPostsLinksByModel(Long brandId, Long modelId) throws IOException {

        Long pageCount = getPageCount(brandId, modelId);
        List<String> urls = new ArrayList<>();

        for (int i = 1; i <= pageCount; i++) {

            String page = pageAvByClient.getCarPageSortedAsc(brandId, modelId, Long.valueOf(i));
            String json = getJsonFromPage(page);
            urls.addAll(jsonParser.parseUrls(json));
        }

        return urls;
    }

    @Scheduled(initialDelay = INITIAL_CAR_UPDATE_DELAY, cron = CAR_UPDATE_CRON_EXPRESSION)
    public void checkForCarUpdates() throws IOException {
        List<BrandReadDto> allBrandsFromSite = apiAvByClient.getBrands();

        List<Long> brandIdsFromDatabase = brandRepository.findAllBrandIds();

        for (BrandReadDto brand : allBrandsFromSite) {
            if (!brandIdsFromDatabase.contains(brand.id())) {
                Brand brandEntity = brandMapper.map(brand);
                brandRepository.save(brandEntity);
            }

            List<ModelReadDto> models = apiAvByClient.getModels(brand.id());
            List<Long> modelIdsFromDatabase = modelRepository.findAllByBrand_Id(brand.id())
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

    @Scheduled(cron = OUTDATED_POSTS_CRON_EXPRESSION)
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


    @Scheduled(fixedRate = CHECK_FOR_NEW_POSTS_RATE)
    public void checkForNewPosts() throws IOException {
        List<Model> models = modelRepository.findAll();

        for (Model model : models) {

            Long modelId = model.getId();
            Long brandId = model.getBrand().getId();

            ModelCheck modelCheck = modelCheckRepository.findByModelId(modelId);
            ZonedDateTime lastCheck;

            if (modelCheck != null) {
                lastCheck = ZonedDateTime.from(modelCheck.getCheckDate().toLocalDateTime());
            } else {
                lastCheck = ZonedDateTime.from(LocalDateTime.MIN);
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
            boolean needToStop = false;
            Long pageCount = getPageCount(brandId, modelId);

            for (int i = 1; i <= pageCount; i++) {

                String page = pageAvByClient.getCarPageSortedAsc(brandId, modelId, Long.valueOf(i));
                String json = getJsonFromPage(page);
                JsonNode adverts = jsonParser.getAdvertsJsonNode(json);

                for (JsonNode advert : adverts) {
                    JsonNode publishedAt = advert.path(PUBLISHED_AT_JSON_NODE_NAME);

                    ZonedDateTime zonedDateTime = ZonedDateTime.parse(publishedAt.asText(), formatter);

                    if (zonedDateTime.isBefore(lastCheck)) {
                        needToStop = true;
                        break;
                    } else {
                        Post post = jsonParser.parsePostFromAdvertJsonNode(advert, model);
                        PostReadDto postReadDto = postMapper.map(post);

                        postRepository.save(post);

                        kafkaProducerService.sendMessage(postReadDto);
                    }
                }
                if (needToStop) {
                    break;
                }
            }

            if (modelCheck != null) {
                modelCheck.setCheckDate(Timestamp.valueOf(LocalDateTime.now()));
            } else {
                modelCheckRepository.save(ModelCheck.builder()
                        .modelId(modelId)
                        .checkDate(Timestamp.valueOf(LocalDateTime.now()))
                        .build());
            }
        }
    }
}