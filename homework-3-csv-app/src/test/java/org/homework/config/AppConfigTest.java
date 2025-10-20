package org.homework.config;

import org.homework.dao.QuestionReader;
import org.homework.service.QuestionPrinter;
import org.homework.service.TestingService;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class AppConfigTest {

    @Test
    void contextLoadsAndBeansPresent() {
        try (var ctx = new AnnotationConfigApplicationContext(AppConfig.class)) {
            assertThat(ctx.getBean(QuestionReader.class)).isNotNull();
            assertThat(ctx.getBean(QuestionPrinter.class)).isNotNull();
            assertThat(ctx.getBean(TestingService.class)).isNotNull();
        }
    }
}
