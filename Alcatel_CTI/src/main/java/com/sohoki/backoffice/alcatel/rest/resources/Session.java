package com.sohoki.backoffice.alcatel.rest.resources;


import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;

import com.sohoki.backoffice.alcatel.core.exception.OTRestClientException;
import com.sohoki.backoffice.alcatel.rest.data.v1_0.sessionschema.SessionInfo;
import com.sohoki.backoffice.alcatel.rest.data.v1_0.sessionschema.SessionRequest;
import com.sohoki.backoffice.alcatel.transport.JerseyRequests;

public class Session {

    private static final String APPLICATION_NAME = "TEST_API";
    
    public SessionInfo openSession(String path, Cookie cookie) throws OTRestClientException {

        SessionRequest sessionRequest = new SessionRequest();
        sessionRequest.setApplicationName(APPLICATION_NAME);
        Response response = JerseyRequests.post(path, cookie, sessionRequest, Response.Status.OK);
        return response.readEntity(SessionInfo.class);
    }

    public void keepAlive(String path, Cookie cookie) throws OTRestClientException {

        String keepalivePath = path + "/keepalive";
        SessionRequest sessionRequest = new SessionRequest();
        sessionRequest.setApplicationName(APPLICATION_NAME);
        JerseyRequests.post(keepalivePath, cookie, sessionRequest, Response.Status.NO_CONTENT);
    }

    public void closeSession(String path, Cookie cookie) throws OTRestClientException {

        JerseyRequests.delete(path, cookie, Response.Status.NO_CONTENT);
    }
}
