package egovframework.let.sym.ccm.cca.service;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CmmnCode implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * 코드ID
	 */
	private String codeId = "";
	
	/*
	 * 코드ID명
	 */
	private String codeIdNm = "";
	
	/*
	 * 코드ID설명
	 */
	private String codeIdDc = "";
	

	/*
	 * 사용여부
	 */
    private String useAt = "";

    /*
     * 최초등록자ID
     */
    private String frstRegisterId = "";
    


	/*
     * 최종수정자ID
     */
    private String lastUpdusrId   = "";

    
    private String mode = "";
    
    

	private String menuGubun;
	
	
    
    
    
    
	public String getMenuGubun() {
		return menuGubun;
	}

	public void setMenuGubun(String menuGubun) {
		this.menuGubun = menuGubun;
	}

	public String getCodeIdNm() {
		return codeIdNm;
	}

	public void setCodeIdNm(String codeIdNm) {
		this.codeIdNm = codeIdNm;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * codeId attribute 를 리턴한다.
	 * @return String
	 */
	public String getCodeId() {
		return codeId;
	}

	/**
	 * codeId attribute 값을 설정한다.
	 * @param codeId String
	 */
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	

	/**
	 * codeIdDc attribute 를 리턴한다.
	 * @return String
	 */
	public String getCodeIdDc() {
		return codeIdDc;
	}

	/**
	 * codeIdDc attribute 값을 설정한다.
	 * @param codeIdDc String
	 */
	public void setCodeIdDc(String codeIdDc) {
		this.codeIdDc = codeIdDc;
	}

	


	/**
	 * useAt attribute 를 리턴한다.
	 * @return String
	 */
	public String getUseAt() {
		return useAt;
	}

	/**
	 * useAt attribute 값을 설정한다.
	 * @param useAt String
	 */
	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}

	/**
	 * frstRegisterId attribute 를 리턴한다.
	 * @return String
	 */
	public String getFrstRegisterId() {
		return frstRegisterId;
	}
	/**
	 * frstRegisterId attribute 값을 설정한다.
	 * @param frstRegisterId String
	 */
	public void setFrstRegisterId(String frstRegisterId) {
		this.frstRegisterId = frstRegisterId;
	}
	/**
	 * lastUpdusrId attribute 를 리턴한다.
	 * @return String
	 */
	public String getLastUpdusrId() {
		return lastUpdusrId;
	}

	/**
	 * lastUpdusrId attribute 값을 설정한다.
	 * @param lastUpdusrId String
	 */
	public void setLastUpdusrId(String lastUpdusrId) {
		this.lastUpdusrId = lastUpdusrId;
	}

	/**
     * toString 메소드를 대치한다.
     */
    public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }
}
