package com.august.refactoring._02_duplicated_code;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ReviewerDashboard extends Dashboard {

    public void printReviewers() throws IOException {
        printUsernames(30);
    }

    public void printUsernames(int eventId) throws IOException {
        super.printUsernames(eventId);
    }

}
