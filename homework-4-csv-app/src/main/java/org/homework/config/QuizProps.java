package org.homework.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;


@Validated
@ConfigurationProperties(prefix = "quiz")
@Component
@Data
public class QuizProps {
    private String csvBaseName;
    private int passThreshold;
    private String locale;

}
