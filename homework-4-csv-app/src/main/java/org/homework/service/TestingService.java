package org.homework.service;

import org.homework.config.QuizProps;
import org.homework.model.Question;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import org.springframework.context.MessageSource;

@Component
public class TestingService {

    private final QuestionPrinter printer;
    private final int passThreshold;
    private final MessageSource messages;
    private final Scanner scanner = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));
    private final Locale locale;


    public TestingService(QuestionPrinter printer, QuizProps props, MessageSource messages){
        this.printer = printer;
        this.passThreshold = props.getPassThreshold();
        this.messages = messages;
        this.locale = Locale.forLanguageTag(props.getLocale());
    }

    private String m(String code, Object... args) {
        return messages.getMessage(code, args, locale);
    }

    public void runTest() {
        System.out.print(m("io.enter.name") + " ");
        String first = scanner.nextLine();
        System.out.print(m("io.enter.surname") + " ");
        String last = scanner.nextLine();

        List<Question> questions = printer.load();
        if (questions.isEmpty()) {
            System.out.println(m("io.no.questions"));
            return;
        }

        int score = 0;
        for (Question q : questions) {
            if (printer.askAndReadAnswer(q) == q.getCorrectIndex()) score++;
        }

        System.out.println();
        System.out.println(MessageFormat.format(m("io.student"), first, last));
        System.out.println(MessageFormat.format(m("io.correct"), score, questions.size()));
        System.out.println(score >= passThreshold ? m("io.passed") : m("io.not.passed"));
    }
}
