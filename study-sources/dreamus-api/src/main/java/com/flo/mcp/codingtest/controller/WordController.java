package com.flo.mcp.codingtest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class WordController {
    private static int AUTO_INCREMENTED_ID = 0;

    Map<String, Integer> wordMap = new HashMap<>();
    Map<Integer, String> statementDB = new HashMap<>();

    @PostMapping("/words")
    public void addWords(@RequestBody String words) {
        String[] tokens = words.split(" ");

        // 생성
        int id = addStatement(words);

        // 조회
        String statement = findById(id);
        System.out.println("\"" + statement + "\"" + " has been added with id " + id);

        // 수정
        String statementToUpdate = "updated";
        update(id, statementToUpdate);
        String statementUpdated = findById(id);
        System.out.println("\"" + statement + "\"" + " has been updated to " + "\"" + statementUpdated + "\"");

        // 삭제
        deleteById(id);
        System.out.println("id " + id + " has been deleted. DB size is " + statementDB.size());
        for (Integer key : statementDB.keySet()) {
            System.out.println("statementDB = " + statementDB.get(key));
        }

        for (String token : tokens) {
            wordMap.put(token, wordMap.getOrDefault(token, 0) + 1);
        }
    }

    public void update(int id, String statement) {
        statementDB.put(id, statement);
    }

    public void deleteById(int id) {
        statementDB.remove(id);
    }

    public String findById(int id) {
        return statementDB.get(id);
    }

    public int addStatement(String words) {
        statementDB.put(AUTO_INCREMENTED_ID, words);
        return AUTO_INCREMENTED_ID++;
    }


    @GetMapping("/words")
    public Map<String, Integer> wordCount() {
        return wordMap;
    }

}
