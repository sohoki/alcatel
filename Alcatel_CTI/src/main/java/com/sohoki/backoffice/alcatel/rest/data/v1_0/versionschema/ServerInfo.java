package com.sohoki.backoffice.alcatel.rest.data.v1_0.versionschema;

public class ServerInfo {

	/** Friendly description of the product. */
	 protected String productName;
	
	/** Short name of the product. */
	protected String productType;
	
	/** Version of the product. */
	protected ProductVersion productVersion;

	/** Indicate if the server is in High Availability mode. */
	protected Boolean haMode;
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public ProductVersion getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(ProductVersion productVersion) {
		this.productVersion = productVersion;
	}

	public Boolean getHaMode() {
		return haMode;
	}

	public void setHaMode(Boolean haMode) {
		this.haMode = haMode;
	}
	
}
