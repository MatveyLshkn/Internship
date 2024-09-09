package lma.service;

import com.fasterxml.jackson.databind.JsonNode;
import feign.Response;
import jakarta.annotation.PostConstruct;
import lma.client.ApiAvByClient;
import lma.client.PageAvByClient;
import lma.dto.BrandDto;
import lma.dto.ModelDto;
import lma.dto.PostDto;
import lma.entity.Brand;
import lma.entity.Model;
import lma.entity.ModelCheck;
import lma.entity.Post;
import lma.mapper.BrandMapper;
import lma.mapper.ModelMapper;
import lma.mapper.PostMapper;
import lma.parser.JsonParser;
import lma.parser.PageParser;
import lma.repository.BrandRepository;
import lma.repository.ModelCheckRepository;
import lma.repository.ModelRepository;
import lma.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import static lma.constants.CommonConstants.CAR_UPDATE_CRON_EXPRESSION;
import static lma.constants.CommonConstants.CHECK_FOR_NEW_POSTS_RATE;
import static lma.constants.CommonConstants.INITIAL_CAR_UPDATE_DELAY;
import static lma.constants.CommonConstants.OUTDATED_POSTS_CRON_EXPRESSION;
import static lma.constants.CommonConstants.POST_KAFKA_TOPIC_NAME;

@Service
@RequiredArgsConstructor
public class ScheduledService {

    private final ApiAvByClient apiAvByClient;

    private final PageAvByClient pageAvByClient;

    private final BrandRepository brandRepository;

    private final ModelRepository modelRepository;

    private final PostRepository postRepository;

    private final ModelCheckRepository modelCheckRepository;

    private final KafkaProducerService kafkaProducerService;

    private final BrandMapper brandMapper;

    private final ModelMapper modelMapper;

    private final PostMapper postMapper;

    private final PageParser pageParser;

    private final JsonParser jsonParser;


    @Scheduled(cron = CAR_UPDATE_CRON_EXPRESSION)
    public void updateCarAndModelList() throws IOException {
        List<BrandDto> allBrandsFromSite = apiAvByClient.getBrands();

        List<Long> brandIdsFromDatabase = brandRepository.findAllBrandsIds();

        for (BrandDto brand : allBrandsFromSite) {
            if (!brandIdsFromDatabase.contains(brand.id())) {
                Brand brandEntity = brandMapper.map(brand);
                brandRepository.save(brandEntity);
            }

            List<ModelDto> models = apiAvByClient.getModels(brand.id());
            List<Long> modelIdsFromDatabase = modelRepository.findAllModelIdsByBrandId(brand.id());

            for (ModelDto model : models) {
                if (!modelIdsFromDatabase.contains(model.id())) {
                    Model modelEntity = modelMapper.map(model, brand);
                    modelRepository.save(modelEntity);
                }
            }
        }
    }


    @Scheduled(cron = OUTDATED_POSTS_CRON_EXPRESSION)
    public void removeOutdatedPosts() {
        List<Post> allPosts = postRepository.findAll();
        allPosts.forEach(this::checkPost);
    }


    private void checkPost(Post post) {
        Response postInfo = apiAvByClient.getPostInfo(post.getId());
        int status = postInfo.status();

        if (HttpStatus.resolve(status).is4xxClientError()) {
            postRepository.delete(post);
        }
    }


    @Scheduled(fixedRate = CHECK_FOR_NEW_POSTS_RATE)
    public void checkForNewPosts() throws IOException {
        List<Model> models = modelRepository.findAllSubscribed();

        for (Model model : models) {
            Long modelId = model.getId();
            Long brandId = model.getBrand().getId();

            ModelCheck modelCheck = modelCheckRepository.findByModelId(modelId);
            LocalDateTime lastCheck;

            if (modelCheck != null) {
                lastCheck = modelCheck.getCheckDate().toLocalDateTime();
            } else {
                lastCheck = LocalDateTime.MIN;
            }

            boolean needToStop = false;
            Long pageCount = pageParser.getPageCount(brandId, modelId);

            for (int i = 1; i <= pageCount; i++) {

                String page = pageAvByClient.getCarPageSortedAsc(brandId, modelId, Long.valueOf(i));
                String json = pageParser.getJsonFromPage(page);
                JsonNode adverts = jsonParser.getAdvertsJsonNode(json);

                for (JsonNode advert : adverts) {

                    LocalDateTime postDate = jsonParser.getZonedDateTimeFromAdvertJsonNode(advert);

                    if (postDate.isBefore(lastCheck)) {
                        needToStop = true;
                        break;
                    } else {
                        Post post = jsonParser.parsePostFromAdvertJsonNode(advert, model);
                        PostDto postDto = postMapper.map(post);

                        postRepository.save(post);

                        kafkaProducerService.sendMessage(postDto, POST_KAFKA_TOPIC_NAME);
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


    @PostConstruct
    public void initCarBrandsAndModels() throws IOException {
        updateCarAndModelList();
    }
}
