package com.august.refactoring._12_repeated_swtiches;

public class SwitchImprovements {

    public int vacationHours(String type) {
        int result;
        switch (type) {
            case "full-time":
                result = 120;
                break;
            case "part-time":
                result = 80;
                break;
            case "temporal":
                result = 32;
                break;
            default:
                result = 0;
        }
        return result;
    }
}
