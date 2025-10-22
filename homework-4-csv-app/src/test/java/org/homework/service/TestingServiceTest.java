package org.homework.service;

import org.homework.config.QuizProps;
import org.homework.model.Question;
import org.junit.jupiter.api.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.MessageSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestingServiceTest {

    @Mock
    QuestionPrinter printer;

    @Mock
    MessageSource messages;

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private PrintStream originalOut;
    private InputStream originalIn;

    private QuizProps props;

    @BeforeEach
    void setUp() {
        props = new QuizProps();
        props.setPassThreshold(1);
        props.setLocale("en");
        props.setCsvBaseName("questions");

        originalOut = System.out;
        originalIn  = System.in;
        System.setOut(new PrintStream(out, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void runTest_happyPath_countsScoreAndPrintsResult() {
        System.setIn(new ByteArrayInputStream("John\nDoe\n".getBytes(StandardCharsets.UTF_8)));

        when(messages.getMessage(eq("io.enter.name"), any(), any(Locale.class))).thenReturn("Enter your name:");
        when(messages.getMessage(eq("io.enter.surname"), any(), any(Locale.class))).thenReturn("Enter your surname:");
        when(messages.getMessage(eq("io.student"), any(), any(Locale.class))).thenReturn("Student: {0} {1}");
        when(messages.getMessage(eq("io.correct"), any(), any(Locale.class))).thenReturn("Correct answers: {0} of {1}");
        when(messages.getMessage(eq("io.passed"), any(), any(Locale.class))).thenReturn("Passed");

        TestingService service = new TestingService(printer, props, messages);

        var q1 = new Question("Q1", List.of("A","B"), 2);
        var q2 = new Question("Q2", List.of("C","D"), 1);

        when(printer.load()).thenReturn(List.of(q1, q2));
        when(printer.askAndReadAnswer(q1)).thenReturn(2);
        when(printer.askAndReadAnswer(q2)).thenReturn(2);

        service.runTest();

        String output = out.toString(StandardCharsets.UTF_8);
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

        when(messages.getMessage(eq("io.enter.name"), any(), any(Locale.class))).thenReturn("Enter your name:");
        when(messages.getMessage(eq("io.enter.surname"), any(), any(Locale.class))).thenReturn("Enter your surname:");
        when(messages.getMessage(eq("io.no.questions"), any(), any(Locale.class))).thenReturn("No questions found. Exiting.");

        TestingService service = new TestingService(printer, props, messages);

        when(printer.load()).thenReturn(List.of());

        service.runTest();

        String output = out.toString(StandardCharsets.UTF_8);
        assertThat(output).contains("No questions found. Exiting.");

        verify(printer).load();
        verifyNoMoreInteractions(printer);
    }
}