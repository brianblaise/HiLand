package com.example.brianb.demo1;

import android.widget.Spinner;

import java.io.Serializable;

/**
 * Created by BrianB on 17-Apr-17.
 */
public class Person implements Serializable{
    public int id;
    public String name, email, accntype, pass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccntype() {
        return accntype;
    }

    public void setAccntype(String accntype) {
        this.accntype = accntype;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}

