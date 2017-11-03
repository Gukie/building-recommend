package org.recommend.oauth2;

/**
 * @author gushu
 * @date 2017/11/03
 */
public class OAuth2PropKeys {

	public static final String OAUTH_TOKEN = "mail.imaps.sasl.mechanisms.oauth2.oauthToken";
	
	public static final String GOOLGE_SASL_CLIENT_FACTORY_XOAUTH2_KEY = "SaslClientFactory.XOAUTH2";
//	public static final String GOOLGE_SASL_CLIENT_FACTORY_XOAUTH2_VALUE = "com.google.code.samples.oauth2.OAuth2SaslClientFactory";
	public static final String GOOLGE_SASL_CLIENT_FACTORY_XOAUTH2_VALUE = "org.recommend.oauth2.MyOAuth2SaslClientFactory";
	
}
