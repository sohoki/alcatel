package com.sohoki.backoffice.alcatel.rest.data.v1_0.sessionschema;

import java.util.ArrayList;
import java.util.List;

public class SessionInfo {


    protected int timeToLive;
    protected String publicBaseUrl;
    // required
    protected String privateBaseUrl;

    protected List<Service> services;

    protected List<String> capabilities;

	protected boolean admin;
    /**
     * Gets the value of the timeToLive property.
     */
    public int getTimeToLive() {
        return timeToLive;
    }

    /**
     * Sets the value of the timeToLive property.
     */
    public void setTimeToLive(int value) {
        this.timeToLive = value;
    }

    /**
     * Gets the value of the publicBaseUrl property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPublicBaseUrl() {
        return publicBaseUrl;
    }

    /**
     * Sets the value of the publicBaseUrl property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPublicBaseUrl(String value) {
        this.publicBaseUrl = value;
    }

    /**
     * Gets the value of the privateBaseUrl property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPrivateBaseUrl() {
        return privateBaseUrl;
    }

    /**
     * Sets the value of the privateBaseUrl property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPrivateBaseUrl(String value) {
        this.privateBaseUrl = value;
    }

    public List<Service> getServices() {
        if (services == null) {
            services = new ArrayList<>();
        }
        return services;
    }

    public void setService(List<Service> service) {
        this.services = service;
    }

    public List<String> getCapabilities() {
        if (capabilities == null) {
            capabilities = new ArrayList<>();
        }
        return capabilities;
    }

    public void setCapabilities(List<String> capability) {
        this.capabilities = capability;
    }
	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}


    @Override
    public String toString() {
        return "SessionInfo{" +
                "timeToLive=" + timeToLive +
                ", publicBaseUrl='" + publicBaseUrl + '\'' +
                ", privateBaseUrl='" + privateBaseUrl + '\'' +
                ", services=" + services +
                ", capabilities=" + capabilities +
                '}';
    }
}
