package com.sohoki.backoffice.sts.error.service;

import java.io.Serializable;

public class ErrorInfo implements  Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String errorSeq;
	private String errorType;
	private String errorMessage;
	private String errorRegdate;
	
	
	
	public String getErrorSeq() {
		return errorSeq;
	}
	public void setErrorSeq(String errorSeq) {
		this.errorSeq = errorSeq;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorRegdate() {
		return errorRegdate;
	}
	public void setErrorRegdate(String errorRegdate) {
		this.errorRegdate = errorRegdate;
	}
	
	
	

}
