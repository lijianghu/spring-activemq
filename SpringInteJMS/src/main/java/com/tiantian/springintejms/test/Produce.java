package com.tiantian.springintejms.test;

import java.io.FileNotFoundException;
import java.io.File;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.BlobMessage;
import org.apache.activemq.command.ActiveMQBlobMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
public class Produce {
	  private static final Log logger = LogFactory.getLog(Produce.class); 
	    static int index = 0; 
	    public static void main(String[] args) throws FileNotFoundException {
	        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//	        final Destination destination = (Destination)ctx.getBean("defaultResponseQueue");//用于接收消费者返回的消息
	        JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("myJmsTemplate");
	        JmsTemplate jmsTemplate2 = (JmsTemplate) ctx.getBean("myJmsTemplate2"); 
	        for (int i = 0; i < 10; i++) {
	            index = i; 
	            try {
	                jmsTemplate.send(new MessageCreator() { 
	                    public Message createMessage(Session session) throws JMSException {
//	                    	//文本消息  
	                    	System.out.println("send "+index);
	                    	Message m = session.createTextMessage("文本消息"+index);  
	                    	
//	                    	//键值对消息  
//	                    	MapMessage m = session.createMapMessage();  
//	                    	m.setLong("age", new Long(32));  
//	                    	m.setDouble("sarray", new Double(5867.15));  
//	                    	m.setString("username", "键值对消息");  
	                    	  
	                    	//流消息  
//	                    	StreamMessage m = session.createStreamMessage();  
//	                    	m.writeString("streamMessage流消息");  
//	                    	m.writeLong(55);
	                    	
//	                    	//字节消息  
//	                    	String s = "BytesMessage字节消息";  
//	                    	BytesMessage m = session.createBytesMessage();  
//	                    	bytesMessage.writeBytes(s.getBytes());  
//	                    	  
//	                    	//对象消息  
//	                    	User user = new User(1,"admin");
//	                    	ObjectMessage m = session.createObjectMessage();  
//	                    	objectMessage.setObject(user);  
	                    	return m; 
	                    } 
	                });
	            } catch (Exception e) { 
	            	System.out.println("eeeeeeeeeee");
	                e.printStackTrace();
	            } 
	        } 
	    }
}
