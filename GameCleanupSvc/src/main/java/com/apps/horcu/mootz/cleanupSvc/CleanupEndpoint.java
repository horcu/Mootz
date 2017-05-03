/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.apps.horcu.mootz.cleanupSvc;

import com.example.models.ResponseBean;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

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

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayClean")
    public ResponseBean sayClean(@Named("name") String name) {
        ResponseBean response = new ResponseBean();

        Map<String,Object> map = new HashMap<>();
        map.put("message","Welcome to the cleanup micro-service , " + name);
        response.setData(map);
        return response;
    }

}
