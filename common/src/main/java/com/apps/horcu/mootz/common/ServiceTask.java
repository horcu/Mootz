package com.apps.horcu.mootz.common;

import java.util.Map;

/**
 * Created by Horatio on 5/5/2017.
 */

public class ServiceTask {
    private String taskName;
    private String taskUrl;
    private String queueName;
    private Map<String,Object> params;
    private String token;
    private boolean isAuthenticated;
    private String userId;

    ServiceTask(String taskName, String taskUrl, String queueName, Map<String, Object> params, String token, String userId) {
        this.taskName = taskName;
        this.taskUrl = taskUrl;
        this.queueName = queueName;
        this.params = params;
        this.token = token;
        this.userId = userId;
    }

    public ServiceTask() {

    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskUrl() {
        return taskUrl;
    }

    public void setTaskUrl(String taskUrl) {
        this.taskUrl = taskUrl;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getToken() {
        return token;
    }

    public void setIsAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
