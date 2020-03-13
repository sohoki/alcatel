package com.sohoki.backoffice.alcatel.core;

public enum EventPackages {

	TELEPHONY("telephony"),
    UNIFIEDCOMLOG("unifiedComLog"),
    EVENTSUMMARY("eventSummary"),
    USER("user"),
    ROUTINGMANAGEMENT("routingManagement");

    private String eventPackage;

    private EventPackages(String s) {
        eventPackage = s;
    }

    public String getPackage() {
        return eventPackage;
    }
}
