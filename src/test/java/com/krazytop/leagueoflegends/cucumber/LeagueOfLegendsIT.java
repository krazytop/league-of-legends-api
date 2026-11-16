package com.krazytop.leagueoflegends.cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krazytop.leagueoflegends.cucumber.config.MongoCollectionRegistry;
import io.cucumber.spring.CucumberContextConfiguration;
import no.stelar7.api.r4j.impl.R4J;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class LeagueOfLegendsIT {

    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8.0");

    static {
        mongoDBContainer.start();
    }

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Autowired
    protected MongoCollectionRegistry collectionRegistry;

    @Autowired
    @MockitoBean(answers = Answers.RETURNS_DEEP_STUBS)
    protected R4J r4j;
}