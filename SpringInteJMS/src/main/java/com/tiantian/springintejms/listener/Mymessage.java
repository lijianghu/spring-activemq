package com.tiantian.springintejms.listener;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQStreamMessage;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

public class Mymessage implements MessageConverter {

	public Message toMessage(Object object, Session session)
			throws JMSException, MessageConversionException {
		// ObjectMessage m = session.createObjectMessage((Serializable) object);
		ActiveMQStreamMessage m = (ActiveMQStreamMessage) session.createStreamMessage();
		StreamMessage streamMessage = session.createStreamMessage(); 
		return m;
	}
	public Object fromMessage(Message m) throws JMSException,
			MessageConversionException {
		if (m instanceof TextMessage) { // �����ı���Ϣ
			TextMessage message = (TextMessage) m;
			System.out.println(message.getText());
		} else if (m instanceof MapMessage) { // ���ռ�ֵ����Ϣ
			MapMessage message = (MapMessage) m;
			// System.out.println(message.getLong("age"));
			// System.out.println(message.getDouble("sarray"));
			// System.out.println(message.getString("username"));
		} else if (m instanceof StreamMessage) { // ��������Ϣ
			StreamMessage message = (StreamMessage) m;
			System.out.println(message.readString());
			System.out.println(message.readLong());
		} else if (m instanceof BytesMessage) { // �����ֽ���Ϣ
			byte[] b = new byte[1024];
			int len = -1;
			BytesMessage message = (BytesMessage) m;
			while ((len = message.readBytes(b)) != -1) {
				System.out.println(new String(b, 0, len));
			}
		} else if (m instanceof ObjectMessage) { // ���ն�����Ϣ
			ObjectMessage message = (ObjectMessage) m;
			User user = (User) message.getObject();
			System.out.println(user);
		} else {
			System.out.println(m);
		}
		return m;
	}
}
