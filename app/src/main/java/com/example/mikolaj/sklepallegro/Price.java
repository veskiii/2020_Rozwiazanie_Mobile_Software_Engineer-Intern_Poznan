package com.example.mikolaj.sklepallegro;

import java.io.Serializable;

public class Price implements Serializable {
    private double amount;
    private String currency;

    Price(double amount, String currency){
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
