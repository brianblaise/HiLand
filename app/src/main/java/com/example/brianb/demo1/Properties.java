package com.example.brianb.demo1;

import java.io.Serializable;

/**
 * Created by BrianB on 01-May-17.
 */
public class Properties implements Serializable{
    public int Id;
    public String address,units, property_type, pPath;

    public Properties() {
    }

    public Properties(String property_type, String address, String units, String pPath) {
        this.property_type = property_type;
        this.address = address;
        this.units = units;
        this.pPath = pPath;
    }

    public int getId() {
        return Id;
    }

    public String getpPath() {
        return pPath;
    }

    public void setpPath(String pPath) {
        this.pPath = pPath;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getProperty() {
        return property_type;
    }

    public void setProperty(String property_type) {
        this.property_type = property_type;
    }

    public String getAddress() { return address; }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }


}
