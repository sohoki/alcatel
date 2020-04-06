package com.sohoki.backoffice.sym.agt.service;

import java.io.Serializable;

public class TelephoneInfo implements  Serializable {

	private static final long serialVersionUID = 1L;
	
	private String agentCode = "";
	private String agentNm = "";
	private String agentRemark = "";
	private String agentIp = "";
	private String agentMac = "";
	private String agentUseyn = "";
	private String frstRegistPnttm = "";
	private String frstRegistId = "";
	private String lastUpdtPnttm = "";
	private String lastUpusrId = "";
	private String mode = "";
	private String userId = "";
	
	private String updateDate = "";
	//신규 추가 
	private String centerId = "";
	private String partId = "";
	private String agentState = "";
	private String agentNownumber = "";
	private String agentBasicnumber = "";
	private String agentOsversion = "";
	private int agentFloor = 0;
	
    private String loginId = "";
    private String loginPwd = "";
	
    private String authCookie = "";
    private String seatId = "";
    private String nodeInfo = "";
    
    
	
	public String getNodeInfo() {
		return nodeInfo;
	}

	public void setNodeInfo(String nodeInfo) {
		this.nodeInfo = nodeInfo;
	}

	public String getSeatId() {
		return seatId;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}

	public String getAuthCookie() {
		return authCookie;
	}

	public void setAuthCookie(String authCookie) {
		this.authCookie = authCookie;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getAgentUseyn() {
		return agentUseyn;
	}

	public void setAgentUseyn(String agentUseyn) {
		this.agentUseyn = agentUseyn;
	}

	public String getAgentOsversion() {
		return agentOsversion;
	}

	public void setAgentOsversion(String agentOsversion) {
		this.agentOsversion = agentOsversion;
	}

	public String getAgentState() {
		return agentState;
	}

	public void setAgentState(String agentState) {
		this.agentState = agentState;
	}

	public String getAgentNownumber() {
		return agentNownumber;
	}

	public void setAgentNownumber(String agentNownumber) {
		this.agentNownumber = agentNownumber;
	}

	public String getAgentBasicnumber() {
		return agentBasicnumber;
	}

	public void setAgentBasicnumber(String agentBasicnumber) {
		this.agentBasicnumber = agentBasicnumber;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAgentNm() {
		return agentNm;
	}

	public void setAgentNm(String agentNm) {
		this.agentNm = agentNm;
	}

	public String getAgentRemark() {
		return agentRemark;
	}

	public void setAgentRemark(String agentRemark) {
		this.agentRemark = agentRemark;
	}

	public String getAgentIp() {
		return agentIp;
	}

	public void setAgentIp(String agentIp) {
		this.agentIp = agentIp;
	}

	public String getAgentMac() {
		return agentMac;
	}

	public void setAgentMac(String agentMac) {
		this.agentMac = agentMac;
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

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getCenterId() {
		return centerId;
	}

	public void setCenterId(String centerId) {
		this.centerId = centerId;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public int getAgentFloor() {
		return agentFloor;
	}

	public void setAgentFloor(int agentFloor) {
		this.agentFloor = agentFloor;
	}

	

}
