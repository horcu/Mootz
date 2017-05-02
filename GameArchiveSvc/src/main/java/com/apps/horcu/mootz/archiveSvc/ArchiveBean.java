package com.apps.horcu.mootz.archiveSvc;

/**
 * The object model for the data we are sending through endpoints
 */
public class ArchiveBean {

    private String myData;

    public String getData() {
        return myData;
    }

    public void setData(String data) {
        myData = data;
    }
}