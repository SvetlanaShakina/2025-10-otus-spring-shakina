package org.homework.service;

import com.opencsv.exceptions.CsvException;
import org.homework.dao.QuestionReader;
import org.homework.model.Question;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class QuestionPrinterImpl implements QuestionPrinter {

    private final PrintStream out;
    private final BufferedReader in;
    private final QuestionReader questionReader;

    public QuestionPrinterImpl(PrintStream out, InputStream in, QuestionReader questionReader) {
        this.out = out;
        this.in = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        this.questionReader = questionReader;
    }

    /**
     * Prints questions and answers to the configured output stream.
     */
    @Override
    public void print() {
        List<Question> questions;
        try {
            questions = questionReader.readAll();
        } catch (IOException | CsvException e) {
            out.println("Не удалось загрузить вопросы: " + e.getMessage());
            return;
        }
        for (Question q : questions) {
            out.println(q.getQuestionText());
            q.getAnswers().forEach(out::println);
            pressAnyKeyToContinue();
        }
    }

    private void pressAnyKeyToContinue() {
        out.println("Press Enter key to continue...");
        try {
            in.readLine();
        } catch (IOException ignored) { }
    }
}