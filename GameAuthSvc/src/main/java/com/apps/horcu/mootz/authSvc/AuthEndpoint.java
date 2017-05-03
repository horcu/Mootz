/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.apps.horcu.mootz.authSvc;

import com.example.models.ResponseBean;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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
public class AuthEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayNo")
    public AuthBean sayNo(@Named("name") String name) {
        AuthBean response = new AuthBean();


         /* This is for local testing only */
        try {
            String param = "Jessica";
            String url = "http://cleanupSvc.ballrz-7d916.appspot.com/_ah/api/cleanupApi/v1/sayClean/" + param;
            boolean sent = sendRequest(url, new HashMap<String,Object>());

           response.setData(String.valueOf(sent));

        } catch (MalformedURLException e) {
            e.printStackTrace();
            response.setData(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            response.setData(e.getMessage());
        }

        return response;
    }

    private boolean sendRequest(String url, Map<String, Object> reqMap) throws IOException {
        Queue queue = QueueFactory.getDefaultQueue();
        queue.add(TaskOptions.Builder.withUrl(url));
        return true;
    }
}
