package com.sevennine.Delivery;

import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

public class Person {

    String orderId, storename, storeaddress, customername, customeraddress, firstmile, lastmile, distance, timeestimation, totalamount,PickupRatings,CollectedCash,DeliveryRatings;
    Map<String,Double> storelatlang,custlatlang;
    boolean ReachedPickupLocation,ItemsConfirmed,PickupComplete,ReachedDelivryLocation,DeliveryComplete;

    public Person(String orderId, String storename, String storeaddress, String customername, String customeraddress, String firstmile, String lastmile, String distance, String timeestimation, String totalamount, Map<String, Double> storelatlang, Map<String, Double> custlatlang,String PickupRatings,String CollectedCash,String DeliveryRatings,boolean ReachedPickupLocation,boolean ItemsConfirmed,boolean PickupComplete,boolean ReachedDelivryLocation,boolean DeliveryComplete) {
        this.orderId = orderId;
        this.storename = storename;
        this.storeaddress = storeaddress;
        this.customername = customername;
        this.customeraddress = customeraddress;
        this.firstmile = firstmile;
        this.lastmile = lastmile;
        this.distance = distance;
        this.timeestimation = timeestimation;
        this.totalamount = totalamount;
        this.storelatlang = storelatlang;
        this.custlatlang = custlatlang;
        this.PickupRatings = PickupRatings;
        this.CollectedCash = CollectedCash;
        this.DeliveryRatings = DeliveryRatings;
        this.ReachedPickupLocation = ReachedPickupLocation;
        this.ItemsConfirmed = ItemsConfirmed;
        this.PickupComplete = PickupComplete;
        this.ReachedDelivryLocation = ReachedDelivryLocation;
        this.DeliveryComplete = DeliveryComplete;
    }
    public Person(){}

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

    public Map<String, Double> getStorelatlang() {
        return storelatlang;
    }

    public void setStorelatlang(Map<String, Double> storelatlang) {
        this.storelatlang = storelatlang;
    }

    public Map<String, Double> getCustlatlang() {
        return custlatlang;
    }

    public void setCustlatlang(Map<String, Double> custlatlang) {
        this.custlatlang = custlatlang;
    }

    public String getPickupRatings() {
        return PickupRatings;
    }

    public void setPickupRatings(String pickupRatings) {
        PickupRatings = pickupRatings;
    }

    public String getCollectedCash() {
        return CollectedCash;
    }

    public void setCollectedCash(String collectedCash) {
        CollectedCash = collectedCash;
    }

    public String getDeliveryRatings() {
        return DeliveryRatings;
    }

    public void setDeliveryRatings(String deliveryRatings) {
        DeliveryRatings = deliveryRatings;
    }

    public boolean isReachedPickupLocation() {
        return ReachedPickupLocation;
    }

    public void setReachedPickupLocation(boolean reachedPickupLocation) {
        ReachedPickupLocation = reachedPickupLocation;
    }

    public boolean isItemsConfirmed() {
        return ItemsConfirmed;
    }

    public void setItemsConfirmed(boolean itemsConfirmed) {
        ItemsConfirmed = itemsConfirmed;
    }

    public boolean isPickupComplete() {
        return PickupComplete;
    }

    public void setPickupComplete(boolean pickupComplete) {
        PickupComplete = pickupComplete;
    }

    public boolean isReachedDelivryLocation() {
        return ReachedDelivryLocation;
    }

    public void setReachedDelivryLocation(boolean reachedDelivryLocation) {
        ReachedDelivryLocation = reachedDelivryLocation;
    }

    public boolean isDeliveryComplete() {
        return DeliveryComplete;
    }

    public void setDeliveryComplete(boolean deliveryComplete) {
        DeliveryComplete = deliveryComplete;
    }
}


