package com.sohoki.backoffice.alcatel.service.impl;



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


import com.sohoki.backoffice.mapper.TelephoneInfoManageMapper;
import com.sohoki.backoffice.mapper.TranInfoManagerMapper;
import com.sohoki.backoffice.mapper.UserPhoneInfoManageMapper;
import com.sohoki.backoffice.sym.agt.service.TelephoneInfoVO;
import com.sohoki.backoffice.sym.agt.service.UserPhoneInfoVO;
import com.sohoki.backoffice.sym.agt.web.AgentInfoManageController;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.ChunkedInput;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.filter.LoggingFilter;

import javax.net.ssl.*;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;






import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import java.util.concurrent.*;


@Service("alcatelServiceInfo")
public class alcatelServiceInfoImpl  extends EgovAbstractServiceImpl  implements alcatelServiceInfo  {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(alcatelServiceInfoImpl.class);
	 
	
    Client client;
    WebTarget webTarget;
    
    public static final String USER_COOKIE_NAME = "AlcUserId";
    
    
    
	@Resource(name="UserPhoneInfoManageMapper")
	private UserPhoneInfoManageMapper phoneInfo;
	
	@Resource(name="TelephoneInfoManageMapper")
	private TelephoneInfoManageMapper telManageInfo;
	
    @Resource(name = "fileProperties")
	private Properties fileProperties;

	
	@Override
	public Boolean userAuthentication(String userId, String seatId , String telProcessGubun ) throws Exception {
		// TODO Auto-generated method stub
		
		ClientSession userSession = null;
        ClientSubscription userSubscription = null;
        Boolean result = false;
        TelephoneInfoVO telInfoVO = telManageInfo.selectAgentPageInfoManageDetailSeatId(seatId);
        
        
		try{
			
			
			
			
			
			UserPhoneInfoVO userInfo = phoneInfo.selectUsserPhoneInfoDetail(userId);
			
			// 관리자 로그인 아이디, 패스워드, 전화번호, 디바이스 아이디
			UserConfig userConfig  = new UserConfig(fileProperties.getProperty("roxe.adminId")  , null , fileProperties.getProperty("roxe.adminPwd")  , userInfo.getPhoneNumber(), null ); 
			
			//인증
			Authentication userAuthentication = new Authentication();
			userAuthentication.authenticate(userConfig);            // User 1 session manager
			//세션 정리
			userSession = new ClientSession(userConfig.getLogin(), userAuthentication);
            userSession.open(); // a session is needed for the user to be able to initiate a make-call
            //전화기 처리 관련 내용 정리
            
            
            
            
            
            
            /*LOGGER.debug("4--------------------------------------------------------------------------------------------------------------------");
            // User 1 subscribes to telephonic events
            userSubscription = new ClientSubscription(userConfig.getLogin(), userAuthentication.getCookie(), userSession);
            LOGGER.debug("5--------------------------:" + EventPackages.TELEPHONY.getPackage());
            userSubscription.subscribe(EventPackages.TELEPHONY.getPackage());
            LOGGER.debug("6--------------------------------------------------------------------------------------------------------------------");
            */
            
            //object put 전송으로 mac 삭제
            
            
            /*1
            Future<String> callRefFuture = userSubscription.waitForCallRefInOnCallCreated();
            LOGGER.debug("7--------------------------------------------------------------------------------------------------------------------");
            // Wait before initiating the call
            waitInSeconds(1);
            LOGGER.debug("8--------------------------------------------------------------------------------------------------------------------");
            CallControl userCallControl = new CallControl(userConfig, userAuthentication.getCookie(), userSession);
            LOGGER.debug("{} releases the call", userConfig.getLogin());
            String callRef = getCallRef(callRefFuture);

            
            // User 1 (the caller) releases the call
            userCallControl.releaseCallRequest(callRef);*/
            
            
            
            //변경값 넣기 
            TelephoneInfoVO info = new TelephoneInfoVO();
            info.setAgentNownumber(userInfo.getPhoneNumber());
            info.setAgentState("PHONE_STATE_2");
            info.setAuthCookie(  "" );
            info.setAgentCode(telInfoVO.getAgentCode());
            telManageInfo.updateAgentChangeNumber(info);
            
            result = true;
			
		} finally {
			try {
                if (userSubscription != null) {
                	LOGGER.info("logoutuserSubscription");
                	userSubscription.unsubscribe();
                }
            } catch (OTRestClientException e) {
                LOGGER.info("Couldn't close user's 1 subscription : ", e);
            }

            try {
                if (userSession != null) {
                	
                	//인증 쿠기값 전송
                	TelephoneInfoVO info = new TelephoneInfoVO();
                    info.setAgentNownumber(telInfoVO.getAgentBasicnumber());
                    info.setAgentState("PHONE_STATE_4");
                    info.setAgentCode(telInfoVO.getAgentCode());
                    info.setAuthCookie("");
                    telManageInfo.updateAgentChangeNumber(info);
                    LOGGER.info("userSession close Session");
                	userSession.close();
                }
            } catch (OTRestClientException e) {
                LOGGER.info("Couldn't close user 1 session : ", e);
            }
        }
		return result;
	}

	@Override
	public Boolean userSessionOut(String seatId) throws Exception {
		// TODO Auto-generated method stub
		Boolean result = false;
		try{
			/*userSubscription.unsubscribe();
			userSession.close();*/
			
			client = createClient();
	        webTarget = client.target(fileProperties.getProperty("roxe.hostRootPath"));

	        TelephoneInfoVO telInfoVO = telManageInfo.selectAgentPageInfoManageDetailSeatId(seatId);
	        
	        
	        Cookie spCookie = authenticate(telInfoVO.getLoginId(), telInfoVO.getLoginPwd());

	        // II) open session:

	        openSession(spCookie);

	        // III) subscribe to some events, and wait for a call-ref:
     
	        Subscription subscription = subscribe(telInfoVO.getLoginId(), spCookie);
	        
	        unsubscribe(telInfoVO.getLoginId(), spCookie, subscription.subscriptionId);

	        Thread.sleep(1000L);

	        closeSession(spCookie);

	        client.close();
	        
	        result = true;
	        
		}catch(Exception e){
			LOGGER.debug("error userSessionOut:" + e.toString());
			result = false;
		}
		return result;
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
    //인증 하기 
	@Override
	public Boolean userSubscriptionRaw(String userId, String seatNm) 	throws Exception {
		Boolean result = false;
		try{
			
			UserPhoneInfoVO userInfo = phoneInfo.selectUsserPhoneInfoDetail(userId);
			TelephoneInfoVO telInfoVO = telManageInfo.selectAgentPageInfoManageDetail(seatNm);
			
			
			client = createClient();
	        webTarget = client.target(fileProperties.getProperty("roxe.hostRootPath"));
	        //쿠키 저장
	        Cookie spCookie = authenticate(telInfoVO.getLoginId(), telInfoVO.getLoginPwd());
	        openSession(spCookie);
	        Subscription subscription = subscribe(  telInfoVO.getLoginId() , spCookie);
	        //Subscription subscription = subscribe(User.SP.login, spCookie);
	        
	        
	        unsubscribe(telInfoVO.getLoginId()  , spCookie, subscription.subscriptionId);

	        Thread.sleep(1000L);

	        closeSession(spCookie);

	        client.close();
	        
		}catch(Exception e){
			
		}
		
		
		return result;
	}
	
	@Override
	public Boolean userSubscriptionSessionOut(String loginId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	private Subscription subscribe(String login, Cookie cookie) {
       
        System.out.println("Subscribe for " + login);
        
        // subscribing to "telephony" package
        String version = "1.0";
        String filter = "{\"selectors\":[{\"ids\":[\"" + login + "\"],\"names\":[\"telephony\"],\"families\":[],\"origins\":[]}]}";
        final String subscriptionRequest = "{\"notificationUrl\":null"
                + ",\"filter\":" + filter
                + ",\"version\":\"" + version + '\"'
                + '}';
       
        return webTarget.path("subscriptions").request().cookie(cookie).post(Entity.json(subscriptionRequest), Subscription.class);
        //return webTarget.path(arg0)
    }
	//전화기 사용자 인증 
	private Subscription sendJson(String login, Cookie cookie, String seatNm, String sendGubun) throws Exception {
		
		TelephoneInfoVO telInfoVO = telManageInfo.selectAgentPageInfoManageDetail(seatNm);
		UserPhoneInfoVO userInfo = phoneInfo.selectUsserPhoneInfoDetail(login);
		String pathUrl = "1.0/pbxs/"+  telInfoVO.getNodeInfo() +"/instances/Subscriber/"+ userInfo.getPhoneNumber() +  "/Tsc_IP_subscriber/"+ userInfo.getPhoneNumber();
		String jsonR = sendGubun.equals("OT")  ? "" :  telInfoVO.getAgentMac() ;
		String sendJson =  "{ \"attributes\" : [ { \"name\": \"Ethernet_Address\", \"value\": [\""+  jsonR + "\"] } ] } "; 
	    if (sendGubun.equals("INFO")){
	    	return webTarget.path(pathUrl).request().cookie(cookie).post(Entity.json(sendJson), Subscription.class);
	    }else {
	    	return webTarget.path(pathUrl).request().cookie(cookie).put(Entity.json(sendJson), Subscription.class);
	    }
		
	}
	//작업 해제
	private void unsubscribe(final String login, final Cookie cookie, String subscriptionId) {

        System.out.println("Unsubscribe for " + login);

        Response response = webTarget.path("subscriptions").path(subscriptionId).request().cookie(cookie).delete();

        System.out.println("Unsubscribe response: " + response);
    }
	private static Client createClient() throws KeyManagementException, IOException {

        ClientConfig clientConfig = new ClientConfig(JacksonJsonProvider.class);
        clientConfig.register(new LoggingFilter());

        SSLContext sslContext = SslConfigurator.newInstance().createSSLContext();
        sslContext.init(null, new TrustManager[ ] {
        	    new X509TrustManager() {
        	    	@Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
        	    	@Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                    }
        	    	@Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                    }
                }
        }, new SecureRandom());

        Client client = ClientBuilder.newBuilder().withConfig(clientConfig).hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        }).sslContext(sslContext).build();

        client.property(ClientProperties.FOLLOW_REDIRECTS, Boolean.FALSE);

        return client;
    }
	private Future<String> getFirstCallRef(final String privateUrlForChunks) {

        System.out.println("for polling: " + privateUrlForChunks);

        ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                0L, TimeUnit.SECONDS,  // so the created thread will die after the task has been executed
                new SynchronousQueue<Runnable>());

        Future<String> result = executorService.submit(new Callable<String>() {
            @Override
            public String call() {
                Response response = client.target(privateUrlForChunks).request().get();
                ChunkedInput<JsonNode> chunkedInput = response.readEntity(new GenericType<ChunkedInput<JsonNode>>() { });
                JsonNode chunk;
                // waiting for the first callRef:
                while ((chunk = chunkedInput.read()) != null) {
                    System.out.println("Next chunk received: " + chunk);
                    String callRef = chunk.get("callRef").getTextValue();
                    if (callRef != null) {
                        System.out.println("callRef = " + callRef);
                        return callRef;
                    }
                }
                return null;
            }
        });

        return result;
    }
	private void openSession(Cookie cookie)  {

        class SessionRequest { public String getApplicationName() { return "Test API"; } }

        Response response = webTarget.path("sessions").request().cookie(cookie).post(Entity.json(new SessionRequest()));
        LOGGER.debug("open response:" + response);
    }


    private void closeSession(Cookie cookie) {

        Response response = webTarget.path("sessions").request().cookie(cookie).delete();
        LOGGER.debug("close response:" + response);
       
    }
    //쿠기 생성
    private Cookie authenticate(String login, String password) {

        Response response1 = client.target(fileProperties.getProperty("roxe.hosturl")).request().get();
        LOGGER.debug("response1:" + response1) ;
        
        JsonNode jsonNode1 = response1.readEntity(JsonNode.class);
        String publicUrl = jsonNode1.get("versions").get(0).get("publicUrl").getTextValue();
        LOGGER.debug("public Url:" + response1);
        
        Response response2 = client.target(publicUrl).request().get();
        LOGGER.debug("response2:" + response2);
        
        Response response3 = client.target(response2.getLocation()).register(HttpAuthenticationFeature.basic(login, password)).request().get();
        LOGGER.debug("response3:" + response3);

        Map<String, NewCookie> cookies = response3.getCookies();
       
        Cookie result = cookies.get(USER_COOKIE_NAME);
        LOGGER.debug("cookie:" + result);
        return result;
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
    static class Subscription {
        String privatePollingUrl;
        String subscriptionId;

        void setPrivatePollingUrl(String privatePollingUrl) {
            this.privatePollingUrl = privatePollingUrl;
        }

        void setSubscriptionId(String subscriptionId) {
            this.subscriptionId = subscriptionId;
        }
    }
	
}
