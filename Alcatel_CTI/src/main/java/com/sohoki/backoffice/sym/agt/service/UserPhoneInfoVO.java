package com.sohoki.backoffice.sym.agt.service;

import java.io.Serializable;
import com.sohoki.backoffice.sym.agt.service.UserPhoneInfo;


public class UserPhoneInfoVO extends UserPhoneInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	/** 검색조건 */
    private String searchCondition = "";    
    /** 검색Keyword */
    private String searchKeyword = "";    
    /** 검색사용여부 */
    private String searchUseYn = "";    
    
    private String mber_Sttus = "";
    
    
    /** 현재페이지 */
    private int pageIndex = 1;    
    /** 페이지갯수 */
    private int pageUnit = 10;    
    /** 페이지사이즈 */
    private int pageSize = 10;    
    private int firstIndex = 1;
    private int lastIndex = 1;    
    private int recordCountPerPage = 10;
    
    private int totalRecordCount = 0;
    
    private String phoneGubunTxt = "";
    private String phoneStateTxt = "";
    private String searchPhoneGubun = "";
    private String searchPhoneState = "";
    private String searchPhoneUseyn = "";
    
    
    
    
	public String getSearchPhoneUseyn() {
		return searchPhoneUseyn;
	}
	public void setSearchPhoneUseyn(String searchPhoneUseyn) {
		this.searchPhoneUseyn = searchPhoneUseyn;
	}
	public String getPhoneGubunTxt() {
		return phoneGubunTxt;
	}
	public void setPhoneGubunTxt(String phoneGubunTxt) {
		this.phoneGubunTxt = phoneGubunTxt;
	}
	public String getPhoneStateTxt() {
		return phoneStateTxt;
	}
	public void setPhoneStateTxt(String phoneStateTxt) {
		this.phoneStateTxt = phoneStateTxt;
	}
	public String getSearchPhoneGubun() {
		return searchPhoneGubun;
	}
	public void setSearchPhoneGubun(String searchPhoneGubun) {
		this.searchPhoneGubun = searchPhoneGubun;
	}
	public String getSearchPhoneState() {
		return searchPhoneState;
	}
	public void setSearchPhoneState(String searchPhoneState) {
		this.searchPhoneState = searchPhoneState;
	}
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public String getSearchUseYn() {
		return searchUseYn;
	}
	public void setSearchUseYn(String searchUseYn) {
		this.searchUseYn = searchUseYn;
	}
	public String getMber_Sttus() {
		return mber_Sttus;
	}
	public void setMber_Sttus(String mber_Sttus) {
		this.mber_Sttus = mber_Sttus;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageUnit() {
		return pageUnit;
	}
	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getFirstIndex() {
		return firstIndex;
	}
	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}
	public int getLastIndex() {
		return lastIndex;
	}
	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}
	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}
	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}
    
    
    
    
}
