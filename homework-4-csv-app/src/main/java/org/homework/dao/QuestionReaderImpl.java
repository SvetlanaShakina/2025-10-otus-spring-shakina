package org.homework.dao;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.homework.config.QuizProps;
import org.homework.model.Question;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionReaderImpl implements QuestionReader {
    private final QuizProps props;

    public QuestionReaderImpl(QuizProps props){
        this.props = props;
    }

    @Override
    public List<Question> readAll() throws IOException, CsvException {
        String lang = props.getLocale() == null ? "en" : props.getLocale();
        String fileName = props.getCsvBaseName() + "_" + lang + ".csv";
        var resource = new ClassPathResource(fileName);
        try (Reader r = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
             CSVReader csv = new CSVReaderBuilder(r).withSkipLines(1).build()) {
            return csv.readAll().stream()
                    .filter(arr -> arr != null && arr.length >= 3)
                    .map(arr -> {
                        String q = arr[0] == null ? "" : arr[0].replace("\uFEFF","").trim();
                        int correct = Integer.parseInt(arr[arr.length-1].trim());
                        List<String> answers = Arrays.stream(arr).skip(1).limit(arr.length-2L)
                                .map(s -> s==null? "" : s.trim()).collect(Collectors.toList());
                        return new Question(q, answers, correct);
                    }).collect(Collectors.toList());
        }
    }
}
