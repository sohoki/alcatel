package com.sohoki.backoffice.alcatel.rest.data.v1_0.versionschema;

public class Version {
	 protected String id;
	    // required
	    protected String status;
	    // required
	    protected String publicUrl;
	    // required
	    protected String internalUrl;

	    /**
	     * Gets the value of the id property.
	     *
	     * @return possible object is
	     *         {@link String }
	     */
	    public String getId() {
	        return id;
	    }

	    /**
	     * Sets the value of the id property.
	     *
	     * @param value allowed object is
	     *              {@link String }
	     */
	    public void setId(String value) {
	        this.id = value;
	    }

	    /**
	     * Gets the value of the status property.
	     *
	     * @return possible object is
	     *         {@link String }
	     */
	    public String getStatus() {
	        return status;
	    }

	    /**
	     * Sets the value of the status property.
	     *
	     * @param value allowed object is
	     *              {@link String }
	     */
	    public void setStatus(String value) {
	        this.status = value;
	    }

	    /**
	     * Gets the value of the publicUrl property.
	     *
	     * @return possible object is
	     *         {@link String }
	     */
	    public String getPublicUrl() {
	        return publicUrl;
	    }

	    /**
	     * Sets the value of the publicUrl property.
	     *
	     * @param value allowed object is
	     *              {@link String }
	     */
	    public void setPublicUrl(String value) {
	        this.publicUrl = value;
	    }

	    /**
	     * Gets the value of the internalUrl property.
	     *
	     * @return possible object is
	     *         {@link String }
	     */
	    public String getInternalUrl() {
	        return internalUrl;
	    }

	    /**
	     * Sets the value of the internalUrl property.
	     *
	     * @param value allowed object is
	     *              {@link String }
	     */
	    public void setInternalUrl(String value) {
	        this.internalUrl = value;
	    }

	    @Override
	    public String toString() {
	        return "Version{" +
	                "id='" + id + '\'' +
	                ", status='" + status + '\'' +
	                ", publicUrl='" + publicUrl + '\'' +
	                ", internalUrl='" + internalUrl + '\'' +
	                '}';
	    }
}
