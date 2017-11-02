package org.recommend.service.impl;

import java.util.Date;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.recommend.service.EmailService;
import org.recommend.utils.EmailBasicInfoUtils;
import org.recommend.utils.RecommendPropKeys;
import org.springframework.stereotype.Service;

/**
 * @author gushu
 * @date 2017/11/01
 */
 @Service("emailService")
public class EmailServiceImpl implements EmailService {

	public boolean send(String to, String msgBodyTxt) {
		String subject = EmailBasicInfoUtils.getBasicProperties().getProperty(RecommendPropKeys.MAIL_SUBJECT);
		Session session = EmailBasicInfoUtils.getSession();
		return sendEmail(session, to, subject, msgBodyTxt);
	}

	public boolean sendEmail(Session session, String toEmail, String subject, String body) {
		try {
			MimeMessage msg = new MimeMessage(session);
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress("no_reply@journaldev.com", "NoReply-JD"));
			msg.setReplyTo(InternetAddress.parse("no_reply@journaldev.com", false));
			msg.setSubject(subject, "UTF-8");
			msg.setText(body, "UTF-8");

			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			System.out.println("Message is ready");
			Transport.send(msg);
			System.out.println("EMail Sent Successfully!!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
