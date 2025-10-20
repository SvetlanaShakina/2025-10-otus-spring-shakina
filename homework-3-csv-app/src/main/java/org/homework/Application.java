package org.homework;

import org.homework.config.AppConfig;
import org.homework.service.TestingService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        try (var ctx = new AnnotationConfigApplicationContext(AppConfig.class)) {
            ctx.getBean(TestingService.class).runTest();
        }
    }
}