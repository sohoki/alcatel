package com.sohoki.backoffice.alcatel.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import com.sohoki.backoffice.alcatel.core.exception.OTRestClientException;

public class ConfigReader {
	
	 public static Properties load(String filename) throws OTRestClientException {
	        InputStream inputStream = null;
	        try {
	            inputStream = ConfigReader.class.getResourceAsStream("/" + filename);
	            if (inputStream == null) {
	                throw new OTRestClientException("Cannot open properties file: " + filename);
	            }
	            Properties properties = new Properties();
	            properties.load(inputStream);
	            return properties;
	        } catch (IOException e) {
	            throw new OTRestClientException("Cannot read properties in file: " + filename, e);
	        } finally {
	            if (inputStream != null) {
	                try {
	                    inputStream.close();
	                } catch (IOException e) {
	                    throw new OTRestClientException("Cannot close properties file: " + filename, e);
	                }
	            }
	        }
	    }
}
