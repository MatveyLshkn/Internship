package lma.parser;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lma.client.PageAvByClient;
import lma.dto.BrandReadDto;
import lma.dto.ModelReadDto;
import lma.client.ApiAvByClient;
import lma.entity.Brand;
import lma.mapper.BrandMapper;
import lma.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.boot.Banner;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Parser {

    private final ApiAvByClient avByClient;

    private final PageAvByClient pageAvByClient;

    private final BrandRepository brandRepository;

    private final BrandMapper brandMapper;

    public List<BrandReadDto> getAllBrands() throws IOException {
        return avByClient.getBrands();
    }

    public List<ModelReadDto> getAllModelsByBrand(Long brandId) throws IOException {
        return avByClient.getModels(brandId);
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

    @Scheduled
    public void checkForCarUpdates() throws IOException {
        List<BrandReadDto> allBrandsFromSite = getAllBrands();

        List<Long> brandIdsFromDatabase = brandRepository.findAll()
                .stream()
                .map(brand -> brand.getId())
                .toList();

        allBrandsFromSite.removeIf(
                brand -> brandIdsFromDatabase.contains(brand.id())
        );

        if (!allBrandsFromSite.isEmpty()) {
            for (BrandReadDto brand : allBrandsFromSite) {
                Brand brandEntity = brandMapper.map(brand);
                brandRepository.save(brandEntity);
            }
        }
    }

}
