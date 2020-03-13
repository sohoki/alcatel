package com.sohoki.backoffice.alcatel.rest.data.v1_0.versionschema;

public class ProductVersion {

	/** Major version of the product. 
	 * This field should help to identify the release of a product. 
	 **/	
    protected String major;
	
	/** Minor revision of the product. 
	 * This field is for internal needs. 
	 **/	
    protected String minor;

    
	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getMinor() {
		return minor;
	}

	public void setMinor(String minor) {
		this.minor = minor;
	}
}
