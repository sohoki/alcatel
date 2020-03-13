package com.sohoki.backoffice.alcatel.rest.data.v1_0.versionschema;

import java.util.ArrayList;
import java.util.List;

public class Versions {

	public void setVersions(List<Version> versions) {
        this.versions = versions;
    }

    protected List<Version> versions;

    
    public List<Version> getVersions() {
        if (versions == null) {
            versions = new ArrayList<>();
        }
        return this.versions;
    }

    @Override
    public String toString() {
        return "Versions{" +
                "versions=" + versions +
                '}';
    }
}
