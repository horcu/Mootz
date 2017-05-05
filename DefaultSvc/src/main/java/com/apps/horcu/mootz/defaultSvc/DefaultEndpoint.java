/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.apps.horcu.mootz.defaultSvc;

import com.apps.horcu.mootz.common.Conductor;
import com.apps.horcu.mootz.common.ServiceTask;
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
    /**
     * Firebase specific
     */
    private FirebaseApp mootz = null;
    private DatabaseReference mootzDb;
    private Conductor conductor = null;

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
                conductor = new Conductor();
            }

            //get service task object
            serviceTask = conductor.getTaskForRoute(route);

            //make sure the path exists
            if (serviceTask == null) {
                response.setError("path not found");
                return response;
            }

            //authenticate the request and dispath it to the proper queue
            conductor.AuthenticateAndDispath(serviceTask);

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setData(serviceTask);
        return response;
    }
}
