/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.apps.horcu.mootz.cleanupSvc;

import java.io.IOException;

import javax.servlet.http.*;

public class CleanupServlet extends HttpServlet {

    CleanupEndpoint cEp = null;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("Please use the form to POST to this url");

        if(cEp == null){
            cEp = new CleanupEndpoint();
        }

        cEp.sayClean("Getter");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("name");
        resp.setContentType("text/plain");
        if (name == null) {
            resp.getWriter().println("Please enter a name");
        }

        if(cEp == null){
            cEp = new CleanupEndpoint();
        }

        cEp.sayClean("Getter");
        resp.getWriter().println(name + " :now calling Cleanup endpoint"); resp.getWriter().println("Hello " + name);

    }
}
