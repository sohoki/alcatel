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
           // Properties apiProperties = ConfigReader.load("client-rest.properties");
            /*this.apiVersion =  apiProperties.getProperty("api.version");
            this.rootPath = buildRootPath(apiProperties.getProperty("roxe.host"), apiProperties.getProperty("api.path"));
            this.apiLogFile = apiProperties.getProperty("api.log.file");*/
        
        	//this.apiVersion = fileProperties.getProperty("api.version");// .getString("api.version")
        	this.apiVersion = "1.0"; 
        	//this.rootPath = buildRootPath(fileProperties.getProperty("roxe.host"), fileProperties.getProperty("api.path"));
        	this.rootPath = buildRootPath( "otwog.hankooktech.com",  "/api/rest");
        	
        	//this.apiLogFile = fileProperties.getProperty("api.log.file");
        	this.apiLogFile = "C:/Users/user/git/alcatel/Alcatel_CTI/src/main/webapp/log";
        	
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
