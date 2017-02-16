package com.tiantian.springintejms.listener;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * ��������Ϣ����
 * @Title: ConsumerMessageListener.java
 * @Package: com.tiantian.springintejms.listener
 * @Description: TODO
 * 
 ******************************************************** 
 * Date				Author 		Changes 
 * 2017��2��13��	        zx			����
 ********************************************************
 */
public class ConsumerMessageListener implements MessageListener {
	
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			//��������֪�������߷��͵ľ���һ�����ı���Ϣ�������������ֱ�ӽ���ǿ��ת��������ֱ�Ӱ�onMessage�����Ĳ����ĳ�Message������TextMessage
			TextMessage textMsg = (TextMessage) message;
			System.out.println("�����߽��յ�һ�����ı���Ϣ��");
			try {
				System.out.println("��Ϣ�����ǣ�" + textMsg.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else if (message instanceof MapMessage) {
			
		}
		else if(message instanceof Result){
			System.out.println("�����߽��յ�һ��������Ϣ��");
			Result result = (Result) message;
			System.out.println(result);
		}
	}
}
