package com.krazytop.leagueoflegends.cucumber.steps;

import com.krazytop.leagueoflegends.cucumber.LeagueOfLegendsIT;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Quand;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HttpApiStep extends LeagueOfLegendsIT {

    private ResultActions lastResponse;

    @Quand("j'appelle GET {string}")
    public void callGet(String url) throws Exception {
        lastResponse = mockMvc.perform(get(url));
    }

    @Quand("j'appelle GET {string} avec le header {string} égal à {string}")
    public void callGetWithHeader(String url, String headerName, String headerValue) throws Exception {
        lastResponse = mockMvc.perform(get(url).header(headerName, headerValue));
    }

    @Quand("j'appelle PUT {string}")
    public void callPut(String url) throws Exception {
        lastResponse = mockMvc.perform(put(url));
    }

    @Quand("j'appelle POST {string}")
    public void callPost(String url) throws Exception {
        lastResponse = mockMvc.perform(post(url));
    }

    @Alors("le statut HTTP est {int}")
    public void verifyStatus(int expectedStatus) throws Exception {
        lastResponse.andExpect(status().is(expectedStatus));
    }

    @Alors("la réponse JSON est :")
    public void verifyJsonResponse(String expectedJson) throws Exception {
        expectedJson = cucumberContext.resolveDynamicData(expectedJson);
        lastResponse.andExpect(content().json(expectedJson, JsonCompareMode.STRICT));
    }
}