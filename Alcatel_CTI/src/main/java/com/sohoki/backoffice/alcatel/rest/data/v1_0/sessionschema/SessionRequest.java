package com.sohoki.backoffice.alcatel.rest.data.v1_0.sessionschema;

public class SessionRequest {

    // required
    protected String applicationName;
    protected String applicationAddress;
    protected SupervisedAccount supervisedAccount;

    /**
     * Gets the value of the applicationName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * Sets the value of the applicationName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setApplicationName(String value) {
        this.applicationName = value;
    }

    /**
     * Gets the value of the applicationAddress property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getApplicationAddress() {
        return applicationAddress;
    }

    /**
     * Sets the value of the applicationAddress property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setApplicationAddress(String value) {
        this.applicationAddress = value;
    }

    /**
     * Gets the value of the supervisedAccount property.
     *
     * @return possible object is
     *         {@link SupervisedAccount }
     */
    public SupervisedAccount getSupervisedAccount() {
        return supervisedAccount;
    }

    /**
     * Sets the value of the supervisedAccount property.
     *
     * @param value allowed object is
     *              {@link SupervisedAccount }
     */
    public void setSupervisedAccount(SupervisedAccount value) {
        this.supervisedAccount = value;
    }

}
