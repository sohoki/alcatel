package com.sohoki.backoffice.sts.tran.service;

import java.io.Serializable;

public class TransInfoVO extends TransInfo implements Serializable  {

	
    private static final long serialVersionUID = 1L;

	
	/** 검색조건 */
    private String searchCondition = "";    
    /** 검색Keyword */
    private String searchKeyword = "";    
    /** 검색사용여부 */
    private String searchUseYn = "";    
    /** 현재페이지 */
    private int pageIndex = 1;    
    /** 페이지갯수 */
    private int pageUnit = 10;    
    /** 페이지사이즈 */
    private int pageSize = 10;    
    private int firstIndex = 1;
    private int lastIndex = 1;    
    private int recordCountPerPage = 10;
    
    private String seachWorkGubun = "";
    private int totalRecordCount = 0;
    
    private String workGubunTxt = "";
    private String sendGubunTxt = "";
    
    
    
    
    
	public String getWorkGubunTxt() {
		return workGubunTxt;
	}
	public void setWorkGubunTxt(String workGubunTxt) {
		this.workGubunTxt = workGubunTxt;
	}
	public String getSendGubunTxt() {
		return sendGubunTxt;
	}
	public void setSendGubunTxt(String sendGubunTxt) {
		this.sendGubunTxt = sendGubunTxt;
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
	public String getSeachWorkGubun() {
		return seachWorkGubun;
	}
	public void setSeachWorkGubun(String seachWorkGubun) {
		this.seachWorkGubun = seachWorkGubun;
	}
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
    
    
    
    
}
