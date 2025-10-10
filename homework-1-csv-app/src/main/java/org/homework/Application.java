package org.homework;

import org.homework.model.Question;
import org.homework.dao.QuestionReader;
import org.homework.service.QuestionPrinter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class Application {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        var printer = context.getBean(QuestionPrinter.class);
        printer.print();
    }
}
