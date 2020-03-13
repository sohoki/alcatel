package com.sohoki.backoffice.alcatel.rest.data.v1_0.subscription;

public class SubscriptionRequest {

	/**
     * The version of the events the user wants to subscribe to.
     */
    protected String version;

    /**
     * Required<br>
     * Default lifetime of the channel opened between the server and the client.<br>
     * After that time, in minutes, the server can close the connection. It is up to the client to reconnect.<br>
     * Note that this timer is by default at 10 minutes in the server, but can be changed by the client.
     */
    protected Integer timeout;

    /**
     * Describes the event filtering
     */
    protected Filter filter;

    public SubscriptionRequest () {
        timeout = 10;
    }

    /**
     * Gets the value of the filter property.
     *
     * @return possible object is
     *         {@link Filter }
     */
    public Filter getFilter() {
        return filter;
    }

    /**
     * Sets the value of the filter property.
     *
     * @param value allowed object is
     *              {@link Filter }
     */
    public void setFilter(Filter value) {
        this.filter = value;
    }


    /**
     * Gets the value of the version property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the timeout property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * Sets the value of the timeout property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setTimeout(Integer value) {
        this.timeout = value;
    }

    @Override
    public String toString() {
        return "version: " + getVersion()
                + ", timeout: " + getTimeout()
                + ", filter: {" + getFilter()
                + "}";
    }
}
