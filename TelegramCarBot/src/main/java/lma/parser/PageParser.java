package lma.parser;

import lma.client.PageAvByClient;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static lma.constants.CommonConstants.AV_BY_JSON_TAG;


@Component
@RequiredArgsConstructor
public class PageParser {

    private final JsonParser jsonParser;

    private final PageAvByClient pageAvByClient;

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
}