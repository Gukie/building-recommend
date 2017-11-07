package org.recommend.service;

import java.io.File;

/**
 * @author gushu
 * @date 2017/11/01
 */
public interface EmailService {

	boolean send(String targetEmail, String msgBodyTxt);
	
	boolean sendWithAttachment(String targetEmail, File attachment);
}
