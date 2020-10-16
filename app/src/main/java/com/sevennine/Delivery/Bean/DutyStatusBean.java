package com.sevennine.Delivery.Bean;

public class DutyStatusBean {

    boolean DutyOn;



    public DutyStatusBean(boolean DutyOn) {

        this.DutyOn = DutyOn;

    }

    public boolean isDutyOn() {
        return DutyOn;
    }

    public void setDutyOn(boolean dutyOn) {
        DutyOn = dutyOn;
    }


}
