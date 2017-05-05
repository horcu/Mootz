/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.apps.horcu.mootz.authSvc;

import com.apps.horcu.mootz.common.Conductor;
import com.apps.horcu.mootz.common.IQueueService;
import com.apps.horcu.mootz.common.ServiceTask;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

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
    Conductor conductor = null;

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "a")
    public AuthBean a(@Named("serviceTask")String serviceTask) {

        ServiceTask sTask = new Gson().fromJson(serviceTask, ServiceTask.class);
        //create the response bean
        AuthBean response = new AuthBean();

        try {

        //auth the service call first
        if (!auth(sTask.getUserId())) {
            response.setError("bad token");
            return response;
        }

        //set auth to true
        sTask.setIsAuthenticated(true);

            //set the auth token
        sTask.setToken(UUID.randomUUID() + "_" + String.valueOf(new Date().getTime()));

        //make sure the conductor isn't null
            if (conductor == null)
                conductor = new Conductor();

            //create the cleanup task add to queue
            boolean jobDone = conductor.AddToQueue(sTask);

            //set the response data
            response.setData(String.valueOf(jobDone));

        } catch (Exception e) {
            e.printStackTrace();
            response.setData(e.getMessage());
        }
        return response;
    }

    private boolean auth(String token) {
        return true;
    }


}
