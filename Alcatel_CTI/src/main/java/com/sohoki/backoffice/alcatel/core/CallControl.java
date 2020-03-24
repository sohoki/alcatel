package com.sohoki.backoffice.alcatel.core;


import javax.ws.rs.core.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sohoki.backoffice.alcatel.config.UserConfig;
import com.sohoki.backoffice.alcatel.core.exception.OTRestClientException;
import com.sohoki.backoffice.alcatel.rest.data.v1_0.telephonyschema.AnswerCallRequest;
import com.sohoki.backoffice.alcatel.rest.data.v1_0.telephonyschema.Call;
import com.sohoki.backoffice.alcatel.rest.data.v1_0.telephonyschema.Calls;
import com.sohoki.backoffice.alcatel.rest.data.v1_0.telephonyschema.MakeCallRequest;
import com.sohoki.backoffice.alcatel.rest.resources.Telephony;

/**
 * Telephonic oriented class<br>
 *     It gives access to basic telephonic features like make a call or release it.<br>
 *     It is high level: it builds request to be used by lower level functions.
 */
public class CallControl {

    private static final Logger LOGGER = LoggerFactory.getLogger(CallControl.class);

    private final String userLogin;
    private final String userDeskPhone;
    private final Telephony telephony;

    public CallControl(UserConfig userConfig, Cookie cookie, ClientSession clientSession) {
        this.userLogin = userConfig.getLogin();
        this.userDeskPhone = userConfig.getDeskPhone();
        telephony = new Telephony(cookie, clientSession);
    }

    /**
     * Initiate a simple call.<br>
     *   The method takes the caller's desk phone (given by configuration) to call the given user.
     *
     * @param calleeUserConfig the user to call
     * @throws sample.core.exception.OTRestClientException when the call cannot be initiated
     */
    public void makeCallRequest(UserConfig calleeUserConfig) throws OTRestClientException {

        final String callerDeviceNumber = userDeskPhone;
        final String calleeDirectoryNumber = calleeUserConfig.getNumber();

        LOGGER.debug("Make a call request for user " + userLogin + " from device {} calling user {}",
                callerDeviceNumber, calleeDirectoryNumber);

        //Build the request and send it
        telephony.makeCall(new MakeCallRequest(callerDeviceNumber, calleeDirectoryNumber));
    }

    /**
     * Answer the call with the given callRef.
     *
     * @param callRef is used to identify the call to answer
     * @throws sample.core.exception.OTRestClientException the call cannot be answered
     */
    public void answerCallRequest(String callRef) throws OTRestClientException {

        final String callerDeviceNumber = userDeskPhone;

        LOGGER.debug("User {} answers the current incoming call with device {}.", userLogin, callerDeviceNumber);

        //Build the request
        AnswerCallRequest answerCallRequest = new AnswerCallRequest();
        answerCallRequest.setDeviceId(callerDeviceNumber);
        telephony.answerCall(callRef, answerCallRequest);
    }

    /**
     * Release all the ongoing calls associated to the user.
     *
     * @throws sample.core.exception.OTRestClientException when one of the calls cannot be released
     */
    public void releaseAllCallsRequest() throws OTRestClientException {

        LOGGER.debug("Release all current calls for user {}", userLogin);
        Calls calls = telephony.getCalls();
        for (Call call : calls.getCalls()) {
            telephony.terminateCalls(call.getCallRef());
        }
    }

    /**
     * Release the call with the given callRef associated to the user.
     *
     * @param callRef identifies a call
     * @throws sample.core.exception.OTRestClientException the call cannot be released
     */
    public void releaseCallRequest(String callRef) throws OTRestClientException {

        LOGGER.debug("Release call for user {} with {} callRef", userLogin, callRef);
        telephony.terminateCalls(callRef);
    }
}