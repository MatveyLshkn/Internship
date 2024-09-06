package lma.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lma.TestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

import static lma.constants.TestConstants.ACCESS_TOKEN_JSON_PATH_KEYWORD;
import static lma.constants.TestConstants.LOGIN_JSON_BODY;
import static lma.constants.TestConstants.REFRESH_JSON_BODY;
import static lma.constants.TestConstants.REFRESH_TOKEN_JSON_NODE_KEYWORD;
import static lma.constants.TestConstants.REFRESH_TOKEN_JSON_PATH_KEYWORD;
import static lma.constants.TestConstants.REGISTER_FOR_LOGIN_JSON_BODY;
import static lma.constants.TestConstants.REGISTER_FOR_REFRESH_TOKEN_JSON_BODY;
import static lma.constants.TestConstants.REGISTER_JSON_BODY;
import static lma.constants.CommonConstants.LOGIN_ENDPOINT;
import static lma.constants.CommonConstants.REFRESH_ENDPOINT;
import static lma.constants.CommonConstants.REGISTER_ENDPOINT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AuthControllerTest extends TestBase {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register() throws Exception {

        mockMvc.perform(
                        post(REGISTER_ENDPOINT)
                                .contentType(APPLICATION_JSON)
                                .content(REGISTER_JSON_BODY)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath(ACCESS_TOKEN_JSON_PATH_KEYWORD).exists())
                .andExpect(jsonPath(REFRESH_TOKEN_JSON_PATH_KEYWORD).exists());
    }

    @Test
    void login() throws Exception {

        mockMvc.perform(
                        post(REGISTER_ENDPOINT)
                                .contentType(APPLICATION_JSON)
                                .content(REGISTER_FOR_LOGIN_JSON_BODY)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath(ACCESS_TOKEN_JSON_PATH_KEYWORD).exists())
                .andExpect(jsonPath(REFRESH_TOKEN_JSON_PATH_KEYWORD).exists());

        mockMvc.perform(
                        post(LOGIN_ENDPOINT)
                                .contentType(APPLICATION_JSON)
                                .content(LOGIN_JSON_BODY)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath(ACCESS_TOKEN_JSON_PATH_KEYWORD).exists())
                .andExpect(jsonPath(REFRESH_TOKEN_JSON_PATH_KEYWORD).exists());
    }

    @Test
    void refreshToken() throws Exception {

        MvcResult registerResult = mockMvc.perform(
                        post(REGISTER_ENDPOINT)
                                .contentType(APPLICATION_JSON)
                                .content(REGISTER_FOR_REFRESH_TOKEN_JSON_BODY)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath(ACCESS_TOKEN_JSON_PATH_KEYWORD).exists())
                .andExpect(jsonPath(REFRESH_TOKEN_JSON_PATH_KEYWORD).exists())
                .andReturn();

        String registerResponseContent = registerResult.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(registerResponseContent);
        String refreshToken = jsonNode.get(REFRESH_TOKEN_JSON_NODE_KEYWORD).asText();

        mockMvc.perform(
                        post(REFRESH_ENDPOINT)
                                .contentType(APPLICATION_JSON)
                                .content(REFRESH_JSON_BODY.formatted(refreshToken))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath(ACCESS_TOKEN_JSON_PATH_KEYWORD).exists())
                .andExpect(jsonPath(REFRESH_TOKEN_JSON_PATH_KEYWORD).exists());
    }
}