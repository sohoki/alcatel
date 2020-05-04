package com.sohoki.backoffice.alcatel.service.impl;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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


import com.sohoki.backoffice.mapper.ErrorInfoManageMapper;
import com.sohoki.backoffice.mapper.TelephoneInfoManageMapper;
import com.sohoki.backoffice.mapper.TranInfoManagerMapper;
import com.sohoki.backoffice.mapper.UserPhoneInfoManageMapper;
import com.sohoki.backoffice.sts.error.service.ErrorInfo;
import com.sohoki.backoffice.sts.error.service.ErrorInfoManageService;
import com.sohoki.backoffice.sts.error.service.ErrorInfoVO;
import com.sohoki.backoffice.sym.agt.service.TelephoneInfoVO;
import com.sohoki.backoffice.sym.agt.service.UserPhoneInfoVO;
import com.sohoki.backoffice.sym.agt.web.AgentInfoManageController;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.SecureRandom;
import java.util.List;
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
    
	@Resource(name="TelephoneInfoManageMapper")
	private TelephoneInfoManageMapper agentMapper;
	
	
	@Autowired
	private ErrorInfoManageMapper errorMapper;

	
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
            userSubscription = new ClientSubscription(userConfig.getLogin(), userAuthentication.getCookie(), userSession);
        
            //변경값 넣기 
            TelephoneInfoVO info = new TelephoneInfoVO();
            info.setAgentNownumber(userInfo.getPhoneNumber());
            info.setAgentState("PHONE_STATE_2");
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
	public String telelPhoneStateChange(String loginId, String seatName, String state) throws Exception {
		// TODO Auto-generated method stub
		String result = "";
		try{
			
			 TelephoneInfoVO telInfoVO = telManageInfo.selectAgentPageInfoManageDetailSeatId(seatName);
	    	 UserPhoneInfoVO userInfo = phoneInfo.selectUsserPhoneInfoDetail(loginId);
			
	    	 //우선 전화기 변경 작업 시간 확인
	    	 // 사용자가 다른 번호를 사용 하고 있는지 확인
	    	 
	    	 
	    	 if (telInfoVO == null || userInfo ==  null ){
	    		 result = "Insufficient information";
	    	 }else {
	    		
	    		client = createClient();
	 	        webTarget = client.target(fileProperties.getProperty("roxe.hostRootPath"));
	 	        Cookie spCookie = authenticate(fileProperties.getProperty("roxe.adminId"), fileProperties.getProperty("roxe.adminPwd"));
	 	        try{
	 	        	openSession(spCookie);
	 	        }catch(Exception e1){
	 	        	LOGGER.debug("open_Session Error:" + e1.toString());
	 	        }
	 	        
	 	       
	 	        String macAddress = telInfoCheck( telInfoVO.getNodeInfo(),   spCookie, userInfo.getPhoneNumber());
	     	
	 	        TelephoneInfoVO info = new TelephoneInfoVO();
	            
	            
	            info.setAgentCode(telInfoVO.getAgentCode());
	        
	 	        
	 	        if (!macAddress.equals("") && state.equals("OT")) {
	 	           
	 	    	   Subscription subscription = sendJson( telInfoVO.getNodeInfo() , spCookie, userInfo.getPhoneNumber(),  "");
	 	    	   result = "OK";
	 	    	   info.setAgentState("PHONE_STATE_4");
	 	    	   
	 	    	   LOGGER.debug("PHONE_STATE_4 ---------------------------------------------------------");
	 	    	   info.setAgentNownumber( "" );
	 	    	   telManageInfo.updateAgentChangeNumber(info);
	 	    	   
	 	        }else if (macAddress.equals("") && state.equals("IN")) {
	 	        	//시간 체크 후 확인 하기 
	 	          Subscription subscription = sendJson( telInfoVO.getNodeInfo() , spCookie, userInfo.getPhoneNumber(),  telInfoVO.getAgentMac());
	 	          result = "OK";
	 	    	  info.setAgentState("PHONE_STATE_2");  
	 	    	  info.setAgentNownumber( userInfo.getPhoneNumber() );
	 	    	  telManageInfo.updateAgentChangeNumber(info);
	 	    	   
	 	    	   
	 	        }else if  (macAddress.equals("") && state.equals("OT")) {
	 	    	   result = "ChangeERROR NOT MACADDRESS";  
	 	        }else if  (!macAddress.equals("") && state.equals("IN")) {
	 		    	   result = "MACADDRESS EXIST"; 
	 	        }else {
	 	        	result = "CHANGE ERROR";
	 	        }
	 	      
	 	        closeSession(spCookie);
	 	        LOGGER.debug("STEP01----- Close Session End-");

	 	        client.close();
	 	       LOGGER.debug("STEP02- client Close-");
	    	 }
	    	 
			
	        
		}catch(Exception e){
			LOGGER.debug("error userSessionOut:" + e.toString());
			result = "FALSE";
		}
		return result;
	}
	//전화번호 타입 변경 및 로그인 
	@Override
	public String telelPhoneChange(String loginId, String seatId, String state) throws Exception {
		String result = "";
		TelephoneInfoVO telInfoVO = telManageInfo.selectAgentPageInfoManageDetailSeatId(seatId);
    	UserPhoneInfoVO userInfo = phoneInfo.selectUsserPhoneInfoDetail(loginId);
				try{
					
					

			    	 if (telInfoVO == null || userInfo ==  null ){
			    		 result = "Insufficient information";
			    	 }else {
			    		//roxe  인증 먼저 하기 
			    		client = createClient();
			 	        webTarget = client.target(fileProperties.getProperty("roxe.hostRootPath"));
			 	        Cookie spCookie = authenticate(fileProperties.getProperty("roxe.adminId"), fileProperties.getProperty("roxe.adminPwd"));
			 	        try{
			 	        	openSession(spCookie);
			 	        }catch(Exception e1){
			 	        	LOGGER.debug("open_Session Error:" + e1.toString());
			 	        }
			 	        
			 	       String macAddress = "";
			 	       try{
			 	    	   macAddress = telInfoCheck( telInfoVO.getNodeInfo(),   spCookie, userInfo.getPhoneNumber());
			 	    	   LOGGER.debug("macAddress:" + macAddress);
			 	       }catch(Exception e2){
			 	        	LOGGER.debug("macAddress check Error:" + e2.toString());
			 	        }
			 	        
			 	       
			     	    TelephoneInfoVO info = new TelephoneInfoVO();
			            info.setAgentCode(telInfoVO.getAgentCode());
			            
			 	        if (!macAddress.equals("") && state.equals("OT")) {
			 	           
			 	    	   Subscription subscription = sendJTelTypeChange( telInfoVO.getNodeInfo() , spCookie, userInfo.getPhoneNumber(),  "NOE_C");
			 	    	   //폰 타입 변경 확인
			 	    	   Thread.sleep(100L);
			 	    	   subscription = sendJTelTypeChange( telInfoVO.getNodeInfo() , spCookie, userInfo.getPhoneNumber(),  "NOE_B_IP_8008");	
			 	    	   result = "OK";
			 	           //체크 구문이 먼저 필요 
			 	          
			 	           info.setAgentState("PHONE_STATE_1");  
			 	           info.setAgentNownumber( "0" );
			 	    	   telManageInfo.updateAgentChangeNumber(info);
			 	    	   subscription = null;
			 	    	   result = "OK";
			 	        }else if (macAddress.equals("") && state.equals("OT")) {
				 	           
				 	    	   Subscription subscription = sendJTelTypeChange( telInfoVO.getNodeInfo() , spCookie, userInfo.getPhoneNumber(),  "NOE_C");
				 	    	   //폰 타입 변경 확인
				 	    	   Thread.sleep(100L);
				 	    	   subscription = sendJTelTypeChange( telInfoVO.getNodeInfo() , spCookie, userInfo.getPhoneNumber(),  "NOE_B_IP_8008");	
				 	    	   result = "OK";
				 	           //체크 구문이 먼저 필요 
				 	           info.setAgentState("PHONE_STATE_1");  
				 	           info.setAgentNownumber( "0" );
				 	    	   telManageInfo.updateAgentChangeNumber(info);
				 	    	   subscription = null;
				 	    	   result = "OK";
				 	    	   
				 	     }else if (macAddress.equals("") && state.equals("IN")) {
				 	    	 
			 	              //좌석 번호에 이전 번호가 있으면 로그 아웃
				 	          TelephoneInfoVO info_pre = new TelephoneInfoVO();	
				 	          info_pre.setSeatId(seatId);
				 	          info_pre.setAgentNownumber(loginId);
				 	          
				 	          ErrorInfo vo = new ErrorInfo(); 
				 	          String in_preCheck = 	telManageInfo.selectTelChangeInfo(info_pre);
				 	          if (in_preCheck.equals("OK")){
				 	        	 
				 	        	 
						 	      Subscription  subscription1 = sendJson( telInfoVO.getNodeInfo() , spCookie, userInfo.getPhoneNumber(),  telInfoVO.getAgentMac());
				 	        	  //변경 된 내역 확인
				 	        	  LOGGER.debug("subscription.subscriptionId:" + subscription1.subscriptionId);
				 	        	  if (!subscription1.subscriptionId.contains("Error reading entity from input stream")){
				 	        		  result = "CHANGE ERROR";
				 	        		  //Thread.sleep(100L);
				 	        		 
				 	        		 /* subscription = sendJson( telInfoVO.getNodeInfo() , spCookie, userInfo.getPhoneNumber(),  telInfoVO.getAgentMac());
				 	        		 if (subscription.subscriptionId.contains("sendJson ERROR")){
				 	        			
				 	        		 }else {
				 	        			result = "OK";
							 	        //체크 구문이 먼저 필요 
							 	        info.setAgentState("PHONE_STATE_2");  
							 	    	info.setAgentNownumber( userInfo.getPhoneNumber() );
							 	    	telManageInfo.updateAgentChangeNumber(info);
				 	        		 }*/
				 	        		 vo.setErrorType("ERROR_TYPE_4");
					 				 vo.setErrorMessage("맥 변환 애러:" + userInfo.getPhoneNumber() + ":" + seatId + ":" +loginId );
					 				 errorMapper.insertErrorMessage(vo); 
				 	        	  }else {
				 	        		  result = "OK";
						 	          //체크 구문이 먼저 필요 
						 	          info.setAgentState("PHONE_STATE_2");  
						 	    	  info.setAgentNownumber( userInfo.getPhoneNumber() );
						 	    	  telManageInfo.updateAgentChangeNumber(info);
						 	    	  
						 	    	  
						 	    	 vo.setErrorType("ERROR_TYPE_4");
					 				 vo.setErrorMessage("좌석 변경 확인:" + userInfo.getPhoneNumber() + ":" + seatId + ":" +loginId );
					 				 errorMapper.insertErrorMessage(vo); 
				 	        	  }
				 	        	 subscription1 = null;
				 	        	  
				 	          }else {
				 	        	  //중복 이거나 3분 안에 처리 요청시 결과값
				 	        	 
				 	        	 vo.setErrorType("ERROR_TYPE_4");
				 				 vo.setErrorMessage("중복 애러:" + userInfo.getPhoneNumber() + ":" + seatId + ":" +loginId );
				 				 errorMapper.insertErrorMessage(vo);
				 				  vo = null;
				 	        	 result = in_preCheck;
				 	          }
			 	          
			 	        }else if (macAddress.equals("") && state.equals("ST")) {
				 	          //전화 타입 변경 먼저 하기 (IP_tel로 변경)
			 	        	  
				 	          result = "OK";
				 	          //체크 구문이 먼저 필요 
				 	          info.setAgentState("PHONE_STATE_2");  
				 	    	  info.setAgentNownumber( userInfo.getPhoneNumber() );
				 	    	  telManageInfo.updateAgentChangeNumber(info);
				 	    	  
				 	     }else if  (macAddress.equals("") && state.equals("OT")) {
			 	    	        result = "ChangeERROR NOT MACADDRESS";  
			 	         }else if  (!macAddress.equals("") && state.equals("IN")) {
			 		    	   result = "MACADDRESS EXIST"; 
			 	         }else {
			 	        	result = "CHANGE ERROR";
			 	         }
			 	         info = null;
			 	        closeSession(spCookie);
			 	        client.close();
			    	 }
				}catch(Exception e){
					LOGGER.debug("error userSessionOut:" + e.toString());
					result = "FALSE";
				}
				return result;
				
	}
    //단말기 맥 교체
	private String MacChange(String node,  String number ,String mac )  throws Exception{
		   String result = "";
		    try {
				client = createClient();
				webTarget = client.target(fileProperties.getProperty("roxe.hostRootPath"));
		        Cookie spCookie = authenticate(fileProperties.getProperty("roxe.adminId"), fileProperties.getProperty("roxe.adminPwd"));
		        try{
		        	openSession(spCookie);
		        }catch(Exception e1){
		        	LOGGER.debug("open_Session Error:" + e1.toString());
		        }
		        Subscription subscription = sendJson( node , spCookie, number,  mac);
		        
		        closeSession(spCookie);
	 	        
	 	        client.close();
	 	        result = "OK";
	 	        
			} catch (KeyManagementException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = "FALSE";
			}
	        return result;
	}
    
	@Override
	public String telePhoneReset() throws Exception {
		String result = "";
		try{
			
			    client = createClient();
	 	        webTarget = client.target(fileProperties.getProperty("roxe.hostRootPath"));
	 	        Cookie spCookie = authenticate(fileProperties.getProperty("roxe.adminId"), fileProperties.getProperty("roxe.adminPwd"));
	 	        try{
	 	        	openSession(spCookie);
	 	        }catch(Exception e1){
	 	        	LOGGER.debug("open_Session Error:" + e1.toString());
	 	        }
	 	        
	 	        //리스트 처리 이후 reset 처리 하기
	 	       TelephoneInfoVO searchVO = new TelephoneInfoVO();
	 	       searchVO.setSearchReset("R");
	 	       searchVO.setSearchTelChange("TEL_CHANGE_2");
	 	       searchVO.setFirstIndex(0);
	 	       searchVO.setRecordCountPerPage(2000);
	 	      
	 	       List<TelephoneInfoVO> telEesets = agentMapper.selectAgentPageInfoManageListByPagination(searchVO);
	 	       LOGGER.debug("update telEesets.count:" + telEesets.size());
	 	       
	 	       TelephoneInfoVO info = new TelephoneInfoVO();
	 	       //에러 설정 
	 	       ErrorInfo vo = new ErrorInfo();
	 	      
	 	       for(TelephoneInfoVO telInfo : telEesets){
	 	    	   try{
	 	    		   
	 	    		   String macAddress = telInfoCheck( telInfo.getNodeInfo(),   spCookie, telInfo.getAgentNownumber());
		 		     	info.setAgentCode(telInfo.getAgentCode());
			            if (!macAddress.equals("") ) {
			 	           
			               Subscription subscription = sendJTelTypeChange( telInfo.getNodeInfo() , spCookie, telInfo.getAgentNownumber(),  "NOE_C");
				 	        //폰 타입 변경 확인
			               Thread.sleep(100L);
				 	       subscription = sendJTelTypeChange( telInfo.getNodeInfo() , spCookie, telInfo.getAgentNownumber(),  "NOE_B_IP_8008");	
				 	       Thread.sleep(100L);
			 	    	   result = result ;
			 	    	   info.setAgentState("PHONE_STATE_1");
			 	    	   info.setAgentNownumber( "0" );
			 	    	   telManageInfo.updateAgentChangeNumber(info);
			 	    	   Thread.sleep(100L);
			 	    	   
			 	        }else {
			 	        	result = result + "," + telInfo.getAgentCode();
			 	        }
	 	    		   
	 	    	   }catch(Exception e2){
	 	    		  result = result + "," + telInfo.getAgentCode();
	 	    		  vo.setErrorType("ERROR_TYPE_3");
	 				  vo.setErrorMessage(telInfo.getNodeInfo() + "," +telInfo.getAgentNownumber() +"," + telInfo.getAgentCode());
	 				  errorMapper.insertErrorMessage(vo);
	 				   
	 	    		  LOGGER.debug("---------------------------------------------------------------------------:");
	 	    		  LOGGER.debug("mac SearchError:" + e2.toString());
	 	    		  LOGGER.debug("---------------------------------------------------------------------------:");
	 	    	   }
	 	    	    
		 	       
	 	       }
	 	      telEesets = null;
	 	      vo.setErrorType("ERROR_TYPE_4");
			  vo.setErrorMessage("초기화 완료");
			  errorMapper.insertErrorMessage(vo);
	 	       //장애 내역 된 구문 다시 한번더 전송 
	 	      
			  vo.setErrorType("ERROR_TYPE_4");
			  vo.setErrorMessage("전일자 초기화 시작");
			  errorMapper.insertErrorMessage(vo);
	 	      
	 	      List<TelephoneInfoVO> preDayReset =  agentMapper.selectTodayBackUplist();
	 	      for(TelephoneInfoVO preTelInfo : preDayReset){
		 	    	   try{
		 	    		   
		 	    		   String macAddress = telInfoCheck( preTelInfo.getNodeInfo(),   spCookie, preTelInfo.getHisTelNumber());
			 		     	info.setAgentCode(preTelInfo.getAgentCode());
				            if (!macAddress.equals("") ) {
				 	           
				               Subscription subscription = sendJTelTypeChange( preTelInfo.getNodeInfo() , spCookie, preTelInfo.getHisTelNumber(),  "NOE_C");
					 	        //폰 타입 변경 확인
				               Thread.sleep(100L);
					 	       subscription = sendJTelTypeChange( preTelInfo.getNodeInfo() , spCookie, preTelInfo.getHisTelNumber(),  "NOE_B_IP_8008");	
					 	       Thread.sleep(100L);
				 	    	   result = result ;
				 	    	   info.setAgentState("PHONE_STATE_1");
				 	    	   info.setAgentNownumber( "0" );
				 	    	   telManageInfo.updateAgentChangeNumber(info);
				 	    	   Thread.sleep(100L);
				 	    	   
				 	        }else {
				 	        	result = result + "," + preTelInfo.getAgentCode();
				 	        }
		 	    		   
		 	    	   }catch(Exception e2){
		 	    		  result = result + "," + preTelInfo.getAgentCode();
		 	    		  vo.setErrorType("ERROR_TYPE_3");
		 				  vo.setErrorMessage(preTelInfo.getNodeInfo() + "," +preTelInfo.getHisTelNumber() +"," + preTelInfo.getAgentCode());
		 				  errorMapper.insertErrorMessage(vo);
		 				   
		 	    		  LOGGER.debug("---------------------------------------------------------------------------:");
		 	    		  LOGGER.debug("mac SearchError:" + e2.toString());
		 	    		  LOGGER.debug("---------------------------------------------------------------------------:");
		 	    	   }
		 	    	    
			 	       
		 	  }
	 	      preDayReset = null;
	 	     
	 	      vo.setErrorType("ERROR_TYPE_4");
			  vo.setErrorMessage("전일자 초기화 종료");
			  errorMapper.insertErrorMessage(vo);
			  
			  vo.setErrorType("ERROR_TYPE_4");
			  vo.setErrorMessage(" 초기화 에러 재전송 시작");
			  errorMapper.insertErrorMessage(vo);
	 	      try{
	 	    	 
	 	    	  ErrorInfoVO searchError = new ErrorInfoVO();
		 	      searchError.setSearchKeyword("ERROR_TYPE_3");
		 	      searchError.setSearchCondition("errorType");
		 	      searchError.setSearchError("N");
		 	      searchError.setFirstIndex(0);
		 	      searchError.setRecordCountPerPage(2000);
	 	    	 List<ErrorInfoVO> resendLists =  errorMapper.selectErrorMessage(searchError);
		 	      if (resendLists.size() > 0){
		 	    	  for(ErrorInfoVO errMessage : resendLists){
			 	    	   try{
			 	    			 String[] sendTelInfo =  errMessage.getErrorMessage().toString().split(",");
				 	    		  
				 	    		  String macAddress = telInfoCheck( sendTelInfo[0].toString(),   spCookie, sendTelInfo[1].toString() );
					 		      info.setAgentCode(sendTelInfo[2].toString());
						          if (!macAddress.equals("") ) {
						 	           
						               Subscription subscription = sendJTelTypeChange( sendTelInfo[0].toString() , spCookie, sendTelInfo[1].toString() ,  "NOE_C");
							 	        //폰 타입 변경 확인
						               Thread.sleep(100L);
							 	       subscription = sendJTelTypeChange( sendTelInfo[0].toString() , spCookie, sendTelInfo[1].toString() ,  "NOE_B_IP_8008");	
							 	       Thread.sleep(100L);
						 	    	   result = result ;
						 	    	   info.setAgentState("PHONE_STATE_1");
						 	    	   info.setAgentNownumber( "0" );
						 	    	   telManageInfo.updateAgentChangeNumber(info);
						 	    	   Thread.sleep(100L);
						 	    	   
						 	        }else {
						 	        	result = result + "," + sendTelInfo[2].toString();
						 	        }
			 	    		 }catch(Exception e3){
			 	    			 vo.setErrorType("ERROR_TYPE_1");
				 				 vo.setErrorMessage("Reject Error Send  Error:" + e3.toString() + ":" + errMessage.getErrorMessage());
				 				 errorMapper.insertErrorMessage(vo);
			 	    		 }
		 	    		 
		 	    	  }
		 	      }
		 	     resendLists = null;
	 	      }catch(Exception e5){
	 	    	    vo.setErrorType("ERROR_TYPE_2");
	 				vo.setErrorMessage("reset Err Tabble list Error:" + e5.toString());
	 				errorMapper.insertErrorMessage(vo);
	 	      }
	 	     vo.setErrorType("ERROR_TYPE_4");
			 vo.setErrorMessage(" 초기화 에러 재전송 종료");
			 errorMapper.insertErrorMessage(vo);
	 	       
	 	      //장애 내역된 구문 다시 한번더 전송 끝 부분
	 	      
	 	      //단말기 초기화 안된 사항 검증 작업 시작
	 	      int totalCnt = 0;
	 	      totalCnt = agentMapper.selectRestChangeCount();
	 	      
	 	      if ( totalCnt > 0){

	 	 	     vo.setErrorType("ERROR_TYPE_4");
	 			 vo.setErrorMessage(" 초기화 작업 이후 번호 체인지 안된 리스트 초기화 시작");
	 			 errorMapper.insertErrorMessage(vo);
	 			 
	 	       	 List<TelephoneInfoVO> telEeset = agentMapper.selectRestChangeUpdate();
	 	    	 
	 	         for(TelephoneInfoVO telInfo_Pre : telEeset){
		 	    	   try{
		 	    		   
		 	    		   String macAddress = telInfoCheck( telInfo_Pre.getNodeInfo(),   spCookie, telInfo_Pre.getAgentNownumber());
			 		     	info.setAgentCode(telInfo_Pre.getAgentCode());
				            if (!macAddress.equals("") ) {
				 	           
				               Subscription subscription = sendJTelTypeChange( telInfo_Pre.getNodeInfo() , spCookie, telInfo_Pre.getAgentNownumber(),  "NOE_C");
					 	        //폰 타입 변경 확인
				               Thread.sleep(100L);
					 	       subscription = sendJTelTypeChange( telInfo_Pre.getNodeInfo() , spCookie, telInfo_Pre.getAgentNownumber(),  "NOE_B_IP_8008");	
					 	       Thread.sleep(100L);
				 	    	   result = result ;
				 	    	   info.setAgentState("PHONE_STATE_1");
				 	    	   info.setAgentNownumber( "0" );
				 	    	   telManageInfo.updateAgentChangeNumber(info);
				 	    	   Thread.sleep(100L);
				 	    	   
				 	        }else {
				 	        	
				 	        	info.setAgentState("PHONE_STATE_1");
					 	    	info.setAgentNownumber( "0" );
					 	    	telManageInfo.updateAgentChangeNumber(info);
					 	    	Thread.sleep(100L);
				 	        	//에러 구문 넣기 
					 	    	vo.setErrorType("ERROR_TYPE_1");
				 				vo.setErrorMessage("agent reset Error:" + telInfo_Pre.getAgentCode());
				 				errorMapper.insertErrorMessage(vo);
				 	        	
				 	        	
				 	        }
		 	    		   
		 	    	   }catch(Exception e2){
		 	    		  
		 	    		 vo.setErrorType("ERROR_TYPE_1");
		 				 vo.setErrorMessage("step 2 agent mac address Error:" + e2.toString());
		 				 errorMapper.insertErrorMessage(vo);
		 				 
		 	    	   }
		 	       }
	 	       	telEeset = null ;
	 	       	
	 	       vo.setErrorType("ERROR_TYPE_4");
	 		   vo.setErrorMessage(" 초기화 작업 이후 번호 체인지 안된 리스트 초기화 종료");
	 		   errorMapper.insertErrorMessage(vo);
	 			 
	 	      }else {
	 	    	 vo.setErrorType("ERROR_TYPE_4");
 				 vo.setErrorMessage("리셋 에러 없음");
 				 errorMapper.insertErrorMessage(vo);
	 	      }
	 	      //단말기 초기화 안된 사항 검증 작업 끝
	 	      info = null;
	 	      vo = null;
	 	      closeSession(spCookie);
	 	      client.close();
	 	       
		}catch(Exception e){
			LOGGER.debug("error userSessionOut:" + e.toString());
			result = "F";
		}
		return result;
		
	}
	@Override
	public String telePhoneBasicUpdate() throws Exception {
		String result = "";
		try{
			
			   
	    		client = createClient();
	 	        webTarget = client.target(fileProperties.getProperty("roxe.hostRootPath"));
	 	        Cookie spCookie = authenticate(fileProperties.getProperty("roxe.adminId"), fileProperties.getProperty("roxe.adminPwd"));
	 	        try{
	 	        	openSession(spCookie);
	 	        }catch(Exception e1){
	 	        	LOGGER.debug("open_Session Error:" + e1.toString());
	 	        }
	 	        
	 	        //리스트 처리 이후 reset 처리 하기
	 	       TelephoneInfoVO searchVO = new TelephoneInfoVO();
	 	       searchVO.setSearchReset("R");
	 	       searchVO.setSearchTelChange("TEL_CHANGE_1");
	 	       searchVO.setFirstIndex(0);
	 	       searchVO.setRecordCountPerPage(2000);
	 	      LOGGER.debug("telePhoneBasicUpdate---------------------------------------------------------------------------------------------------");
	 	       List<TelephoneInfoVO> telEesets = agentMapper.selectAgentPageInfoManageListByPagination(searchVO);
	 	       LOGGER.debug("telEesets.count:" + telEesets.size());
	 	       
	 	       TelephoneInfoVO info = new TelephoneInfoVO();
	 	       
	 	       for(TelephoneInfoVO telInfo : telEesets){
	 	    	   
	 	    	 	info.setAgentCode(telInfo.getAgentCode());
		          	LOGGER.debug("-----" +  telInfo.getAgentBasicnumber() + ":" +  telInfo.getAgentMac());
		            Subscription subscription = sendJson( telInfo.getNodeInfo() , spCookie, telInfo.getAgentBasicnumber(),  telInfo.getAgentMac());
			 	    result = result;
			 	          //체크 구문이 먼저 필요 
			 	    info.setAgentState("PHONE_STATE_2");  
			 	    info.setAgentNownumber( telInfo.getAgentBasicnumber() );
			 	    telManageInfo.updateAgentChangeNumber(info);
			 	    Thread.sleep(1000L);
	 	       }
	 	       info = null;
	 	       
	 	       closeSession(spCookie);
	 	       client.close();
	 	       
		}catch(Exception e){
			LOGGER.debug("error userSessionOut:" + e.toString());
			result = "F";
		}
		return result;
		
	}
	
	private Subscription subscribe(String login, Cookie cookie) {
       
        String version = "1.0";
        String filter = "{\"selectors\":[{\"ids\":[\"" + login + "\"],\"names\":[\"telephony\"],\"families\":[],\"origins\":[]}]}";
        final String subscriptionRequest = "{\"notificationUrl\":null"
                + ",\"filter\":" + filter
                + ",\"version\":\"" + version + '\"'
                + '}';
        return webTarget.path("subscriptions").request().cookie(cookie).post(Entity.json(subscriptionRequest), Subscription.class);
    }
	
	
	//전화기 사용자 인증 
	private Subscription sendJson(String node, Cookie cookie, String phoneNumber, String macAddress) throws Exception {
		Subscription subscript = new Subscription();
		try{
			
			String pathUrl = "pbxs/"+ node +"/instances/Subscriber/"+ phoneNumber +  "/Tsc_IP_subscriber/"+ phoneNumber;
			String sendJson =  "{ \"attributes\" : [ { \"name\": \"Ethernet_Address\", \"value\": [\""+  macAddress + "\"] } ] } "; 
			LOGGER.debug("sendJson:" + sendJson);
			subscript =  webTarget.path(pathUrl).request().cookie(cookie).put(Entity.json(sendJson), Subscription.class);
		}catch(Exception e1){
			subscript.setSubscriptionId("sendJson ERROR:" + e1.toString());
			LOGGER.debug("sendJson ERROR:" + e1.toString());
		}
		return subscript;
	   
	}
	//전화기 타입 바꾸기 
	private Subscription sendJTelTypeChange(String node, Cookie cookie, String phoneNumber, String telType) throws Exception {
			
			Subscription subscript = new Subscription();
			try{
				String pathUrl = "pbxs/"+ node +"/instances/Subscriber/"+ phoneNumber;
				String sendJson = "{\"attributes\": [{\"name\": \"Station_Type\", \"value\": [\""+telType+"\"] } ] }";
				//LOGGER.debug("sendJson:" + sendJson);
				subscript =  webTarget.path(pathUrl).request().cookie(cookie).put(Entity.json(sendJson), Subscription.class);
			}catch(Exception e1){
				subscript.setSubscriptionId("ERROR:Error reading");
				LOGGER.debug("sendJson ERROR:" + e1.toString());
			}
			return subscript;
		   
	}
	
	 
	private String telInfoCheck(String node, Cookie cookie, String userNumber) {
		
		String pathUrl = "pbxs/"+  node +"/instances/Subscriber/"+ userNumber +  "/Tsc_IP_subscriber/"+ userNumber;
		
		Response response =webTarget.path(pathUrl).request().cookie(cookie).get();
		JsonNode jsonNode1 = response.readEntity(JsonNode.class);
		String macAddress = jsonNode1.get("attributes").get(1).get("value").get(0).getTextValue();
		response =null;
		return macAddress;
	}
	//작업 해제
	private void unsubscribe(final String login, final Cookie cookie, String subscriptionId) {
      
       try{
    	   LOGGER.debug("unsubscribe subscriptionId:"  + subscriptionId + ":" + login);
    	   Response response = webTarget.path("subscriptions").path(subscriptionId).request().cookie(cookie).delete();
    	   LOGGER.debug("unsubscribe response:"  + response );   	   
       }catch(Exception e1){
    	   LOGGER.debug("unsubscribe ERROR:"  + e1.toString() );
       }

       

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

        class SessionRequest { public String getApplicationName() { return "TEST_APIROX_SERVER"; } }
        
		try{
			Response response = webTarget.path("sessions").request().cookie(cookie).post(Entity.json(new SessionRequest()));
	        JsonNode jsonNode1 = response.readEntity(JsonNode.class);
	        String publicUrl = jsonNode1.get("versions").get(0).get("publicUrl").getTextValue();
	        LOGGER.debug("public Url:" + publicUrl);
		}catch(Exception e){
			//여기 부분 확인 필요 
			LOGGER.debug("ERROR open session:" + e.toString());
		}
		
    }
    private void closeSession(Cookie cookie) {
        Response response = webTarget.path("sessions").request().cookie(cookie).delete();
        LOGGER.debug("close response:" + response);
       
    }
    //쿠기 생성
    private Cookie authenticate(String login, String password) {

        Response response1 = client.target(fileProperties.getProperty("roxe.hosturl")).request().get();
        
        JsonNode jsonNode1 = response1.readEntity(JsonNode.class);
        String publicUrl = jsonNode1.get("versions").get(0).get("publicUrl").getTextValue();
        Response response3 = client.target(publicUrl).register(HttpAuthenticationFeature.basic(login, password)).request().get();
        LOGGER.debug("response3:" + response3);

        Map<String, NewCookie> cookies = response3.getCookies();
       
        Cookie result = cookies.get(USER_COOKIE_NAME);
        LOGGER.debug("cookie:" + result);
        return result;
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
