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
import org.springframework.http.HttpStatus;
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