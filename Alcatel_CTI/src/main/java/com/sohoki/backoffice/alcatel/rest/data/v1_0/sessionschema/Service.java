package com.sohoki.backoffice.alcatel.rest.data.v1_0.sessionschema;

public class Service {

	 // required
    protected String serviceName;
    protected String serviceVersion;
    // required
    protected String relativeUrl;

    /**
     * Gets the value of the serviceName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Sets the value of the serviceName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setServiceName(String value) {
        this.serviceName = value;
    }

    /**
     * Gets the value of the serviceVersion property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getServiceVersion() {
        return serviceVersion;
    }

    /**
     * Sets the value of the serviceVersion property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setServiceVersion(String value) {
        this.serviceVersion = value;
    }

    /**
     * Gets the value of the relativeUrl property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getRelativeUrl() {
        return relativeUrl;
    }

    /**
     * Sets the value of the relativeUrl property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRelativeUrl(String value) {
        this.relativeUrl = value;
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceName='" + serviceName + '\'' +
                ", serviceVersion='" + serviceVersion + '\'' +
                ", relativeUrl='" + relativeUrl + '\'' +
                '}';
    }
}
