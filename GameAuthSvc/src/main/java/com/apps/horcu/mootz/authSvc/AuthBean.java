package com.apps.horcu.mootz.authSvc;

/**
 * The object model for the data we are sending through endpoints
 */
public class AuthBean {

    private String error;
    private String myData;

    public String getData() {
        return myData;
    }

    public void setData(String data) {
        myData = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}