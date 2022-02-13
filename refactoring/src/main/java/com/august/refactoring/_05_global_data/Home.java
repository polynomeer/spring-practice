package com.august.refactoring._05_global_data;

public class Home {

    public static void main(String[] args) {
        System.out.println(Thermostats.getTargetTemperature());
        Thermostats.setTargetTemperature(68);
        Thermostats.setFahrenheit(false);
    }
}
