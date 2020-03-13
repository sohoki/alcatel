package com.sohoki.backoffice.alcatel.rest.data.v1_0.subscription;

public enum SubscriptionState {

	 /**
     * Subscription state is unknown.
     */
    UNKNOWN,

    /**
     * Full subscription has been accepted.
     */
    ACCEPTED,

    /**
     * Only part of the subscription has been accepted.
     */
    PARTIAL,

    /**
     * The subscription has been refused.
     */
    REFUSED;

    public String value() {
        return name();
    }

    public static SubscriptionState fromValue(String v) {
        return valueOf(v);
    }
}
