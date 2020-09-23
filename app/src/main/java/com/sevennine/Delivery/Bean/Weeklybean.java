package com.sevennine.Delivery.Bean;
public class Weeklybean {

    String amount,earnings,id;


    public Weeklybean(String amount, String earnings,String id) {

        this.amount = amount;
        this.earnings = earnings;
        this.id = id;


    }

    public String getAmount() {
        return amount;
    }

    public String getEarnings() {
        return earnings;
    }
    public String getId() {
        return id;
    }


}
