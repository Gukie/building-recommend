/* Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.recommend.oauth2;

import java.security.Provider;
import java.security.Security;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Session;
import javax.mail.URLName;

import com.sun.mail.imap.IMAPSSLStore;
import com.sun.mail.imap.IMAPStore;
import com.sun.mail.smtp.SMTPTransport;

/**
 * refer: https://github.com/google/gmail-oauth2-tools
 * authentication.
 *
 * <p>
 * Before using this class, you must call {@code initialize} to install the
 * OAuth2 SASL provider.
 */
//@Service("oauth2Authenticator")
public class MyOAuth2Authenticator {
	private final Logger logger = Logger.getLogger(MyOAuth2Authenticator.class.getName());

	public final class OAuth2Provider extends Provider {
		private static final long serialVersionUID = 1L;

		public OAuth2Provider() {
			super("Google OAuth2 Provider", 1.0, "Provides the XOAUTH2 SASL Mechanism");
			put(OAuth2PropKeys.GOOLGE_SASL_CLIENT_FACTORY_XOAUTH2_KEY, OAuth2PropKeys.GOOLGE_SASL_CLIENT_FACTORY_XOAUTH2_VALUE);
		}
	}

	/**
	 * Installs the OAuth2 SASL provider. This must be called exactly once
	 * before calling other methods on this class.
	 */
	public void init() {
		Security.addProvider(new OAuth2Provider());
	}

	/**
	 * Connects and authenticates to an IMAP server with OAuth2. You must have
	 * called {@code initialize}.
	 *
	 * @param host
	 *            Hostname of the imap server, for example {@code
	 *     imap.googlemail.com}.
	 * @param port
	 *            Port of the imap server, for example 993.
	 * @param userEmail
	 *            Email address of the user to authenticate, for example
	 *            {@code oauth@gmail.com}.
	 * @param oauthToken
	 *            The user's OAuth token.
	 * @param debug
	 *            Whether to enable debug logging on the IMAP connection.
	 *
	 * @return An authenticated IMAPStore that can be used for IMAP operations.
	 */
	public IMAPStore getAnthenticatedImapStore(String host, int port, String userEmail, String oauthToken,
			boolean debug) throws Exception {
		Properties props = new Properties();
		props.put("mail.imaps.sasl.enable", "true");
		props.put("mail.imaps.sasl.mechanisms", "XOAUTH2");
		props.put(OAuth2PropKeys.OAUTH_TOKEN, oauthToken);
		Session session = Session.getInstance(props);
		session.setDebug(debug);

		final URLName unusedUrlName = null;
		IMAPSSLStore store = new IMAPSSLStore(session, unusedUrlName);
		final String emptyPassword = "";
		store.connect(host, port, userEmail, emptyPassword);
		return store;
	}

	/**
	 * Connects and authenticates to an SMTP server with OAuth2. You must have
	 * called {@code initialize}.
	 *
	 * @param host
	 *            Hostname of the smtp server, for example {@code
	 *     smtp.googlemail.com}.
	 * @param port
	 *            Port of the smtp server, for example 587.
	 * @param userEmail
	 *            Email address of the user to authenticate, for example
	 *            {@code oauth@gmail.com}.
	 * @param oauthToken
	 *            The user's OAuth token.
	 * @param debug
	 *            Whether to enable debug logging on the connection.
	 *
	 * @return An authenticated SMTPTransport that can be used for SMTP
	 *         operations.
	 */
	public SMTPTransport getSmtpTransport(Session session,String host, int port, String userEmail) throws Exception {
		final URLName unusedUrlName = null;
		SMTPTransport transport = new SMTPTransport(session, unusedUrlName);
		// If the password is non-null, SMTP tries to do AUTH LOGIN.
		final String emptyPassword = "";
		transport.connect(host, port, userEmail, emptyPassword);

		return transport;
	}

	public Session getSmtpSession(String oauthToken, boolean debug) {
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.starttls.required", "true");
		props.put("mail.smtp.sasl.enable", "true");
		props.put("mail.smtp.sasl.mechanisms", "XOAUTH2");
		props.put(OAuth2PropKeys.OAUTH_TOKEN, oauthToken);
		Session session = Session.getInstance(props);
		session.setDebug(debug);
		return session;
	}

	// /**
	// * Authenticates to IMAP with parameters passed in on the commandline.
	// */
	// public static void main(String args[]) throws Exception {
	// if (args.length != 2) {
	// System.err.println(
	// "Usage: OAuth2Authenticator <email> <oauthToken>");
	// return;
	// }
	// String email = args[0];
	// String oauthToken = args[1];
	//
	// initialize();
	//
	// IMAPStore imapStore = getAnthenticatedImapStore("imap.gmail.com",
	// 993,
	// email,
	// oauthToken,
	// true);
	// System.out.println("Successfully authenticated to IMAP.\n");
	// SMTPTransport smtpTransport = getSmtpTransport("smtp.gmail.com",
	// 587,
	// email,
	// oauthToken,
	// true);
	// System.out.println("Successfully authenticated to SMTP.");
	// }
}
