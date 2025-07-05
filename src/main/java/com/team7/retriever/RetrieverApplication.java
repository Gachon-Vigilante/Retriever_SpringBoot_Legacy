package com.team7.retriever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // Scheduler 활성화
@EnableMongoAuditing
@SpringBootApplication
public class RetrieverApplication {

    public static void main(String[] args) {
        SpringApplication.run(RetrieverApplication.class, args);
    }

}
