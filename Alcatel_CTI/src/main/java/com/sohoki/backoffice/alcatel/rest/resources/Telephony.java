package com.sohoki.backoffice.alcatel.rest.resources;


import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;

import com.sohoki.backoffice.alcatel.core.ClientSession;
import com.sohoki.backoffice.alcatel.core.exception.OTRestClientException;
import com.sohoki.backoffice.alcatel.rest.data.v1_0.telephonyschema.AnswerCallRequest;
import com.sohoki.backoffice.alcatel.rest.data.v1_0.telephonyschema.Calls;
import com.sohoki.backoffice.alcatel.rest.data.v1_0.telephonyschema.MakeCallRequest;
import com.sohoki.backoffice.alcatel.transport.JerseyRequests;

/**
 * <pre>
 *  Rest level for telephonic services.
 *  Telephonic requests are Rest formatted.
 * </pre>
 */
public class Telephony {

    private final Cookie cookie;
    private final String telephonyPath;

    public Telephony(Cookie cookie, ClientSession clientSession) {
        this.cookie = cookie;
        this.telephonyPath = clientSession.getServicePath(Services.TELEPHONY.getService());
    }

    public void makeCall(MakeCallRequest makeCallRequest) throws OTRestClientException {

        JerseyRequests.post(telephonyPath, cookie, makeCallRequest, Response.Status.CREATED);
    }

    public Calls getCalls() throws OTRestClientException {

        Response response = JerseyRequests.get(telephonyPath, cookie, Response.Status.OK);
        return response.readEntity(Calls.class);
    }

    public void answerCall(String callRef, AnswerCallRequest answerCallRequest) throws OTRestClientException {

        JerseyRequests.post(telephonyPath + "/" + callRef + "/answer", cookie, answerCallRequest, Response.Status.NO_CONTENT);
    }

    public void terminateCalls(String callRef) throws OTRestClientException {

        JerseyRequests.delete(telephonyPath + "/" + callRef, cookie, Response.Status.NO_CONTENT);
    }
}
