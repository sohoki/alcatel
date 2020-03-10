package com.sohoki.backoffice.util.service;

import java.io.Serializable;

public class UniUtilInfo implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private String inTable;
	private String inCheckName;
	private String inCondition;
	private int otCnt;
	
	
	
	
	
	public int getOtCnt() {
		return otCnt;
	}
	public void setOtCnt(int otCnt) {
		this.otCnt = otCnt;
	}
	public String getInTable() {
		return inTable;
	}
	public void setInTable(String inTable) {
		this.inTable = inTable;
	}
	public String getInCheckName() {
		return inCheckName;
	}
	public void setInCheckName(String inCheckName) {
		this.inCheckName = inCheckName;
	}
	public String getInCondition() {
		return inCondition;
	}
	public void setInCondition(String inCondition) {
		this.inCondition = inCondition;
	}
	
}
