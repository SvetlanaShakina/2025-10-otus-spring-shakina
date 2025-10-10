package org.homework.config;

import org.homework.dao.QuestionReader;
import org.homework.dao.QuestionReaderImpl;
import org.homework.service.QuestionPrinter;
import org.homework.service.QuestionPrinterImpl;
import org.homework.service.TestingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = "org.homework")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public QuestionReader questionReader(@Value("${quiz.csv-path}") String csvPath) {
        return new QuestionReaderImpl(csvPath);
    }

    @Bean
    public QuestionPrinter questionPrinter(QuestionReader reader) {
        return new QuestionPrinterImpl(reader);
    }

    @Bean
    public TestingService testingService(QuestionReader reader,
                                         @Value("${quiz.pass-threshold}") int passThreshold) {
        return new TestingService(reader, passThreshold);
    }
}
