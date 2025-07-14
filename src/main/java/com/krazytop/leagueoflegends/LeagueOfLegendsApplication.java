package com.krazytop.leagueoflegends;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@ConfigurationPropertiesScan
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableMongoRepositories(basePackages = {"com.krazytop.leagueoflegends.repository"})
public class LeagueOfLegendsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeagueOfLegendsApplication.class, args);
    }

}
