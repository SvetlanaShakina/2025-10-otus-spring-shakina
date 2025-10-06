package org.homework;

import org.homework.utils.CsvReader;
import org.homework.utils.QuestionerPrinter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        final CsvReader csvReader = context.getBean(CsvReader.class);
        final QuestionerPrinter questionerPrinter = context.getBean(QuestionerPrinter.class);
        questionerPrinter.print(csvReader.getAsQuestioner());
    }
}
