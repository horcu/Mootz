package com.apps.horcu.mootz.common;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import java.util.Map;

/**
 * Created by Horatio on 5/3/2017.
 */

public class QueueMaster {

    public static boolean AddToDefaultQueue(String urlPath, String key ,String val){

        boolean result = false;
        try {
            Queue queue = QueueFactory.getDefaultQueue();
            queue.add(TaskOptions.Builder.withUrl(urlPath)
            .param(key,val));

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean AddToQueue(String queueName, String urlPath, String key ,String val){

        boolean result = false;
        try {
            Queue queue = QueueFactory.getQueue(queueName);
            queue.add(TaskOptions.Builder.withUrl(urlPath)
                    .param(key,val));

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
