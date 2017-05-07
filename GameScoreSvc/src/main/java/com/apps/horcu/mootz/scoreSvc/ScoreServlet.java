/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.apps.horcu.mootz.scoreSvc;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ScoreServlet extends HttpServlet {

    ScoreEndpoint cEp = null;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String serviceTask = req.getParameter("serviceTask");
        resp.setContentType("text/plain");

        resp.getWriter().println("Please use the form to POST to this url");

        if(cEp == null){
            cEp = new ScoreEndpoint();
        }

        cEp.score(serviceTask);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String serviceTask = req.getParameter("serviceTask");

        if (serviceTask == null) {
            resp.getWriter().println("Please enter a name");
        }

        if(cEp == null){
            cEp = new ScoreEndpoint();
        }

        cEp.score(serviceTask);
        resp.setContentType("text/plain");
        resp.getWriter().println("done!");

    }
}
