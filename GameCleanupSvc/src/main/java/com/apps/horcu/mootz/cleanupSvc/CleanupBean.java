package com.apps.horcu.mootz.cleanupSvc;

/**
 * The object model for the data we are sending through endpoints
 */
public class CleanupBean {

    private String myData;

    public String getData() {
        return myData;
    }

    public void setData(String data) {
        myData = data;
    }
}