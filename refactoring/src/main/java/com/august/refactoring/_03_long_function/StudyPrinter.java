package com.august.refactoring._03_long_function;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public abstract class StudyPrinter {

    protected final int totalNumberOfEvents;
    protected final List<Participant> participants;

    public StudyPrinter(int totalNumberOfEvents, List<Participant> participants) {
        this.totalNumberOfEvents = totalNumberOfEvents;
        this.participants = participants;
        this.participants.sort(Comparator.comparing(Participant::username));
    }

    public abstract void execute() throws IOException;

    protected String checkMark(Participant p) {
        StringBuilder line = new StringBuilder();
        for (int i = 1; i <= this.totalNumberOfEvents; i++) {
            if (p.homework().containsKey(i) && p.homework().get(i)) {
                line.append("|:white_check_mark:");
            } else {
                line.append("|:x:");
            }
        }
        return line.toString();
    }
}
