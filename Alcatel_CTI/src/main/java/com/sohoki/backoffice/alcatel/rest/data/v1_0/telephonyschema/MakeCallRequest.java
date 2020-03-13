package com.sohoki.backoffice.alcatel.rest.data.v1_0.telephonyschema;


public class MakeCallRequest {

    // required
    protected String deviceId;
    protected String callee;

    // optional
    protected Boolean autoAnswer;

    public MakeCallRequest(String deviceId, String callee) {
        this.deviceId = deviceId;
        this.callee = callee;
        this.autoAnswer = true;
    }

    /**

     * Gets the value of the deviceId property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Sets the value of the deviceId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDeviceId(String value) {
        this.deviceId = value;
    }

    /**
     * Gets the value of the callee property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCallee() {
        return callee;
    }

    /**
     * Sets the value of the callee property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCallee(String value) {
        this.callee = value;
    }

    /**
     * Gets the value of the autoAnswer property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isAutoAnswer() {
        return autoAnswer;
    }

    /**
     * Sets the value of the autoAnswer property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setAutoAnswer(Boolean value) {
        this.autoAnswer = value;
    }
}
