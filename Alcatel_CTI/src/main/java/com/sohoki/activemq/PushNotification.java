package com.sohoki.activemq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PushNotification {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public void handleMessage(String message) {
		logger.info("Receive Message: "+ message);
	}
}
