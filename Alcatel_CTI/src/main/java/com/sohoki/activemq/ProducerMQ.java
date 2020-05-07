package com.sohoki.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class ProducerMQ {
	public static void main(String[] args) {
		ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection conn = null;
		Session session = null;
		try {
			conn = cf.createConnection();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = new ActiveMQQueue("queueName");
			MessageProducer producer = session.createProducer(destination);
			TextMessage message = session.createTextMessage();
			message.setText("Hello ActiveMQ world!");
			producer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			try {
				if (session != null) {
					session.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (JMSException ex) {
			}
		}

	}

}
