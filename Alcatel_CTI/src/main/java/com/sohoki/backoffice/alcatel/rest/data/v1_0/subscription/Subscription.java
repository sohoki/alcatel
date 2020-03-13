package com.sohoki.backoffice.alcatel.rest.data.v1_0.subscription;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown=true)
public class Subscription {

    protected String subscriptionId;
    protected String message;
    // anyURI
    protected String publicPollingUrl;
    // anyURI
    protected String privatePollingUrl;
    // required
    protected SubscriptionState status;

    /**
     * Gets the value of the subscriptionId property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getSubscriptionId() {
        return subscriptionId;
    }

    /**
     * Sets the value of the subscriptionId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSubscriptionId(String value) {
        this.subscriptionId = value;
    }

    /**
     * Gets the value of the message property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the publicPollingUrl property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPublicPollingUrl() {
        return publicPollingUrl;
    }

    /**
     * Sets the value of the publicPollingUrl property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPublicPollingUrl(String value) {
        this.publicPollingUrl = value;
    }

    /**
     * Gets the value of the privatePollingUrl property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPrivatePollingUrl() {
        return privatePollingUrl;
    }

    /**
     * Sets the value of the privatePollingUrl property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPrivatePollingUrl(String value) {
        this.privatePollingUrl = value;
    }

    /**
     * Gets the value of the status property.
     *
     * @return possible object is
     *         {@link SubscriptionState }
     */
    public SubscriptionState getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value allowed object is
     *              {@link SubscriptionState }
     */
    public void setStatus(SubscriptionState value) {
        this.status = value;
    }

    @Override
    public String toString() {
        return "subscriptionId: " + getSubscriptionId()
                + ", message: " + getMessage()
                + ", publicPollingUrl: " + getPublicPollingUrl()
                + ", privatePollingUrl: " + getPrivatePollingUrl()
                + ", status: {" + getStatus()
                + "}";
    }
}
