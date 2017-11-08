package org.recommend.service.impl;

import java.io.File;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.smtp.SMTPTransport;
import org.apache.commons.lang.StringUtils;
import org.common.constant.SpecialValues;
import org.recommend.oauth2.MyOAuth2Authenticator;
import org.recommend.service.EmailService;
import org.recommend.utils.EmailBasicInfoUtils;
import org.recommend.utils.RecommendPropKeys;
import org.springframework.stereotype.Service;

/**
 * refer:
 * https://www.journaldev.com/2532/javamail-example-send-mail-in-java-smtp
 * 
 * @author gushu
 * @date 2017/11/01
 */
@Service("emailService")
public class EmailServiceImpl implements EmailService {

	@Resource(name = "oauth2Authenticator")
	private MyOAuth2Authenticator oAuth2Authenticator;

	public boolean send(String to, String msgBodyTxt) {
		System.err.println("msgBody:" + msgBodyTxt);
		String oauthToken = EmailBasicInfoUtils.getBasicProperties().getProperty(RecommendPropKeys.MAIL_OAUTH_TOKEN);
		Boolean debug = Boolean
				.valueOf(EmailBasicInfoUtils.getBasicProperties().getProperty(RecommendPropKeys.MAIL_DEBUG));

		Session session = oAuth2Authenticator.getSmtpSession(oauthToken, debug);
		return sendByGmail(session, to, msgBodyTxt, null, oauthToken, debug);
	}

	public boolean sendByGmail(Session session, String toEmail, String msgBody, Multipart attachment, String oauthToken,
			boolean debug) {

		String host = EmailBasicInfoUtils.getBasicProperties().getProperty(RecommendPropKeys.MAIL_SMTP_HOST);
		Integer port = Integer
				.valueOf(EmailBasicInfoUtils.getBasicProperties().getProperty(RecommendPropKeys.MAIL_SMTP_PORT));
		String userEmail = EmailBasicInfoUtils.getBasicProperties().getProperty(RecommendPropKeys.MAIL_USER);
		String subject = EmailBasicInfoUtils.getBasicProperties().getProperty(RecommendPropKeys.MAIL_SUBJECT);
		try {

			Message message = new MimeMessage(session);
			Address fromAddress = new InternetAddress(userEmail);
			Address toAddress = new InternetAddress(toEmail);

			message.setFrom(fromAddress);
			message.setRecipient(Message.RecipientType.TO, toAddress);
			message.setSubject(subject);
			message.setText(getMsgBodyTxt(msgBody));
			if (attachment != null) {
				message.setContent(attachment);
			}
			SMTPTransport transport = oAuth2Authenticator.getSmtpTransport(session, host, port, userEmail);
			System.err.println("get smtp transport successfully ");
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.err.println("email sent successfully");
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	private String getMsgBodyTxt(String msgBody) {
		if (StringUtils.isEmpty(msgBody)) {
			return SpecialValues.DEFAULT_EMAIL_BODY;
		}
		return msgBody;
	}

	public boolean sendWithAttachment(String targetEmail, File attachedFile) {
		String oauthToken = EmailBasicInfoUtils.getBasicProperties().getProperty(RecommendPropKeys.MAIL_OAUTH_TOKEN);
		Boolean debug = Boolean
				.valueOf(EmailBasicInfoUtils.getBasicProperties().getProperty(RecommendPropKeys.MAIL_DEBUG));
		Session session = oAuth2Authenticator.getSmtpSession(oauthToken, debug);
		Multipart attachment = generateAttachment(attachedFile);
		return sendByGmail(session, targetEmail, null, attachment, oauthToken, debug);
	}

	private Multipart generateAttachment(File attachment) {
		if (attachment == null) {
			return null;
		}
		Multipart multipart = new MimeMultipart();
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		try {
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(attachment);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(SpecialValues.EMAIL_ATTACHMENT_FILE_NAME);
			multipart.addBodyPart(messageBodyPart);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return multipart;
	}
}
