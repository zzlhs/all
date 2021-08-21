package com.zzl.util;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;


public class EmailUtil {

	// 邮件发送协议
	private final static String PROTOCOL = "smtp";

	// SMTP邮件服务器
	private final static String HOST = "smtp.qq.com";

	// SMTP邮件服务器默认端口
	private final static String PORT = "465";

	// 是否要求身份认证
	private final static String IS_AUTH = "true";

	// 是否启用调试模式（启用调试模式可打印客户端与服务器交互过程时一问一答的响应消息）
	private final static String IS_ENABLED_DEBUG_MOD = "false";

	private final static String FROM = "@qq.com";

	private final static String PASS = "gxzdejlzsemysbaei"; // 16位授权码
	// 初始化连接邮件服务器的会话信息
	private static Properties props = null;

	static {
		props = new Properties();
		props.setProperty("mail.host",HOST);
		props.setProperty("mail.transport.protocol",PROTOCOL);
		props.setProperty("mail.smtp.auth",IS_AUTH);
		
		MailSSLSocketFactory sf;
		try {
			sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.ssl.socketFactory", sf);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}

	public static void send(String subject, String body) throws AddressException, MessagingException {
		List<String> emails = new ArrayList<String>(); // 接收人的邮箱号
		emails.add("18231664995@163.com");
		if (emails == null || emails.size() <= 0) {
			return;
		}
		
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(FROM, PASS);
			}
		});
		
//		session.setDebug(true);
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(FROM)); //发送人
		message.setSubject(subject); 
		
		Address[] addresses = new Address[emails.size()];
		for (int i = 0; i < emails.size(); i++) {
			addresses[i] = new InternetAddress(emails.get(i));
		}
		message.setRecipients(RecipientType.TO, addresses);
		message.setSentDate(new Date());
		message.setText(body);
		message.saveChanges();
		Transport transport = session.getTransport();
		transport.connect(HOST, FROM, PASS);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}

	public static void main(String[] args) throws AddressException, MessagingException {
		send("抓紧处理", "测试1");
		System.out.println("发送成功");
	}
}
