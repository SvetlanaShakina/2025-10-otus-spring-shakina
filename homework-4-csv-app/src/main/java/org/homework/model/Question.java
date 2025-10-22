package org.homework.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Question {
    private String questionText;
    private List<String> answers;
    private final int correctIndex;
}
