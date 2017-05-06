/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.apps.horcu.mootz.cleanupSvc;

import com.apps.horcu.mootz.common.BaseEndpoint;
import com.apps.horcu.mootz.common.ResponseBean;
import com.apps.horcu.mootz.common.ServiceTask;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.firebase.database.FirebaseDatabase;

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
public class CleanupEndpoint extends BaseEndpoint {


    private static final String CLEANUPQUEUE = "cleanupQueue";

    /**
     * A simple endpoint method that takes a name and says Hi back
     */

    @ApiMethod(name = "clean")
    public ResponseBean clean(@Named("serviceTask")String serviceTask) {


        ServiceTask sTask = CreateServiceTaskObject(serviceTask);

        ResponseBean response = new ResponseBean();
        try {
            //set up the response object

            //check if the connection is good and make a new one if its necessary
            if (mootz == null) {
                if (InitFirebase() ==null) {
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
            map.put("message","The cleanup micro-service");
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



}
