package com.apps.horcu.mootz.authSvc;

import com.apps.horcu.mootz.common.ServiceTask;

/**
 * The object model for the data we are sending through endpoints
 */
public class AuthBean {

    private String error;
    private ServiceTask myData;

    public ServiceTask getData() {
        return myData;
    }

    public void setData(ServiceTask data) {
        myData = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}