package com.example.graphql.post;

import java.util.List;

public class WriterDao {

    private final List<Writer> writers;

    public WriterDao(List<Writer> writers) {
        this.writers = writers;
    }

    public Writer getWriter(String id) {
        return writers.stream()
                .filter(writer -> id.equals(writer.getId()))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}