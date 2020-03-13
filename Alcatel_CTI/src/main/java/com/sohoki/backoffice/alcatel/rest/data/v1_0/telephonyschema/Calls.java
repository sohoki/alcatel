package com.sohoki.backoffice.alcatel.rest.data.v1_0.telephonyschema;

import java.util.ArrayList;
import java.util.List;

public class Calls {

    protected List<Call> calls;

    public List<Call> getCalls() {
        if (calls == null) {
            calls = new ArrayList<>();
        }
        return this.calls;
    }

}