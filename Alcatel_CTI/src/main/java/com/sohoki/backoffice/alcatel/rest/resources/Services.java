package com.sohoki.backoffice.alcatel.rest.resources;

public enum Services {
	    USERS("Users"),
	    TELEPHONY("Telephony"),
	    LOGINS("Logins"),
	    DEVICES("Devices"),
	    STATE("State"),
	    DIRECTORY("Directory"),
	    EVENTSUMMARY("EventSummary"),
	    ROUTING("Routing"),
	    SUBSCRIPTIONS("Subscriptions");

	    private String service;

	    private Services(String s) {
	        service = s;
	    }

	    public String getService() {
	        return service;
	    }
}
