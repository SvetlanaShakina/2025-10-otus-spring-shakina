package org.homework.service;

import com.opencsv.exceptions.CsvException;
import org.homework.dao.QuestionReader;
import org.homework.model.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class TestingService {

    private final QuestionReader reader;
    private final int passThreshold;
    private final Scanner scanner = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));

    public TestingService(QuestionReader reader, int passThreshold) {
        this.reader = reader;
        this.passThreshold = passThreshold;
    }

    public void runTest() {
        System.out.print("Enter your name: ");
        String first = scanner.nextLine();
        System.out.print("Enter your surname: ");
        String last = scanner.nextLine();

        List<Question> questions;
        try {
            questions = reader.readAll();
        } catch (IOException | CsvException e) {
            System.out.println("Error loading questions: " + e.getMessage());
            return;
        }

        int score = 0;
        for (Question q : questions) {
            System.out.println();
            System.out.println(q.getQuestionText());
            for (int i = 0; i < q.getAnswers().size(); i++) {
                System.out.println((i + 1) + ") " + q.getAnswers().get(i));
            }
            System.out.print("Your answer (number): ");
            int choice = parseIntSafe(scanner.nextLine());
            if (choice == q.getCorrectIndex()) score++;
        }

        System.out.println();
        System.out.printf("Student: %s %s%n", first, last);
        System.out.printf("Correct answers: %d from %d%n", score, questions.size());
        System.out.println(score >= passThreshold ? "Passed" : "Didn't pass");
    }

    private int parseIntSafe(String s) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return -1; }
    }
}
