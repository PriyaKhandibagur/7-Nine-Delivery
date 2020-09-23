package com.sevennine.Delivery;

import com.google.android.gms.maps.model.LatLng;

public class Person {

    String orderId, storename, storeaddress, customername, customeraddress, firstmile, lastmile, distance, timeestimation, totalamount;
    Object storelatlang;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getStoreaddress() {
        return storeaddress;
    }

    public void setStoreaddress(String storeaddress) {
        this.storeaddress = storeaddress;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getCustomeraddress() {
        return customeraddress;
    }

    public void setCustomeraddress(String customeraddress) {
        this.customeraddress = customeraddress;
    }

    public String getFirstmile() {
        return firstmile;
    }

    public void setFirstmile(String firstmile) {
        this.firstmile = firstmile;
    }

    public String getLastmile() {
        return lastmile;
    }

    public void setLastmile(String lastmile) {
        this.lastmile = lastmile;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTimeestimation() {
        return timeestimation;
    }

    public void setTimeestimation(String timeestimation) {
        this.timeestimation = timeestimation;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public Object getStorelatlang() {
        return storelatlang;
    }

    public void setStorelatlang(Object storelatlang) {
        this.storelatlang = storelatlang;
    }
}


