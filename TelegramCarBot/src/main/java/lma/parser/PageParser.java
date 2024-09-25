package lma.parser;

import com.fasterxml.jackson.databind.JsonNode;
import lma.client.PageAvByClient;
import lma.dto.PostDto;
import lma.entity.Model;
import lma.entity.Post;
import lma.mapper.PostMapper;
import lma.service.KafkaProducerService;
import lma.service.PostService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lma.constants.CommonConstants.AV_BY_JSON_TAG;
import static lma.constants.CommonConstants.POST_KAFKA_TOPIC_NAME;


@Component
@RequiredArgsConstructor
public class PageParser {

    private final JsonParser jsonParser;

    private final PageAvByClient pageAvByClient;

    private final PostMapper postMapper;

    private final PostService postService;

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

    public boolean processPage(int pageNum, LocalDateTime lastCheck, Model model) throws IOException {

        String page = pageAvByClient.getCarPageSortedAsc(model.getBrand().getId(), model.getId(), (long) pageNum);
        String json = getJsonFromPage(page);
        JsonNode adverts = jsonParser.getAdvertsJsonNode(json);

        List<JsonNode> validAdverts = new ArrayList<>();

        for (JsonNode advert : adverts) {
            LocalDateTime postDate = jsonParser.getLocalDateTimeFromAdvertJsonNode(advert);
            if (postDate.isAfter(lastCheck)) {
                validAdverts.add(advert);
            }
        }

        if (validAdverts.isEmpty()) {
            return true;
        }

        for (JsonNode advert : validAdverts) {
            processAdvert(advert, model);
        }
        return false;
    }

    private void processAdvert(JsonNode advert, Model model) {
        Post post = jsonParser.parsePostFromAdvertJsonNode(advert, model);
        PostDto postDto = postMapper.map(post);

        postService.save(post);
        kafkaProducerService.sendMessage(postDto, POST_KAFKA_TOPIC_NAME);
    }
}