package com.sohoki.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

@Component
public class MessageConverterService implements MessageConverter {
	
	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
//		MapMessage mapMessage = session.createMapMessage();
		String text = (String) object;
		TextMessage message = session.createTextMessage();
        message.setText(text);
        return message;
	}

	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
//		MapMessage mapMessage = (MapMessage)message;
		TextMessage textMessage = (TextMessage) message;
		return textMessage.getText();
	}

}
