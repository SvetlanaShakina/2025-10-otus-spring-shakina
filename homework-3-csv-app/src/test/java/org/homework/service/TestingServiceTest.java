package org.homework.service;

import org.homework.model.Question;
import org.junit.jupiter.api.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestingServiceTest {

    @Mock
    QuestionPrinter printer;

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private PrintStream defaultOut;

    @BeforeEach
    void setUp() {
        defaultOut = System.out;
        System.setOut(new PrintStream(out, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void tearDown() {
        System.setOut(defaultOut);
        System.setIn(System.in);
    }

    @Test
    void runTest_happyPath_countsScoreAndPrintsResult() {
        // имя/фамилия
        System.setIn(new ByteArrayInputStream("John\nDoe\n".getBytes(StandardCharsets.UTF_8)));

        var q1 = new Question("Q1", List.of("A","B"), 2);
        var q2 = new Question("Q2", List.of("C","D"), 1);
        when(printer.load()).thenReturn(List.of(q1, q2));
        when(printer.askAndReadAnswer(q1)).thenReturn(2);
        when(printer.askAndReadAnswer(q2)).thenReturn(2);

        var service = new TestingService(printer, 1);

        service.runTest();

        var output = out.toString(StandardCharsets.UTF_8);
        assertThat(output).contains("Student: John Doe");
        assertThat(output).contains("Correct answers: 1 of 2");
        assertThat(output).contains("Passed");

        verify(printer).load();
        verify(printer).askAndReadAnswer(q1);
        verify(printer).askAndReadAnswer(q2);
        verifyNoMoreInteractions(printer);
    }

    @Test
    void runTest_noQuestions_printsAndExits() {
        System.setIn(new ByteArrayInputStream("Ann\nLee\n".getBytes(StandardCharsets.UTF_8)));
        when(printer.load()).thenReturn(List.of());

        var service = new TestingService(printer, 1);
        service.runTest();

        assertThat(out.toString(StandardCharsets.UTF_8)).contains("No questions found");
        verify(printer).load();
        verifyNoMoreInteractions(printer);
    }
}
