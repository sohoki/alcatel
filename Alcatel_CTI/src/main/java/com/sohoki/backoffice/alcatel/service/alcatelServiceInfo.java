package com.sohoki.backoffice.alcatel.service;

public interface alcatelServiceInfo {

	
	Boolean userAuthentication(String loginId) throws Exception;
	
	Boolean userSessionOut(String loginId) throws Exception;
	
	Boolean userStateChange(String loginId) throws Exception;
}
