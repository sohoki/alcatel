package com.sohoki.backoffice.alcatel.service;

public interface alcatelServiceInfo {

	
	Boolean userAuthentication(String userId, String loginId, String telProcessGubun) throws Exception;
	//mac 상태 변경
	String telelPhoneStateChange(String loginId, String seatId, String state) throws Exception;
	//log out 및 로그인 작업 
	String telelPhoneChange(String loginId, String seatId, String state) throws Exception;
	//배치 로그인 
	String telePhoneStart(String jsonTxt)  throws Exception;
	//배치 로그아웃 
	String telePhoneReset() throws Exception;
	//기본값 초기화 
	String telePhoneBasicUpdate() throws Exception;
	
}
