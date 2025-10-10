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

    @Override
    /**
     * Converts CSV file data to a list of Line objects
     */
    public List<Question> readAll() throws IOException, CsvException {
        var resource = new ClassPathResource(fileName);
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
             CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()) {

            List<String[]> rows = csvReader.readAll();

            return rows.stream()
                    .map(arr -> {
                        String question = arr.length > 0 ? arr[0] : "";
                        List<String> answers = Arrays.stream(arr).skip(1).collect(Collectors.toList());
                        return new Question(question, answers);
                    })
                    .collect(Collectors.toList());
        }
    }
}
