/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.apps.horcu.mootz.scoreSvc;

import com.apps.horcu.mootz.common.BaseEndpoint;
import com.apps.horcu.mootz.common.QueueConductor;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "scoreApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Horatio.example.com",
                ownerName = "backend.myapplication.Horatio.example.com",
                packagePath = ""
        )
)
public class ScoreEndpoint extends BaseEndpoint {

    public ScoreEndpoint(){

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
                .child("score")
                .getRef();
    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "score")
    public ScoreBean score(@Named("name") String name) {
        ScoreBean response = new ScoreBean();
        response.setData("Hi, " + name);
        dbRef.push().setValue(name + " sent a request");

        return response;
    }

}
