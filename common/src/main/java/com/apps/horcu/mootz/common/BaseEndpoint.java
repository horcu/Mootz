package com.apps.horcu.mootz.common;

import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.GsonBuilder;
import com.google.appengine.repackaged.com.google.gson.JsonSyntaxException;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;

import java.io.FileInputStream;

/**
 * Created by Horatio on 5/6/2017.
 */

public class BaseEndpoint {

    /** Firebase specific */
    public FirebaseApp mootz = null;
    public DatabaseReference mootzDb;

    protected FirebaseApp InitFirebase() {
        FirebaseOptions options;
        try {
            options = new FirebaseOptions.Builder()
                    .setServiceAccount(new FileInputStream("service-account.json"))
                    .setDatabaseUrl("https://mootz-166219.firebaseio.com/")
                    .build();

            mootz = FirebaseApp.initializeApp(options);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return mootz;
    }

    protected ServiceTask CreateServiceTaskObject(String servTaskString){
        try {
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(servTaskString, ServiceTask.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
