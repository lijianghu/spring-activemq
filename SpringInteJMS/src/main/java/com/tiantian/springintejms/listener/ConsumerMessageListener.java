package com.tiantian.springintejms.listener;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 发送者消息监听
 * @Title: ConsumerMessageListener.java
 * @Package: com.tiantian.springintejms.listener
 * @Description: TODO
 * 
 ******************************************************** 
 * Date				Author 		Changes 
 * 2017年2月13日	        zx			创建
 ********************************************************
 */
public class ConsumerMessageListener implements MessageListener {
	
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			//这里我们知道生产者发送的就是一个纯文本消息，所以这里可以直接进行强制转换，或者直接把onMessage方法的参数改成Message的子类TextMessage
			TextMessage textMsg = (TextMessage) message;
			System.out.println("生产者接收到一个纯文本消息。");
			try {
				System.out.println("消息内容是：" + textMsg.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else if (message instanceof MapMessage) {
			
		}
		else if(message instanceof Result){
			System.out.println("生产者接收到一个对象消息。");
			Result result = (Result) message;
			System.out.println(result);
		}
	}
}
