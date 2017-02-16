package com.tiantian.springintejms.test;
import java.io.File;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.swing.JFileChooser;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.BlobMessage;

public class FileProduce {
	
	public static void main(String[] args) {
		System.out.println("传送文件 begin");
		File file = getFile();
		// 获取 ConnectionFactory
		// Activemq内置Http服务器（Jetty内置）
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
	            "tcp://localhost:61616?jms.blobTransferPolicy.defaultUploadUrl=http://localhost:8161/fileserver/");
		MessageProducer producer = null;
		ActiveMQSession session = null;
		Connection connection = null;
		try {
			// 创建 Connection
			connection = connectionFactory.createConnection();
			connection.start();
			// 创建 Session
			session = (ActiveMQSession) connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			// 创建 Destination
			Destination destination = session.createTopic("topic1");
			// 创建 Producer
			producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);// 设置为持久性
			// 设置持久性的话，文件也可以先缓存下来，接收端离线再连接也可以收到文件
			// 构造 BlobMessage，用来传输文件
			BlobMessage blobMessage = session.createBlobMessage(file);
			// 通过set方法对对象属性进行赋值
			blobMessage.setStringProperty("FILE.NAME", file.getName());
			blobMessage.setLongProperty("FILE.SIZE", file.length());
			blobMessage.setStringProperty("path", "");
			System.out.println("开始发送文件：" + file.getName() + "，文件大小："+ file.length() + " 字节");
			// 7. 发送文件
			producer.send(blobMessage);
			System.out.println("完成文件发送：" + file.getName());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				producer.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
			try {
				session.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		System.out.println("传送文件 begin");
	}
	private static File getFile() {
		// 选择要上传的文件
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("请选择要传送的文件");
		if (fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		File file = fileChooser.getSelectedFile();
		return file;
	}
}