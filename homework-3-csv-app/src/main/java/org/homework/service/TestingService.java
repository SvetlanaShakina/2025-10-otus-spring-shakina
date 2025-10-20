package org.homework.service;

import org.homework.model.Question;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class TestingService {

    private final QuestionPrinter printer;
    private final int passThreshold;
    private final Scanner scanner = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));

    public TestingService(QuestionPrinter printer, int passThreshold) {
        this.printer = printer;
        this.passThreshold = passThreshold;
    }

    public void runTest() {
        System.out.print("Enter your name: ");
        String first = scanner.nextLine();
        System.out.print("Enter your surname: ");
        String last = scanner.nextLine();

        List<Question> questions = printer.load();
        if (questions.isEmpty()) {
            System.out.println("No questions found. Exiting.");
            return;
        }

        int score = 0;
        for (Question q : questions) {
            int choice = printer.askAndReadAnswer(q);
            if (choice == q.getCorrectIndex()) {
                score++;
            }
        }

        System.out.println();
        System.out.printf("Student: %s %s%n", first, last);
        System.out.printf("Correct answers: %d of %d%n", score, questions.size());
        System.out.println(score >= passThreshold ? "Passed" : "Didn't pass");
    }

    private int parseIntSafe(String s) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return -1; }
    }
}
