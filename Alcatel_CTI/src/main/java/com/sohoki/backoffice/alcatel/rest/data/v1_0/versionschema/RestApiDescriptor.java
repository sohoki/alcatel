package com.sohoki.backoffice.alcatel.rest.data.v1_0.versionschema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

public class RestApiDescriptor {

	/** Provide extra information about the server hosting the REST API. 
     * @visibility 2.1 PUBLIC 
     **/
    protected ServerInfo serverInfo;
    
	/** List of the versions supported on the server. */
    @XmlElement(required = true)
    protected List<Version> versions;
    
    /**
     * Gets the value of the versions property.
     */
    public List<Version> getVersions() {
        if (versions == null) {
            versions = new ArrayList<Version>();
        }
        return this.versions;
    }

	public ServerInfo getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
	}
}
