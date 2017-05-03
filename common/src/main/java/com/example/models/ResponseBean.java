package com.example.models;

import java.util.Map;

/**
 * The object model for the data we are sending through endpoints
 */
public class ResponseBean {

    private Map<String,Object> myData;

    public Map<String,Object> getData() {
        return myData;
    }

    public void setData(Map<String,Object> data) {
        myData = data;
    }
}