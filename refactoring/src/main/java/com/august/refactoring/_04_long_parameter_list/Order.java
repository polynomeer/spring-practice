package com.august.refactoring._04_long_parameter_list;

import java.time.LocalDate;

public class Order {

    private final int quantity;
    private final double itemPrice;
    private final LocalDate placedOn;
    private final String deliveryState;

    public Order(int quantity, double itemPrice) {
        this(quantity, itemPrice, null, null);
    }

    public Order(LocalDate placedOn, String deliveryState) {
        this(0, 0, placedOn, deliveryState);
    }

    public Order(int quantity, double itemPrice, LocalDate placedOn, String deliveryState) {
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.placedOn = placedOn;
        this.deliveryState = deliveryState;
    }

    public double finalPrice() {
        double basePrice = this.quantity * this.itemPrice;
        return this.discountedPrice(basePrice);
    }

    private int discountLevel() {
        return this.quantity > 100 ? 2 : 1;
    }

    private double discountedPrice(double basePrice) {
        return discountLevel() == 2 ? basePrice * 0.9 : basePrice * 0.95;
    }

    public LocalDate getPlacedOn() {
        return placedOn;
    }

    public String getDeliveryState() {
        return deliveryState;
    }
}
