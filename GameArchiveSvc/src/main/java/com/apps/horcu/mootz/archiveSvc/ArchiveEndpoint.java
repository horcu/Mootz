/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.apps.horcu.mootz.archiveSvc;

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
        name = "archiveApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "mootz.horcu.apps.com",
                ownerName = "mootz.horcu.apps.com",
                packagePath = ""
        )
)
public class ArchiveEndpoint {

    /** Firebase specific */
    private FirebaseApp mootz = null;
    private DatabaseReference mootzDb;

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi")
    public ArchiveBean sayHi(@Named("name") String name) {
        ArchiveBean response = new ArchiveBean();
        response.setData("Hi, " + name);

        return response;
    }

}
