package com.sevennine.Delivery.Bean;

public class User {

    private String firstName;
    private String joiningDate;
    private String mobileNumber;

    public User() {
    }

    public User(String firstName, String joiningDate, String mobileNumber) {
        this.firstName = firstName;
        this.joiningDate = joiningDate;
        this.mobileNumber = mobileNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
