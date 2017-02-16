package com.tiantian.springintejms.listener; 
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.jms.BytesMessage;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import org.apache.activemq.BlobMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 
public class ConsumerMessageListener { 
	private static final Log logger = LogFactory.getLog(ConsumerMessageListener.class); 
    public static void main(String[] args) throws InterruptedException { 
    	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml"); 
        ctx.start();
    }
    public void receive(Object m) throws Exception { 
    	if (m instanceof TextMessage) { // 接收文本消息
			TextMessage message = (TextMessage) m;
			System.out.println(message.getText());
			
		} else if (m instanceof MapMessage) { // 接收键值对消息
			MapMessage message = (MapMessage) m;
			// System.out.println(message.getLong("age"));
			// System.out.println(message.getDouble("sarray"));
			// System.out.println(message.getString("username"));
		} else if (m instanceof StreamMessage) { // 接收流消息
			StreamMessage message = (StreamMessage) m;
			System.out.println(message.readString());
			System.out.println(message.readLong());
		} else if (m instanceof BytesMessage) { // 接收字节消息
			byte[] b = new byte[1024];
			int len = -1;
			BytesMessage message = (BytesMessage) m;
			while ((len = message.readBytes(b)) != -1) {
				System.out.println(new String(b, 0, len));
			}
		} else if (m instanceof ObjectMessage) { // 接收对象消息
			ObjectMessage message = (ObjectMessage) m;
			User user = (User) message.getObject();
			System.out.println(user);
		}else if(m instanceof BlobMessage){
			System.out.println("filename:"+ ((BlobMessage) m).getStringProperty("FILE.NAME"));
			System.out.println("filesize:"+ ((BlobMessage) m).getLongProperty("FILE.SIZE"));
			InputStream in = ((BlobMessage) m).getInputStream();
			String fileName=((Message) m).getStringProperty("FILE.NAME");
			File file =new File("D://"+System.currentTimeMillis()+fileName);
			OutputStream os = new FileOutputStream(file);
			   int bytesRead = 0;
			   byte[] buffer = new byte[8192];
			   while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
			      os.write(buffer, 0, bytesRead);
			   }
			   os.close();
			   in.close();
			 }else {
			System.out.println(m);
		}
    }
} 