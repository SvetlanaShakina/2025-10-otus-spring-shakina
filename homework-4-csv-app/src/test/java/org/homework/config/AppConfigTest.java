package org.homework.config;

import org.homework.dao.QuestionReader;
import org.homework.service.QuestionPrinter;
import org.homework.service.TestingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class AppConfigTest {

    @Autowired
    ApplicationContext ctx;

    @Test
    void beansPresent() {
        assertThat(ctx.getBean(QuestionReader.class)).isNotNull();
        assertThat(ctx.getBean(QuestionPrinter.class)).isNotNull();
        assertThat(ctx.getBean(TestingService.class)).isNotNull();
    }
}
