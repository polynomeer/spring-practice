package com.august.refactoring._05_global_data;

public class Thermostats {

    private static Integer targetTemperature = 70;

    private static Boolean heating = true;

    private static Boolean cooling = false;

    private static Boolean fahrenheit = true;

    public static Integer getTargetTemperature() {
        return targetTemperature;
    }

    public static void setTargetTemperature(Integer targetTemperature) {
        Thermostats.targetTemperature = targetTemperature;
    }

    public static Boolean getHeating() {
        return heating;
    }

    public static void setHeating(Boolean heating) {
        // TODO: Add validation logic
        Thermostats.heating = heating;
        // TODO: Add notify logic -> e.g. google nest alarm
    }

    public static Boolean getCooling() {
        return cooling;
    }

    public static void setCooling(Boolean cooling) {
        Thermostats.cooling = cooling;
    }

    public static Boolean getFahrenheit() {
        return fahrenheit;
    }

    public static void setFahrenheit(Boolean fahrenheit) {
        Thermostats.fahrenheit = fahrenheit;
    }
}
