package org.homework.dao;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.homework.model.Question;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionReaderImpl implements QuestionReader {
    private final String fileName;

    public QuestionReaderImpl(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Converts CSV file data to a list of Line objects
     */
    @Override
    public List<Question> readAll() throws IOException, CsvException {
        var resource = new ClassPathResource(fileName);
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
             CSVReader csv = new CSVReaderBuilder(reader).withSkipLines(1).build()) {

            return csv.readAll().stream()
                    .filter(arr -> arr != null && arr.length >= 3)
                    .map(arr -> {
                        String q = arr[0].replace("\uFEFF", "").trim();
                        int correct = Integer.parseInt(arr[arr.length - 1].trim());
                        List<String> answers = Arrays.stream(arr)
                                .skip(1)
                                .limit(arr.length - 2L)
                                .map(String::trim)
                                .collect(Collectors.toList());
                        return new Question(q, answers, correct);
                    })
                    .collect(Collectors.toList());
        }
    }
}
