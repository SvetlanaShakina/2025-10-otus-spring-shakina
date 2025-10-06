package org.homework.utils;

import org.homework.model.Questioner;

public interface CsvReader {
    Questioner getAsQuestioner() throws Exception;
}
