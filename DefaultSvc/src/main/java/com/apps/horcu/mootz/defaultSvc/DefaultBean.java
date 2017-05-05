package com.apps.horcu.mootz.defaultSvc;

import com.apps.horcu.mootz.common.ServiceTask;

import java.util.Map;

/**
 * The object model for the data we are sending through endpoints
 */
public class DefaultBean {

    private String error;
    private ServiceTask data;


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ServiceTask getData() {
        return data;
    }

    public void setData(ServiceTask data) {
        this.data = data;
    }
}