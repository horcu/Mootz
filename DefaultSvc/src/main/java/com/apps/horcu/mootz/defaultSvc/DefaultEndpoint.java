/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.apps.horcu.mootz.defaultSvc;

import com.apps.horcu.mootz.common.BaseEndpoint;
import com.apps.horcu.mootz.common.QueueConductor;
import com.apps.horcu.mootz.common.Consts;
import com.apps.horcu.mootz.common.ServiceTask;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "defaultApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Horatio.example.com",
                ownerName = "backend.myapplication.Horatio.example.com",
                packagePath = ""
        )
)
public class DefaultEndpoint extends BaseEndpoint {

    public DefaultEndpoint(){

        try {
            options = new FirebaseOptions.Builder()
                    .setServiceAccount(new FileInputStream("/webapp/service-account.json"))
                    .setDatabaseUrl("https://mootz-166219.firebaseio.com/")
                    .build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        mootz = FirebaseApp.initializeApp(options);
        conductor = new QueueConductor();
    }
    /**
     * A simple endpoint method that takes a name and says Hi back
     */

    @ApiMethod(name = "r")
    public DefaultBean r(@Named("route") String route) {
        DefaultBean response = new DefaultBean();
        ServiceTask serviceTask = null;
        try {
            //init
            if (conductor == null) {
                conductor = new QueueConductor();
            }

            //get service task object
            serviceTask = conductor.GetServiceTaskForRoute(route);

            //make sure the path exists
            if (serviceTask == null) {
                response.setError("path not found");
                return response;
            }

            //authenticate the request and dispatch it to the proper queue
            if (shouldAuth(serviceTask)) {
                //set the nextTaskUrl to the path we want to be re directed to when we authenticate
                serviceTask.setNextTaskUrl(serviceTask.getTaskUrl());
                serviceTask.setQueueName(Consts.AUTH_QUEUE);
                //then set the task path to auth so it can do that first
                serviceTask.setTaskUrl(Consts.AUTH_PATH);
            }

            //either way set the previous task url to this. when the request gets there that assignement will be factual
            serviceTask.setPrevTaskUrl(Consts.DEFAULT_PATH);

            //lastly add the task to the queue
            conductor.AddToQueue(serviceTask);

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setData(serviceTask);
        return response;
    }

    private boolean shouldAuth(ServiceTask serviceTask) {
        return serviceTask.getToken().equals("")
                || serviceTask.getToken() == null;
    }
}
