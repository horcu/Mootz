/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.apps.horcu.mootz.authSvc;

import com.apps.horcu.mootz.common.BaseEndpoint;
import com.apps.horcu.mootz.common.QueueConductor;
import com.apps.horcu.mootz.common.ServiceTask;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonSyntaxException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
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
public class AuthEndpoint extends BaseEndpoint {

    public AuthEndpoint(){

        try {
            options = new FirebaseOptions.Builder()
                    .setServiceAccount(new FileInputStream("service-account.json"))
                    .setDatabaseUrl("https://mootz-166219.firebaseio.com/")
                    .build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        mootz = FirebaseApp.initializeApp(options);
        conductor = new QueueConductor();

        //get the reference for this service in the db
        dbRef = FirebaseDatabase
                .getInstance(mootz)
                .getReference()
                .child("auth")
                .getRef();
    }
    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "a")
    public AuthBean a(@Named("serviceTask")String serviceTask) {

        ServiceTask sTask = null;

        //create the response bean
        AuthBean response = new AuthBean();
        try {

            //deserialize the serviceTask string
         sTask = CreateServiceTaskObject(serviceTask);

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
                conductor = new QueueConductor();

            //create the cleanup task add to queue
           if(conductor.AddToQueue(sTask))
            response.setData(sTask);
            else {
               response.setData(new ServiceTask());
               response.setError("could not add " +  sTask.getTaskName()  + " serviceTask to " + sTask.getQueueName());
           }

        }
        catch (IllegalStateException | JsonSyntaxException exception) {
            response.setError(exception.getMessage());

        }
        catch (Exception e) {
            e.printStackTrace();
            response.setData(sTask);
        }
        return response;
    }

    private boolean auth(String token) {
        return true;
    }


}
