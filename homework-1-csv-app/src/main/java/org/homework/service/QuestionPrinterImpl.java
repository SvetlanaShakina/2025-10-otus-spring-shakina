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

    /**
     * Prints questions and answers to the configured output stream.
     */
    @Override
    public void print() {
        List<Question> questions;
        try {
            questions = questionReader.readAll();
        } catch (Exception e) {
            System.out.println("Не удалось загрузить вопросы: " + e.getMessage());
            return;
        }

        for (Question q : questions) {
            System.out.println(q.getQuestionText());
            for (int i = 0; i < q.getAnswers().size(); i++) {
                System.out.println((i + 1) + ") " + q.getAnswers().get(i));
            }
            pause();
        }
    }

    private void pause() {
        System.out.println("Press Enter key to continue...");
        try {
            scanner.nextLine();
        } catch (Exception ignore)
        {

        }
    }
}