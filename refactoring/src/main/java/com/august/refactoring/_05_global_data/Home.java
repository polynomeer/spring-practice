package com.august.refactoring._05_global_data;

public class Home {

    public static void main(String[] args) {
        System.out.println(Thermostats.targetTemperature);
        Thermostats.targetTemperature = -1111600;
        Thermostats.fahrenheit = false;
    }
}
