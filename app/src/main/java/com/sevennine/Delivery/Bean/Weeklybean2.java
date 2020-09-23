package com.sevennine.Delivery.Bean;
public class Weeklybean2 {

    String amount,earnings,time,deliver;


    public Weeklybean2(String amount, String earnings, String time,String deliver) {

        this.amount = amount;
        this.earnings = earnings;
        this.time = time;
        this.deliver = deliver;


    }

    public String getAmount() {
        return amount;
    }

    public String getEarnings() {
        return earnings;
    }
    public String getTime() {
        return time;
    }
    public String getDeliver() {
        return deliver;
    }


}
