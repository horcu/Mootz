package com.apps.horcu.mootz.authSvc;

/**
 * The object model for the data we are sending through endpoints
 */
public class AuthBean {

    private String myData;

    public String getData() {
        return myData;
    }

    public void setData(String data) {
        myData = data;
    }
}