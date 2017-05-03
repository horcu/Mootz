/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.apps.horcu.mootz.defaultSvc;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;

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
public class DefaultEndpoint {
    /** Firebase specific */
    private FirebaseApp mootz = null;
    private DatabaseReference mootzDb;


    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "r")
    public MyBean r(@Named("route") String route) {
        MyBean response = new MyBean();

        //todo here we need to forward the traffic accordingly ????

        response.setData(route);
        return response;
    }

}
