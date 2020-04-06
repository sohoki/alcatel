package com.sohoki.backoffice.alcatel.service;

public interface alcatelServiceInfo {

	
	Boolean userAuthentication(String userId, String loginId, String telProcessGubun) throws Exception;
	
	Boolean userSubscriptionRaw(String userId, String loginId) throws Exception;
	
	Boolean userSubscriptionSessionOut(String loginId) throws Exception;
	
	Boolean userSessionOut(String loginId) throws Exception;
	
	Boolean userStateChange(String loginId) throws Exception;
}
