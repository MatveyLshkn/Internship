package lma.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lma.constants.CommonConstants;
import lma.constants.JsonConstants;
import lma.dto.PostReadDto;
import lma.entity.Model;
import lma.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static lma.constants.CommonConstants.POST_INFO_FORMAT;
import static lma.constants.JsonConstants.ADVERTS_JSON_NODE_NAME;
import static lma.constants.JsonConstants.AMOUNT_JSON_NODE_NAME;
import static lma.constants.JsonConstants.DESCRIPTION_JSON_NODE_NAME;
import static lma.constants.JsonConstants.FILTER_JSON_NODE_NAME;
import static lma.constants.JsonConstants.ID_JSON_NODE_NAME;
import static lma.constants.JsonConstants.INITIAL_STATE_JSON_NODE_NAME;
import static lma.constants.JsonConstants.MAIN_JSON_NODE_NAME;
import static lma.constants.JsonConstants.PAGE_COUNT_JSON_NODE_NAME;
import static lma.constants.JsonConstants.PRICE_JSON_NODE_NAME;
import static lma.constants.JsonConstants.PROPS_JSON_NODE_NAME;
import static lma.constants.JsonConstants.PUBLIC_URL_JSON_NODE_NAME;
import static lma.constants.JsonConstants.USD_JSON_NODE_NAME;

@Component
@RequiredArgsConstructor
public class JsonParser {

    private final ObjectMapper jsonMapper;

    public Long parsePageCount(String json) throws JsonProcessingException {
        JsonNode rootNode = jsonMapper.readTree(json);

        return rootNode.path(PROPS_JSON_NODE_NAME)
                .path(INITIAL_STATE_JSON_NODE_NAME)
                .path(FILTER_JSON_NODE_NAME)
                .path(MAIN_JSON_NODE_NAME)
                .path(PAGE_COUNT_JSON_NODE_NAME).asLong();
    }

    public List<String> parseUrls(String json) throws JsonProcessingException {
        JsonNode rootNode = jsonMapper.readTree(json);

        JsonNode adverts = getAdvertsJsonNode(json);

        List<String> urls = new ArrayList<>();

        for (JsonNode advert : adverts) {
            JsonNode url = advert.path(PUBLIC_URL_JSON_NODE_NAME);
            urls.add(url.asText());
        }

        return urls;
    }

    public JsonNode getAdvertsJsonNode(String json) throws JsonProcessingException {
        JsonNode rootNode = jsonMapper.readTree(json);

        return rootNode.path(PROPS_JSON_NODE_NAME)
                .path(INITIAL_STATE_JSON_NODE_NAME)
                .path(FILTER_JSON_NODE_NAME)
                .path(MAIN_JSON_NODE_NAME)
                .path(ADVERTS_JSON_NODE_NAME);
    }

    public Post parsePostFromAdvertJsonNode(JsonNode advert, Model model) {
        return Post.builder()
                .id(advert.path(ID_JSON_NODE_NAME).asLong())
                .model(model)
                .info(POST_INFO_FORMAT.formatted(getUsdPriceFromAdvertJsonNode(advert),
                        advert.path(DESCRIPTION_JSON_NODE_NAME).asText()))
                .url(advert.path(PUBLIC_URL_JSON_NODE_NAME).asText())
                .build();
    }

    public Double getUsdPriceFromAdvertJsonNode(JsonNode advert){
        return advert.path(PRICE_JSON_NODE_NAME)
                .path(USD_JSON_NODE_NAME)
                .path(AMOUNT_JSON_NODE_NAME)
                .asDouble();
    }
}
