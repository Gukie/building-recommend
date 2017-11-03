package org.recommend.service.impl;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;
import org.recommend.oauth2.MyOAuth2Authenticator;
import org.recommend.service.EmailService;
import org.recommend.utils.EmailBasicInfoUtils;
import org.recommend.utils.RecommendPropKeys;
import org.springframework.stereotype.Service;

/**
 * refer: https://www.journaldev.com/2532/javamail-example-send-mail-in-java-smtp
 * 
 * @author gushu
 * @date 2017/11/01
 */
 @Service("emailService")
public class EmailServiceImpl implements EmailService {
	 
	 @Resource(name="oauth2Authenticator")
	 private MyOAuth2Authenticator oAuth2Authenticator;

	public boolean send(String to, String msgBodyTxt) {
//		String subject = EmailBasicInfoUtils.getBasicProperties().getProperty(RecommendPropKeys.MAIL_SUBJECT);
//		String from = EmailBasicInfoUtils.getBasicProperties().getProperty(RecommendPropKeys.MAIL_USER);
//		Session session = EmailBasicInfoUtils.getSession();
//		session.setDebug(true);
//		return sendEmail(session,from, to, subject, msgBodyTxt);
		
		String host = EmailBasicInfoUtils.getBasicProperties().getProperty(RecommendPropKeys.MAIL_SMTP_HOST);
		Integer port = Integer.valueOf(EmailBasicInfoUtils.getBasicProperties().getProperty(RecommendPropKeys.MAIL_SMTP_PORT));
		String userEmail = EmailBasicInfoUtils.getBasicProperties().getProperty(RecommendPropKeys.MAIL_USER);
		String oauthToken = EmailBasicInfoUtils.getBasicProperties().getProperty(RecommendPropKeys.MAIL_OAUTH_TOKEN);
		Boolean debug = Boolean.valueOf(EmailBasicInfoUtils.getBasicProperties().getProperty(RecommendPropKeys.MAIL_DEBUG));
		
		Session session = oAuth2Authenticator.getSmtpSession(oauthToken, debug);
		return sendByGmail(session,host, port, userEmail,to, oauthToken, debug);
	}

	public boolean sendByGmail(Session session,String host, int port, String userEmail,String toEmail, String oauthToken,
			boolean debug) {
		try {

	        Message message = new MimeMessage(session);
	        Address fromAddress = new InternetAddress(userEmail);
	        Address toAddress = new InternetAddress(toEmail);

	        message.setFrom(fromAddress);
	        message.setRecipient(Message.RecipientType.TO, toAddress);

	        message.setSubject("Hello Gmail");
	        message.setText("test from lokia");
	        SMTPTransport transport = oAuth2Authenticator.getSmtpTransport(session,host, port, userEmail);
	        System.err.println("get smtp transport successfully ");
	        transport.sendMessage(message,message.getAllRecipients());
	        transport.close();
	        return true;
	    }catch(Exception ex){
	    	ex.printStackTrace();
	    	return false;
	    }
	}

//	public boolean sendEmail(Session session,String from, String to, String subject, String body) {
//	try {
//		MimeMessage msg = new MimeMessage(session);
//		// set message headers
//		msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
//		msg.addHeader("format", "flowed");
//		msg.addHeader("Content-Transfer-Encoding", "8bit");
//
//		msg.setFrom(new InternetAddress(from, "lokia"));
//		msg.setReplyTo(InternetAddress.parse(from, false));
//		msg.setSubject(subject,"UTF-8");
//		msg.setText(body,"UTF-8");
//		msg.setSentDate(new Date());
//		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
//		System.out.println("Message is ready");
//		Transport.send(msg);
////		Transport transport = session.getTransport("smtp");
//		
//		System.out.println("EMail Sent Successfully!!");
//		return true;
//	} catch (Exception e) {
//		e.printStackTrace();
//		return false;
//	}
//}
 }
