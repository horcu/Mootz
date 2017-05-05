package com.apps.horcu.mootz.common;

import java.util.Map;

/**
 * Created by ray on 5/4/17.
 */

public interface IQueueService {

    String AddToQueue(String taskName, String queueName, String urlPath, Map<String , String> paramsMap);

}
