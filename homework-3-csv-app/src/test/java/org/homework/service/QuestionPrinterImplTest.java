package org.homework.service;

import org.homework.dao.QuestionReader;
import org.homework.model.Question;
import org.junit.jupiter.api.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionPrinterImplTest {

    @Mock
    QuestionReader reader;

    private QuestionPrinter printer;

    @BeforeEach
    void setUp() {
        printer = new QuestionPrinterImpl(reader);
    }

    @Test
    void load_returnsQuestions_whenReaderOk() throws Exception {
        var q = new Question("Q?", List.of("A","B"), 1);
        when(reader.readAll()).thenReturn(List.of(q));
        var result = printer.load();
        assertThat(result).containsExactly(q);
        verify(reader, times(1)).readAll();
    }

    @Test
    void load_returnsEmpty_onReaderException() throws Exception {
        when(reader.readAll()).thenThrow(new java.io.IOException("boom"));
        var result = printer.load();
        assertThat(result).isEmpty();
    }

    @Test
    void askAndReadAnswer_readsUserInputNumber() {
        var originalIn = System.in;
        var in = new ByteArrayInputStream("2\n".getBytes(StandardCharsets.UTF_8));
        try {
            System.setIn(in);
            printer = new QuestionPrinterImpl(reader);
            var q = new Question("Pick one", List.of("X","Y","Z"), 2);
            int answer = printer.askAndReadAnswer(q);
            assertThat(answer).isEqualTo(2);
        } finally {
            System.setIn(originalIn);
        }
    }

    @AfterEach
    void tearDown() {
        System.setIn(System.in);
    }
}
