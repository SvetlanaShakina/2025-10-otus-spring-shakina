package org.homework.service;

import com.opencsv.exceptions.CsvException;
import org.homework.dao.QuestionReader;
import org.homework.model.Question;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class QuestionPrinterImpl implements QuestionPrinter {

    private final QuestionReader questionReader;
    private final Scanner scanner;

    public QuestionPrinterImpl(QuestionReader questionReader) {
        this.questionReader = questionReader;
        this.scanner = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));
    }

    @Override
    public List<Question> load() {
        try {
            return questionReader.readAll();
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
        System.out.print("Your answer (number): ");
        String s = scanner.nextLine();
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return -1;
        }
    }
}