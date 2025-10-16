package org.homework.dao;

import com.opencsv.exceptions.CsvException;
import org.homework.model.Question;

import java.io.IOException;
import java.util.List;

public interface QuestionReader {
    List<Question> readAll() throws IOException, CsvException;
}
