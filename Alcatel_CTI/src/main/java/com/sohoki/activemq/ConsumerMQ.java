package com.sohoki.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class ConsumerMQ {

	public static void main(String[] args) {
		ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection conn = null;
		Session session = null;
		try {
			conn = cf.createConnection();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = new ActiveMQQueue("queueName");
			MessageConsumer consumer = session.createConsumer(destination);
			conn.start();
			Message message = consumer.receive();
			TextMessage textMessage = (TextMessage) message;
			System.out.println("From Queue: " + textMessage.getText());
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
