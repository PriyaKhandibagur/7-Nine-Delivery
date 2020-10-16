package com.sevennine.Delivery.Bean;

public class AccountDetails {


    private String accholderName;
    private String accNumber;
    private String bankname;
    private String ifscCode;
    private String panNumber;

    public AccountDetails() {
    }

    public AccountDetails(String accholderName, String accNumber, String bankname, String ifscCode, String panNumber) {
        this.accholderName = accholderName;
        this.accNumber = accNumber;
        this.bankname = bankname;
        this.ifscCode = ifscCode;
        this.panNumber = panNumber;
    }

    public String getAccholderName() {
        return accholderName;
    }

    public void setAccholderName(String accholderName) {
        this.accholderName = accholderName;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }


}
