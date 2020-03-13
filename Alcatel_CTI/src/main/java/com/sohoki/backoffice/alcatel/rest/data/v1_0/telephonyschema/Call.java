package com.sohoki.backoffice.alcatel.rest.data.v1_0.telephonyschema;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Call {

    String callRef;

    public String getCallRef() {
        return callRef;
    }

    public void setCallRef(String value) {
        this.callRef = value;
    }
}