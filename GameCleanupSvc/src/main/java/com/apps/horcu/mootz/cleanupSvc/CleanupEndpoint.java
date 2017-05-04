/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.apps.horcu.mootz.cleanupSvc;

import com.apps.horcu.mootz.common.ResponseBean;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.PipedOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "cleanupApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "mootz.horcu.apps.com",
                ownerName = "mootz.horcu.apps.com",
                packagePath = ""
        )
)
public class CleanupEndpoint {

    /** Firebase specific */
    private FirebaseApp mootz = null;
    private DatabaseReference mootzDb;
    private static final String CLEANUPQUEUE = "cleanupQueue";
    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayClean", httpMethod = "POST")
    public ResponseBean sayClean(@Named("name") String name) {

        ResponseBean response = new ResponseBean();
        try {
            //set up the response object


            //check if the connection is good and make a new one if its necessary
            if (mootz == null) {
                if (!InitFirebase()) {
                    return response;
                }
            }

            //get the reference for this service in the db
            mootzDb = FirebaseDatabase
                    .getInstance(mootz)
                    .getReference()
                    .child("cleanup")
                    .getRef();

            Map<String,Object> map = new HashMap<>();
            map.put("message","The cleanup micro-service , " + name);
            mootzDb.push().setValue(map);


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

            Map<String,Object> map = new HashMap<>();
            map.put("message",e.getMessage());

            response.setData(map);
        }
        return response;
    }


    private boolean InitFirebase() {
        FirebaseOptions options;
        boolean result = true;
        try {
            options = new FirebaseOptions.Builder()
                    .setServiceAccount(new FileInputStream("service-account.json"))
                    .setDatabaseUrl("https://mootz-166219.firebaseio.com/")
                    .build();

            mootz = FirebaseApp.initializeApp(options);

        } catch (Exception e) {
            e.printStackTrace();
            result =  false;
        }
        return result;
    }
}
