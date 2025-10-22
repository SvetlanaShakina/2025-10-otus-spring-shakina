package org.homework.service;

import com.opencsv.exceptions.CsvException;
import org.homework.config.QuizProps;
import org.homework.dao.QuestionReader;
import org.homework.model.Question;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@Component

public class QuestionPrinterImpl implements QuestionPrinter {

    private final QuestionReader reader;
    private final Scanner scanner = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));
    private final MessageSource messages;
    private final Locale locale;

    public QuestionPrinterImpl(QuestionReader reader, MessageSource messages, QuizProps props){
        this.reader = reader;
        this.messages = messages;
        this.locale = Locale.forLanguageTag(props.getLocale());
    }

    private String m(String code, Object... args) {
        return messages.getMessage(code, args, locale);
    }

    @Override
    public List<Question> load() {
        try {
            return reader.readAll();
        } catch (IOException | CsvException e) {
            System.out.println("Failed to load questions: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public int askAndReadAnswer(Question q) {
        System.out.println();
        System.out.println(q.getQuestionText());
        for (int i = 0; i < q.getAnswers().size(); i++) {
            System.out.println((i + 1) + ") " + q.getAnswers().get(i));
        }
        System.out.print(m("io.your.answer") + " ");
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            return -1;
        }
    }
}