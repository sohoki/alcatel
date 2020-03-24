package com.sohoki.backoffice.alcatel.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sohoki.backoffice.alcatel.service.alcatelServiceInfo;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import com.sohoki.backoffice.alcatel.config.UserConfig;
import com.sohoki.backoffice.alcatel.core.Authentication;
import com.sohoki.backoffice.alcatel.core.CallControl;
import com.sohoki.backoffice.alcatel.core.ClientSession;
import com.sohoki.backoffice.alcatel.core.ClientSubscription;
import com.sohoki.backoffice.alcatel.core.EventPackages;
import com.sohoki.backoffice.alcatel.core.exception.OTRestClientException;


import com.sohoki.backoffice.mapper.UserPhoneInfoManageMapper;
import com.sohoki.backoffice.sym.agt.service.UserPhoneInfoVO;
import com.sohoki.backoffice.sym.agt.web.AgentInfoManageController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service("alcatelServiceInfo")
public class alcatelServiceInfoImpl  extends EgovAbstractServiceImpl  implements alcatelServiceInfo  {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(alcatelServiceInfoImpl.class);
	 
	
	
	@Resource(name="UserPhoneInfoManageMapper")
	private UserPhoneInfoManageMapper phoneInfo;
	
	@Override
	public Boolean userAuthentication(String loginId) throws Exception {
		// TODO Auto-generated method stub
		
		ClientSession userSession = null;
        ClientSubscription userSubscription = null;
        Boolean result = false;
		try{
			UserPhoneInfoVO userInfo = phoneInfo.selectUsserPhoneInfoDetail(loginId);
			UserConfig userConfig  = new UserConfig( loginId,  loginId,   userInfo.getLoginPassword(), userInfo.getPhoneNumber(), userInfo.getPhoneNumber()  ); 
			
			Authentication userAuthentication = new Authentication();
            userAuthentication.authenticate(userConfig);

            // User 1 session manager
            userSession = new ClientSession(userConfig.getLogin(), userAuthentication);
            userSession.open(); // a session is needed for the user to be able to initiate a make-call

            // User 1 subscribes to telephonic events
            userSubscription = new ClientSubscription(userConfig.getLogin(), userAuthentication.getCookie(), userSession);
            userSubscription.subscribe(EventPackages.TELEPHONY.getPackage());
            Future<String> callRefFuture = userSubscription.waitForCallRefInOnCallCreated();
            // Wait before initiating the call
            waitInSeconds(1);

            CallControl userCallControl = new CallControl(userConfig, userAuthentication.getCookie(), userSession);
            
            String callRef = getCallRef(callRefFuture);


            // User 1 (the caller) releases the call
            LOGGER.debug("{} releases the call", userConfig.getLogin());
            userCallControl.releaseCallRequest(callRef);
            

            result = true;
			
		} finally {
			try {
                if (userSubscription != null) {
                	userSubscription.unsubscribe();
                }
            } catch (OTRestClientException e) {
                LOGGER.info("Couldn't close user's 1 subscription : ", e);
            }

            try {
                if (userSession != null) {
                	userSession.close();
                }
            } catch (OTRestClientException e) {
                LOGGER.info("Couldn't close user 1 session : ", e);
            }
        }
		return result;
	}

	@Override
	public Boolean userSessionOut(String loginId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean userStateChange(String loginId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	private static void waitInSeconds(int seconds) {
        try {
            Thread.sleep(TimeUnit.MILLISECONDS.convert(seconds, TimeUnit.SECONDS));
        } catch (InterruptedException ignore) {
        }
    }	
	private static String getCallRef(Future<String> callRefFuture)  throws OTRestClientException {
	        try {
	            return callRefFuture.get(); // The get is blocking
	        } catch (InterruptedException | ExecutionException e) {
	            throw new OTRestClientException("Failure in getCallRef ", e);
	        }
	  }
	
}
