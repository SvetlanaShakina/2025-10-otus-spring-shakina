package org.homework.utils;

import org.homework.model.Questioner;

public class QuestionerPrinterSimple implements QuestionerPrinter {

    @Override
    /**
     * Prints out data from Questioner object
     * @param questioner Questioner object for print
     */
    public void print(Questioner questioner) {
        questioner.getLines().forEach(l -> {
            System.out.println(l.getQuestion());
            l.getAnswers().forEach(System.out::println);
            pressAnyKeyToContinue();
        });
    }

    private void pressAnyKeyToContinue() {
        System.out.println("Press Enter key to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }
}
