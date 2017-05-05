package com.apps.horcu.mootz.common;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.GsonBuilder;
import com.google.appengine.repackaged.com.google.gson.internal.ObjectConstructor;
import com.google.appengine.repackaged.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Horatio on 5/5/2017.
 */

public class Conductor {

    public boolean AddToQueue(ServiceTask serviceTask) throws Exception {

        String tName = String.valueOf(new Date().getTime()) + "_" + serviceTask.getTaskName();
        try {
            Queue queue = QueueFactory.getQueue(serviceTask.getQueueName());
            queue.add(TaskOptions.Builder.withUrl(serviceTask.getTaskUrl())
                    .method(TaskOptions.Method.POST)
                    .taskName(tName));

//            Map<String, String> params = serviceTask.getParams();
//            if(params!= null) {
//                for (int i = 0; i < params.keySet().size(); i++) {
//                    String key = String.valueOf(params.keySet().toArray()[i]);
//                    String value = String.valueOf(params.entrySet().toArray()[i]);
//
//                    queue.add(TaskOptions.Builder.withParam(key, value));
//                }
//            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public  ServiceTask getTaskForRoute(String route) {

        switch (route){
            case "/clean" :
                return new ServiceTask("clean-task","/clean", "clean-queue", "cl_1234567890", "1234");
            case "/create" :
                return new ServiceTask("create-task","/create", "create-queue",  "cr_1234567890", "2345");
            case "/destroy" :
                return new ServiceTask("destroy-task","/destroy", "destroy-queue",  "de_1234567890", "3456");
            default:
                return  null;
            }
        }

    public boolean AuthenticateThenAddToQueue(ServiceTask serviceTask) {

        String tName =  String.valueOf("auth_" +  new Date().getTime());
        try {
            Queue queue = QueueFactory.getQueue("a-queue");
            queue.add(TaskOptions.Builder.withUrl("/auth")
                    .method(TaskOptions.Method.POST)
                    .taskName(tName));

            Gson gson = new GsonBuilder().create();

            String jsonString = gson.toJson(serviceTask);
            queue.add(TaskOptions.Builder.withParam("serviceTask", jsonString));

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

