package org.homework;

import org.homework.service.TestingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @ConditionalOnProperty(
            name = "quiz.autorun",
            havingValue = "true",
            matchIfMissing = false
    )
    CommandLineRunner run(TestingService testing) {
        return args -> testing.runTest();
    }
}