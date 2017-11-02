package org.recommend.service;

/**
 * @author gushu
 * @date 2017/11/01
 */
public interface EmailService {

	boolean send(String targetEmail, String msgBodyTxt);
}
