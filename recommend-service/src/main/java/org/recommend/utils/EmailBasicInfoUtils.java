package org.recommend.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

/**
 * @author gushu
 * @date 2017/11/02
 */
public class EmailBasicInfoUtils {

//	private final static String EMAIL_CONFIG_FILE_NAME = "classpath*:email-config.properties";

	private final static String EMAIL_CONFIG_FILE_NAME = "email-conf/email-config.properties";

	private static Properties props;

	public static Properties getBasicProperties() {
		 
		if(props == null){
			synchronized (EmailBasicInfoUtils.class) {
				if(props == null){
					initProps();
				}
			}
		}
		return props;
	}

	private static void initProps() {
		InputStream inputStream = EmailBasicInfoUtils.class.getClassLoader().getResourceAsStream(EMAIL_CONFIG_FILE_NAME);
		if (inputStream == null) {
			System.err.println("cannot load email properties");
			return;
		}
		props = System.getProperties();
		try {
			props.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Authenticator generateAuth() {
		Properties properties = getBasicProperties();
		final String from = properties.getProperty(RecommendPropKeys.MAIL_USER);
		final String password = properties.getProperty(RecommendPropKeys.MAIL_USER_PASSWORD);
		return new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		};
	}
	
	public static Session getSession(){
		Properties props = getBasicProperties();
		Authenticator auth = generateAuth();
		return Session.getDefaultInstance(props, auth);
//		return Session.getInstance(props, auth);
	}
}
