package com.sohoki.backoffice.sym.cnt.service;

import java.io.Serializable;

public class CenterInfo  implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private String centerId;
	private String centerNm;
	private String centerZipcode;
	private String centerZipcode1;
	private String centerZipcode2;
	private String centerAddr1;
	private String centerAddr2;
	private String centerTel;
	private String centerFax;
	private String centerRegId;
	private String centerRegdate;
	private String centerUpdateId;
	private String centerImg;
	private String centerUrl;
	private String centerSeatImg;
	private String centerUseYn;
	private String restInfo;
	private String meetingroomInfo;
	private String centerInfo;
	
	private String adminApprovalYn;
	private String centerFloor;
	private String mode;
	private String partId;
	private String centerFloorEnd;
	private String totalRecordCount;
	
	
	
	
	public String getTotalRecordCount() {
		return totalRecordCount;
	}
	public void setTotalRecordCount(String totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	public String getCenterFloorEnd() {
		return centerFloorEnd;
	}
	public void setCenterFloorEnd(String centerFloorEnd) {
		this.centerFloorEnd = centerFloorEnd;
	}
	public String getPartId() {
		return partId;
	}
	public void setPartId(String partId) {
		this.partId = partId;
	}
	public String getCenterZipcode1() {
		return centerZipcode1;
	}
	public void setCenterZipcode1(String centerZipcode1) {
		this.centerZipcode1 = centerZipcode1;
	}
	public String getCenterZipcode2() {
		return centerZipcode2;
	}
	public void setCenterZipcode2(String centerZipcode2) {
		this.centerZipcode2 = centerZipcode2;
	}
	public String getCenterFloor() {
		return centerFloor;
	}
	public void setCenterFloor(String centerFloor) {
		this.centerFloor = centerFloor;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getCenterId() {
		return centerId;
	}
	public void setCenterId(String centerId) {
		this.centerId = centerId;
	}
	public String getCenterNm() {
		return centerNm;
	}
	public void setCenterNm(String centerNm) {
		this.centerNm = centerNm;
	}
	public String getCenterZipcode() {
		return centerZipcode;
	}
	public void setCenterZipcode(String centerZipcode) {
		this.centerZipcode = centerZipcode;
	}
	public String getCenterAddr1() {
		return centerAddr1;
	}
	public void setCenterAddr1(String centerAddr1) {
		this.centerAddr1 = centerAddr1;
	}
	public String getCenterAddr2() {
		return centerAddr2;
	}
	public void setCenterAddr2(String centerAddr2) {
		this.centerAddr2 = centerAddr2;
	}
	public String getCenterTel() {
		return centerTel;
	}
	public void setCenterTel(String centerTel) {
		this.centerTel = centerTel;
	}
	public String getCenterFax() {
		return centerFax;
	}
	public void setCenterFax(String centerFax) {
		this.centerFax = centerFax;
	}
	public String getCenterRegId() {
		return centerRegId;
	}
	public void setCenterRegId(String centerRegId) {
		this.centerRegId = centerRegId;
	}
	public String getCenterRegdate() {
		return centerRegdate;
	}
	public void setCenterRegdate(String centerRegdate) {
		this.centerRegdate = centerRegdate;
	}
	public String getCenterUpdateId() {
		return centerUpdateId;
	}
	public void setCenterUpdateId(String centerUpdateId) {
		this.centerUpdateId = centerUpdateId;
	}
	public String getCenterImg() {
		return centerImg;
	}
	public void setCenterImg(String centerImg) {
		this.centerImg = centerImg;
	}
	public String getCenterUrl() {
		return centerUrl;
	}
	public void setCenterUrl(String centerUrl) {
		this.centerUrl = centerUrl;
	}
	public String getCenterSeatImg() {
		return centerSeatImg;
	}
	public void setCenterSeatImg(String centerSeatImg) {
		this.centerSeatImg = centerSeatImg;
	}
	public String getCenterUseYn() {
		return centerUseYn;
	}
	public void setCenterUseYn(String centerUseYn) {
		this.centerUseYn = centerUseYn;
	}
	public String getRestInfo() {
		return restInfo;
	}
	public void setRestInfo(String restInfo) {
		this.restInfo = restInfo;
	}
	public String getMeetingroomInfo() {
		return meetingroomInfo;
	}
	public void setMeetingroomInfo(String meetingroomInfo) {
		this.meetingroomInfo = meetingroomInfo;
	}
	public String getCenterInfo() {
		return centerInfo;
	}
	public void setCenterInfo(String centerInfo) {
		this.centerInfo = centerInfo;
	}
	public String getAdminApprovalYn() {
		return adminApprovalYn;
	}
	public void setAdminApprovalYn(String adminApprovalYn) {
		this.adminApprovalYn = adminApprovalYn;
	}

	@Override
	public String toString(){ //return타입이 String인 공용접근지정자를 가진 toString() 메소드!
		  return "CenterId:" +centerId + ":" + centerId + ":" + centerNm + ":" + centerZipcode + " : " + centerAddr1 + ":" + centerUpdateId  ; // return 값은 위에서 정의한대로 쌍따옴표가 들어간 String타입!
	}
	
	

	
	
	
	
	
}
