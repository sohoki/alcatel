package com.sohoki.activemq;

import javax.annotation.Resource;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsService {

	@Resource(name = "jmsTemplate")
	JmsTemplate producer;
	
	public boolean send(String message) throws Exception {
		boolean ret = false;
		
		producer.convertAndSend(message);
		ret = true;
		
		return ret;
	}
}
