package com.apps.horcu.mootz.common;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.GsonBuilder;

import java.util.Date;

/**
 * Created by Horatio on 5/5/2017.
 */

public class QueueConductor {

    public boolean AddToQueue(ServiceTask serviceTask) throws Exception {

        try {
            Gson gson = new GsonBuilder().create();
            String jsonString = gson.toJson(serviceTask);
            Queue queue = QueueFactory.getQueue(serviceTask.getQueueName());
            queue.add(TaskOptions.Builder.withUrl(serviceTask.getTaskUrl())
                    .method(TaskOptions.Method.POST)
                    .param("serviceTask", jsonString)
                    .taskName(serviceTask.getTaskName()));

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public  ServiceTask GetServiceTaskForRoute(String route) {
        //todo remove all hard coded strings .. every last one

        switch (route){
            case "/r" :
                return new ServiceTask("default","/r", Consts.DEFAULT_QUEUE, "def_1", "111111");
            case "/clean" :
                return new ServiceTask("clean-task","/clean", Consts.CLEAN_QUEUE, "cl_1234567890", "1234");
            case "/create" :
                return new ServiceTask("create-task","/create", Consts.CREATE_QUEUE,  "", "2345"); // no token
            case "/destroy" :
                return new ServiceTask("destroy-task","/destroy", Consts.DESTROY_QUEUE,  "de_1234567890", "3456");
            case "/auth" :
                return new ServiceTask("auth-task","/a", Consts.AUTH_QUEUE,  "a_1000000009", "9876");
            case "/pub" :
                return new ServiceTask("pubsub-task","/pub", Consts.PUBSUB_QUEUE,  "ps_1000000009p", "98762");
            case "/sub" :
                return new ServiceTask("pubsub-task","/sub", Consts.PUBSUB_QUEUE,  "ps_1000000009s", "98763");
            default:
                return  null;
            }
        }
}

