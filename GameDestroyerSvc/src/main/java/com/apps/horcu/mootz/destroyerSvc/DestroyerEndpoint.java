/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.apps.horcu.mootz.destroyerSvc;

import com.apps.horcu.mootz.common.BaseEndpoint;
import com.apps.horcu.mootz.common.QueueConductor;
import com.apps.horcu.mootz.common.ServiceTask;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "destroyApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "mootz.horcu.apps.com",
                ownerName = "mootz.horcu.apps.com",
                packagePath = ""
        )
)
public class DestroyerEndpoint extends BaseEndpoint {

    public DestroyerEndpoint(){

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

        //get the reference for this service in the db
        dbRef = FirebaseDatabase
                .getInstance(mootz)
                .getReference()
                .child("destroy")
                .getRef();
    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "destroy")
    public MyBean destroy(@Named("serviceTask")String serviceTask) {

        MyBean response = new MyBean();
        try {

            ServiceTask sTask = CreateServiceTaskObject(serviceTask);
            //check if the connection is good and make a new one if its necessary
            if (mootz == null) {
                if (InitFirebase() !=null) {
                    return response;
                }
            }

            //get the reference for this service in the db
            dbRef = FirebaseDatabase
                    .getInstance(mootz)
                    .getReference()
                    .child("destroy")
                    .getRef();

            Map<String, Object> map = new HashMap<>();
            map.put("message", "The destroy micro-service");
            dbRef.push().setValue(map);


            response.setData(map);

        } catch (Exception e) {
            e.printStackTrace();
            FirebaseDatabase
                    .getInstance(mootz)
                    .getReference()
                    .child("errors")
                    .getRef()
                    .push()
                    .setValue(e.getMessage());

            Map<String, Object> map = new HashMap<>();
            map.put("message", e.getMessage());

            response.setData(map);


        }
        return response;
    }
}
