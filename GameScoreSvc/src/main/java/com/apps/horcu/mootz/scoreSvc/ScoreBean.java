package com.apps.horcu.mootz.scoreSvc;

/**
 * The object model for the data we are sending through endpoints
 */
public class ScoreBean {

    private String myData;

    public String getData() {
        return myData;
    }

    public void setData(String data) {
        myData = data;
    }
}