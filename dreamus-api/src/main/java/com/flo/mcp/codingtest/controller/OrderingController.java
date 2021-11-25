package com.flo.mcp.codingtest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RestController
public class OrderingController {

    /**
     * date: 2021-11-23
     * 문자열을 입력 받아 알파벳 문자는 알파벳 순서로 출력하고, 정수의 합을 출력하는 API
     *
     * @author 함승훈
     * @param str 입력받을 문자열
     */
    @PostMapping("/strings")
    public void addString(@RequestBody String str) {
        char[] arr = str.trim().toUpperCase().toCharArray();
        int sum = 0;
        List<Character> alphabets = new ArrayList<>();

        for (char c : arr) {
            if (c >= '0' && c <= '9') {
                sum += c - ('0' - 0);
            } else {
                alphabets.add(c);
            }
        }

        Collections.sort(alphabets);

        for (Character alphabet : alphabets) {
            System.out.print(alphabet);
        }
        System.out.println(sum);

    }
}
