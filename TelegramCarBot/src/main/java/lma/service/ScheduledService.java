package lma.service;

import jakarta.annotation.PostConstruct;
import lma.client.ApiAvByClient;
import lma.client.PageAvByClient;
import lma.dto.BrandDto;
import lma.dto.ModelDto;
import lma.entity.Brand;
import lma.entity.Model;
import lma.entity.ModelCheck;
import lma.entity.Post;
import lma.mapper.BrandMapper;
import lma.mapper.ModelMapper;
import lma.mapper.PostMapper;
import lma.parser.JsonParser;
import lma.parser.PageParser;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static lma.constants.CommonConstants.CAR_UPDATE_CRON_EXPRESSION;
import static lma.constants.CommonConstants.CHECK_FOR_NEW_POSTS_RATE;
import static lma.constants.CommonConstants.OUTDATED_POSTS_CRON_EXPRESSION;
import static lma.constants.CommonConstants.THREAD_POLL_TASK_EXECUTOR_NAME;

@Service
@RequiredArgsConstructor
public class ScheduledService {

    private final ApiAvByClient apiAvByClient;

    private final PageAvByClient pageAvByClient;

    private final BrandService brandService;

    private final ModelService modelService;

    private final PostService postService;

    private final ModelCheckService modelCheckService;

    private final BrandMapper brandMapper;

    private final ModelMapper modelMapper;

    private final PostMapper postMapper;

    private final PageParser pageParser;

    private final JsonParser jsonParser;


    @Async(THREAD_POLL_TASK_EXECUTOR_NAME)
    @Scheduled(cron = CAR_UPDATE_CRON_EXPRESSION)
    public void updateCarAndModelList() throws IOException {
        List<BrandDto> allBrandsFromSite = apiAvByClient.getBrands();

        List<Long> brandIdsFromDatabase = brandService.findAllBrandsIds();

        for (BrandDto brand : allBrandsFromSite) {
            if (!brandIdsFromDatabase.contains(brand.id())) {
                Brand brandEntity = brandMapper.map(brand);
                brandService.save(brandEntity);
            }

            List<ModelDto> models = apiAvByClient.getModels(brand.id());
            List<Long> modelIdsFromDatabase = modelService.findAllModelsIdsByBrandId(brand.id());

            for (ModelDto model : models) {
                if (!modelIdsFromDatabase.contains(model.id())) {
                    Model modelEntity = modelMapper.map(model, brand);
                    modelService.save(modelEntity);
                }
            }
        }
    }


    @Async(THREAD_POLL_TASK_EXECUTOR_NAME)
    @Scheduled(cron = OUTDATED_POSTS_CRON_EXPRESSION)
    public void removeOutdatedPosts() {
        List<Post> allPosts = postService.findAll();
        allPosts.forEach(postService::removePostIfOutdated);
    }


    @Async(THREAD_POLL_TASK_EXECUTOR_NAME)
    @Scheduled(fixedRate = CHECK_FOR_NEW_POSTS_RATE)
    public void checkForNewPosts() throws IOException {
        List<Model> models = modelService.findAllSubscribedModels();

        for (Model model : models) {
            checkForNewPostsByModel(model);
        }
    }


    private void checkForNewPostsByModel(Model model) throws IOException {
        Long modelId = model.getId();
        Long brandId = model.getBrand().getId();

        ModelCheck modelCheck = modelCheckService.findByModelId(modelId);

        LocalDateTime lastCheck;
        if (modelCheck != null) {
            lastCheck = modelCheck.getCheckDate().toLocalDateTime();
        } else {
            lastCheck = LocalDateTime.MIN;
        }

        Long pageCount = pageParser.getPageCount(brandId, modelId);

        for (int i = 1; i <= pageCount; i++) {
            if (pageParser.processPage(i, lastCheck, model)) {
                break;
            }
        }

        modelCheckService.updateModelCheckDate(modelCheck, modelId);
    }


    @PostConstruct
    public void initCarBrandsAndModels() throws IOException {
        updateCarAndModelList();
    }
}
