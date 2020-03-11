package com.sohoki.backoffice.uat.uia.service;

import java.io.Serializable;

public class PartInfo implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private String partId;
	private String parentPartId;
	private String partNm;
	private String partDc;
	private String partUseyn;
	private String partOrder;
	private String mode;
	private String partLevel;
	private String partCreateDe;
	
	
	
	
	
	public String getPartCreateDe() {
		return partCreateDe;
	}
	public void setPartCreateDe(String partCreateDe) {
		this.partCreateDe = partCreateDe;
	}
	public String getPartId() {
		return partId;
	}
	public void setPartId(String partId) {
		this.partId = partId;
	}
	public String getParentPartId() {
		return parentPartId;
	}
	public void setParentPartId(String parentPartId) {
		this.parentPartId = parentPartId;
	}
	public String getPartNm() {
		return partNm;
	}
	public void setPartNm(String partNm) {
		this.partNm = partNm;
	}
	public String getPartDc() {
		return partDc;
	}
	public void setPartDc(String partDc) {
		this.partDc = partDc;
	}
	public String getPartUseyn() {
		return partUseyn;
	}
	public void setPartUseyn(String partUseyn) {
		this.partUseyn = partUseyn;
	}
	public String getPartOrder() {
		return partOrder;
	}
	public void setPartOrder(String partOrder) {
		this.partOrder = partOrder;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getPartLevel() {
		return partLevel;
	}
	public void setPartLevel(String partLevel) {
		this.partLevel = partLevel;
	}
	
	
	
	
	

}
