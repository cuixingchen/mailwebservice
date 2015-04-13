package com.hdsx.taxi.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 邮件发送服务
 * 
 * @author cuipengfei
 *
 */
public class MailService {
	/**
	 * 邮件发送服务
	 * 
	 * @param to
	 *            收件地址
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @throws IOException
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public static void send_email(String to, String subject, String content)
			throws IOException, AddressException, MessagingException {
		Properties properties = PropertiesUtil.getInstance();
//		InputStream resourceAsStream = null;
//		try {
//			resourceAsStream = Object.class
//					.getResourceAsStream("/email.properties");
//			properties.load(resourceAsStream);
//		} finally {
//			if (resourceAsStream != null) {
//				resourceAsStream.close();
//			}
//		}
		System.err.println("properties:" + properties);
		properties.put("mail.smtp.host", properties.get("mail.smtp.host"));
		properties.put("mail.smtp.port", properties.get("mail.smtp.port"));
		properties.put("mail.smtp.auth", "true");
		Authenticator authenticator = new Email_Authenticator(properties.get(
				"username").toString(), properties.get("password").toString());
		javax.mail.Session sendMailSession = javax.mail.Session
				.getDefaultInstance(properties, authenticator);
		MimeMessage mailMessage = new MimeMessage(sendMailSession);
		mailMessage.setFrom(new InternetAddress(properties.get("username")
				.toString()));
		// Message.RecipientType.TO属性表示接收者的类型为TO
		mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(
				to));
		mailMessage.setSubject(subject, "UTF-8");
		mailMessage.setSentDate(new Date());
		// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
		Multipart mainPart = new MimeMultipart();
		// 创建一个包含HTML内容的MimeBodyPart
		BodyPart html = new MimeBodyPart();
		html.setContent(content.trim(), "text/html; charset=utf-8");
		mainPart.addBodyPart(html);
		mailMessage.setContent(mainPart);
		Transport.send(mailMessage);
	}

}
