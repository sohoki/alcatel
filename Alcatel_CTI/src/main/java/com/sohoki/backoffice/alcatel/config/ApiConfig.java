package com.sohoki.backoffice.alcatel.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.sohoki.backoffice.alcatel.core.exception.OTRestClientException;

import egovframework.rte.fdl.property.EgovPropertyService;

public enum ApiConfig {
	INSTANCE; // This is a singleton.

    /**
     * API version
     */
    private String apiVersion;

    /**
     * API REST root path
     */
    private String rootPath;

    /**
     * Name of the file where REST messages are logged.<br>
     * Default value: none.
     */
    private String apiLogFile;


    
    @Autowired
	private Properties fileProperties;



    
    private ApiConfig() {
        try {
            
        	this.apiVersion = "1.0"; 
        	this.rootPath = buildRootPath(fileProperties.getProperty("roxe.host"), fileProperties.getProperty("api.path"));
        	this.apiLogFile = fileProperties.getProperty("api.log.file");
        } catch (Exception e) {
        	
        	System.out.println( "error:" + e.toString());
            this.apiVersion = null;
            this.rootPath = null;
            this.apiLogFile = null;
        }
    }

    private String buildRootPath(String icsHost, String apiPath) {
        try {
            URI uri = new URI("http", icsHost, apiPath, null);
            return uri.toString();
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public String getRootPath() {
        return rootPath;
    }

    public String getApiLogFile() {
        return apiLogFile;
    }
}
