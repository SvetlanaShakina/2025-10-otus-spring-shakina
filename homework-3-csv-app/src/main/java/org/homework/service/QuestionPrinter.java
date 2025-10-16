package org.homework.service;

import org.homework.model.Question;

import java.util.List;

public interface QuestionPrinter {
    List<Question> load();

    int askAndReadAnswer(Question question);
}
