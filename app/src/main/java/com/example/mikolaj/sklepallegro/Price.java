package com.example.mikolaj.sklepallegro;

public class Price {
    private double amount;
    private String currency;

    public Price(double amount, String currency){
        this.amount = amount;
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}
