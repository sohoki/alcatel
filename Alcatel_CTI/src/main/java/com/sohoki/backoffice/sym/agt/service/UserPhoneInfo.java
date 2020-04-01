package com.sohoki.backoffice.sym.agt.service;

import java.io.Serializable;

public class UserPhoneInfo implements  Serializable {

	private static final long serialVersionUID = 1L;
	
	private String phoneNumber = "";
	private String phoneGubun = "";
	private String phoneUseyn = "";
	private String frstRegistPnttm = "";
	private String frstRegistId = "";
	private String lastUpdtPnttm = "";
	private String lastUpusrId = "";
	private String mode = "";
	private String userId = "";
	private String phoneState = "";
	private String loginId = "";
	private String loginPassword = "";
	private String userName = "";
	
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	public String getPhoneState() {
		return phoneState;
	}
	public void setPhoneState(String phoneState) {
		this.phoneState = phoneState;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPhoneGubun() {
		return phoneGubun;
	}
	public void setPhoneGubun(String phoneGubun) {
		this.phoneGubun = phoneGubun;
	}
	public String getPhoneUseyn() {
		return phoneUseyn;
	}
	public void setPhoneUseyn(String phoneUseyn) {
		this.phoneUseyn = phoneUseyn;
	}
	public String getFrstRegistPnttm() {
		return frstRegistPnttm;
	}
	public void setFrstRegistPnttm(String frstRegistPnttm) {
		this.frstRegistPnttm = frstRegistPnttm;
	}
	public String getFrstRegistId() {
		return frstRegistId;
	}
	public void setFrstRegistId(String frstRegistId) {
		this.frstRegistId = frstRegistId;
	}
	public String getLastUpdtPnttm() {
		return lastUpdtPnttm;
	}
	public void setLastUpdtPnttm(String lastUpdtPnttm) {
		this.lastUpdtPnttm = lastUpdtPnttm;
	}
	public String getLastUpusrId() {
		return lastUpusrId;
	}
	public void setLastUpusrId(String lastUpusrId) {
		this.lastUpusrId = lastUpusrId;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	

}
