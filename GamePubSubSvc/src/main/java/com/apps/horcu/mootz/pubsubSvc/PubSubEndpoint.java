/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.apps.horcu.mootz.pubsubSvc;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "pubsubApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Horatio.example.com",
                ownerName = "backend.myapplication.Horatio.example.com",
                packagePath = ""
        )
)
public class PubSubEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "pub")
    public PubSubBean pub(@Named("name") String name) {
        PubSubBean response = new PubSubBean();
        response.setData("Hi, " + name);

        return response;
    }

    @ApiMethod(name = "sub")
    public PubSubBean sub(@Named("name") String name) {
        PubSubBean response = new PubSubBean();
        response.setData("Hi, " + name);

        return response;
    }
}
