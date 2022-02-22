package com.august.refactoring._03_long_function;

import java.util.List;

public class ConsolePrinter extends StudyPrinter {

    public ConsolePrinter(int totalNumberOfEvents, List<Participant> participants) {
        super(totalNumberOfEvents, participants);
    }

    @Override
    public void execute() {
        this.participants.forEach(p -> {
            System.out.printf("%s %s:%s\n", p.username(), checkMark(p), p.getRate(this.totalNumberOfEvents));
        });
    }

}
