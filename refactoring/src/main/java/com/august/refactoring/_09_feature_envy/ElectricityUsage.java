package com.august.refactoring._09_feature_envy;

public class ElectricityUsage {

    private final double amount;
    private final double pricePerUnit;

    public ElectricityUsage(double amount, double pricePerUnit) {
        this.amount = amount;
        this.pricePerUnit = pricePerUnit;
    }

    public double getAmount() {
        return amount;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public double getElectricityBill() {
        return this.getAmount() * this.getPricePerUnit();
    }
}
