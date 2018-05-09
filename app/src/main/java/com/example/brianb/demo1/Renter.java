package com.example.brianb.demo1;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class Renter implements Serializable{
    public int rHouseNumber, rId;
    public String rName, rDate, rAddress, rEmail, rPhone, rPath;

    public Renter() {

    }

    public Renter(int rHouseNumber, String rName, String rPhone, String rDate, String rAddress, String rEmail, String rPath) {      //byte[] rImage
        this.rHouseNumber = rHouseNumber;
        this.rName = rName;
        this.rPhone = rPhone;
        this.rDate = rDate;
        this.rAddress = rAddress;
        this.rEmail = rEmail;
        this.rPath = rPath;
    }

    public int getrId() {
        return rId;
    }

    public void setrId(int rId) {
        this.rId = rId;
    }

    public int getrHouseNumber() {
        return rHouseNumber;
    }

    public void setrHouseNumber(int rHouseNumber) {
        this.rHouseNumber = rHouseNumber;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public String getrPhone() {
        return rPhone;
    }

    public void setrPhone(String rPhone) {
        this.rPhone = rPhone;
    }

    public String getrDate() {
        return rDate;
    }

    public void setrDate(String rDate) {
        this.rDate = rDate;
    }

    public String getrAddress() {
        return rAddress;
    }

    public void setrAddress(String rAddress) {
        this.rAddress = rAddress;
    }

    public String getrEmail() {
        return rEmail;
    }

    public void setrEmail(String rEmail) {
        this.rEmail = rEmail;
    }

    /**
     * Gets rPath.
     *
     * @return Value of rPath.
     */
    public String getrPath() {
        return rPath;
    }

    /**
     * Sets new rPath.
     *
     * @param rPath New value of rPath.
     */
    public void setrPath(String rPath) {
        this.rPath = rPath;
    }

}

   /* public byte[] getrImage() { return rImage;  }

    public void setrImage(byte[] rImage) { this.rImage = rImage;  } */



