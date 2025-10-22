package org.homework.service;

import org.homework.config.QuizProps;
import org.homework.dao.QuestionReader;
import org.homework.model.Question;
import org.junit.jupiter.api.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.MessageSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionPrinterImplTest {

    @Mock
    QuestionReader reader;

    @Mock
    MessageSource messages;

    private QuestionPrinter printer;
    private InputStream originalIn;
    private QuizProps props;


    @BeforeEach
    void setUp() {
        props = new QuizProps();
        props.setLocale("en");
        originalIn = System.in;
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
    }

    @Test
    void load_returnsQuestions_whenReaderOk() throws Exception {
        printer = new QuestionPrinterImpl(reader, messages, props);

        var q = new Question("Q?", List.of("A","B"), 1);
        when(reader.readAll()).thenReturn(List.of(q));

        var result = printer.load();

        assertThat(result).containsExactly(q);
        verify(reader).readAll();
    }

    @Test
    void load_returnsEmpty_onReaderException() throws Exception {
        printer = new QuestionPrinterImpl(reader, messages, props);

        when(reader.readAll()).thenThrow(new java.io.IOException("boom"));

        assertThat(printer.load()).isEmpty();
    }

    @Test
    void askAndReadAnswer_readsUserInputNumber() {
        System.setIn(new ByteArrayInputStream("2\n".getBytes(StandardCharsets.UTF_8)));
        when(messages.getMessage(eq("io.your.answer"), any(), any(Locale.class)))
                .thenReturn("Your answer (number):");

        printer = new QuestionPrinterImpl(reader, messages, props);

        var q = new Question("Pick one", List.of("X","Y","Z"), 2);

        int answer = printer.askAndReadAnswer(q);

        assertThat(answer).isEqualTo(2);
        verify(messages).getMessage(eq("io.your.answer"), any(), any(Locale.class));
    }
}
