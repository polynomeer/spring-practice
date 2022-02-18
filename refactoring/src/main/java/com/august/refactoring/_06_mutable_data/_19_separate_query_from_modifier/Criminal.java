package com.august.refactoring._06_mutable_data._19_separate_query_from_modifier;

import java.util.List;

public class Criminal {

    /**
     * 리턴은 하지않고 알람만 끄도록
     *
     * @param people 검사할 사람
     */
    public void alertForMiscreant(List<Person> people) {
        for (Person p : people) {
            if (p.getName().equals("Don")) {
                setOffAlarms();
            }

            if (p.getName().equals("John")) {
                setOffAlarms();
            }
        }
    }

    /**
     * 조회만 하는 메서드 (사이드 이팩트 제거)
     *
     * @param people 검사할 사람
     * @return 범죄자 이름
     */
    public String findMiscreant(List<Person> people) {
        for (Person p : people) {
            if (p.getName().equals("Don")) {
                return "Don";
            }

            if (p.getName().equals("John")) {
                return "John";
            }
        }

        return "";
    }

    private void setOffAlarms() {
        System.out.println("set off alarm");
    }
}
