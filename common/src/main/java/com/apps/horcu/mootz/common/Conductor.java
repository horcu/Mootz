package com.apps.horcu.mootz.common;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.repackaged.com.google.gson.Gson;

import java.util.Date;
import java.util.Map;

/**
 * Created by Horatio on 5/5/2017.
 */

public class Conductor {

    public boolean AddToQueue(ServiceTask serviceTask) throws Exception {

        String result;

        String tName = String.valueOf(new Date().getTime()) + "_" + serviceTask.getTaskName();
        try {
            Queue queue = QueueFactory.getQueue(serviceTask.getQueueName());
            queue.add(TaskOptions.Builder.withUrl(serviceTask.getTaskUrl())
                    .method(TaskOptions.Method.POST)
                    .taskName(tName));

            Map<String, Object> params = serviceTask.getParams();
            if(params!= null) {
                for (int i = 0; i < params.keySet().size(); i++) {
                    String key = String.valueOf(params.keySet().toArray()[i]);
                    String value = String.valueOf(params.entrySet().toArray()[i]);

                    queue.add(TaskOptions.Builder.withParam(key, value));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public  ServiceTask getTaskForRoute(String route) {

        switch (route){
            case "/sayClean" :
                return new ServiceTask("clean-task","/sayClean", "clean-queue", null, "", "1234");
            case "/create" :
                return new ServiceTask("create-task","/create", "creat-queue", null, "", "2345");
            case "/destroy" :
                return new ServiceTask("destroy-task","/destroy", "destroy-queue", null, "", "3456");
            default:
                return  null;
            }
        }

    public boolean AuthenticateAndDispath(ServiceTask serviceTask) {

        String tname =  String.valueOf(new Date().getTime()) + "_auth_";
        try {
            Queue queue = QueueFactory.getQueue("auth-queue");
            queue.add(TaskOptions.Builder.withUrl("/auth")
                    .method(TaskOptions.Method.POST)
                    .taskName(tname));

            queue.add(TaskOptions.Builder.withParam("serviceTask", new Gson().toJson(serviceTask)));


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

