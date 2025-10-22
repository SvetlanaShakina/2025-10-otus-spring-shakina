package org.homework.dao;

import org.homework.model.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class QuestionReaderImplTest {

    @Autowired
    private QuestionReader reader;

    @Test
    void readAll_parsesCsvCorrectly() throws Exception {
        List<Question> questions = reader.readAll();

        assertThat(questions).hasSize(2);

        Question q1 = questions.get(0);
        assertThat(q1.getQuestionText()).isEqualTo("What is 2+2?");
        assertThat(q1.getAnswers()).containsExactly("A - 3", "B - 4", "C - 5", "D - 22");
        assertThat(q1.getCorrectIndex()).isEqualTo(2);

        Question q2 = questions.get(1);
        assertThat(q2.getCorrectIndex()).isEqualTo(4);
    }
}
