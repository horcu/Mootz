package com.apps.horcu.mootz.common;

/**
 * Created by Horatio on 5/5/2017.
 */

public class ServiceTask {
    private String taskName;
    private String taskUrl;
    private String fullTaskUrl;
    private String queueName;
    // private HashMap<String,String> params;
    private String token;
    private boolean isAuthenticated;
    private String userId;
    private String prevTaskUrl;
    private String nextTaskUrl;

    ServiceTask(String taskName, String taskUrl, String queueName, String token,
                String userId){
        this.taskName = taskName;
        this.taskUrl = taskUrl;
        this.queueName = queueName;
        this.token = token;
        this.userId = userId;
        this.prevTaskUrl = "";
        this.nextTaskUrl = "";
    }

    ServiceTask(String taskName, String taskUrl, String queueName, String token,
                String userId, String prevTaskUrl, String nextTaskUrl) {

        this.taskName = taskName;
        this.taskUrl = taskUrl;
        this.queueName = queueName;
        this.token = token;
        this.userId = userId;
        this.prevTaskUrl = prevTaskUrl;
        this.nextTaskUrl = nextTaskUrl;
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

    public String getPrevTaskUrl() {
        return prevTaskUrl;
    }

    public void setPrevTaskUrl(String prevTaskUrl) {
        this.prevTaskUrl = prevTaskUrl;
    }

    public String getNextTaskUrl() {
        return nextTaskUrl;
    }

    public void setNextTaskUrl(String nextTaskUrl) {
        this.nextTaskUrl = nextTaskUrl;
    }

    public String getFullTaskUrl() {
        return fullTaskUrl;
    }

    public void setFullTaskUrl(String fullTaskUrl) {
        this.fullTaskUrl = fullTaskUrl;
    }
}
