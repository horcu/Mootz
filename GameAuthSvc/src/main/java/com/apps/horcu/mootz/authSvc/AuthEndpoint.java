/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.apps.horcu.mootz.authSvc;

import com.apps.horcu.mootz.common.IQueueService;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;
import java.util.Map;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "authApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "mootz.horcu.apps.com",
                ownerName = "mootz.horcu.apps.com",
                packagePath = ""
        )
)
public class AuthEndpoint {

    /**
     * Firebase specific
     */
    private FirebaseApp mootz = null;
    private DatabaseReference mootzDb;

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayNo")
    public AuthBean sayNo(@Named("name") String name) {
        AuthBean response = new AuthBean();

        try {

            //create the task add to queue
            String sent = AddToQueue("cleanupTask", "cleanup-queue", "/sayClean", null);

            response.setData(String.valueOf(sent));

        } catch (Exception e) {
            e.printStackTrace();
            response.setData(e.getMessage());
        }
        return response;
    }

    private String AddToQueue(String taskName, String queueName, String urlPath, Map<String , String> paramsMap) {

        String result;

        String tName = String.valueOf(new Date().getTime()) + "_" + taskName;
        try {
            Queue queue = QueueFactory.getQueue(queueName);
            queue.add(TaskOptions.Builder.withUrl(urlPath)
                    .method(TaskOptions.Method.POST)
                    .taskName(tName));

            if(paramsMap!= null) {
                for (int i = 0; i < paramsMap.keySet().size(); i++) {
                    String key = String.valueOf(paramsMap.keySet().toArray()[i]);
                    String value = String.valueOf(paramsMap.entrySet().toArray()[i]);

                    queue.add(TaskOptions.Builder.withParam(key, value));
                }
            }

            result = "true";
        } catch (Exception e) {
            e.printStackTrace();
            result = e.getMessage();
        }
        return result;
    }
}
