package com.sevennine.Delivery.Bean;
public class PickupItemsBean {

    String item_count,item_name,quantity;


    public PickupItemsBean(String item_count, String item_name, String quantity) {

        this.item_count = item_count;
        this.item_name = item_name;
        this.quantity = quantity;

    }

    public String getItem_count() {
        return item_count;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getQuantity() {
        return quantity;
    }
}
