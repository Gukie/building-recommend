package org.recommend.service;

import org.junit.Assert;
import org.junit.Test;
import org.recommend.ServiceBaseTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gushu
 * @date 2017/11/02
 */
public class EmailServiceTest extends ServiceBaseTest{

	@Autowired
	private EmailService emailService;
	
	@Test
	public void testSend(){
		String to = "zjut_gu@163.com";
		String msgBodyTxt = "hello, mail";
		boolean isSent = emailService.send(to, msgBodyTxt);
		Assert.assertTrue(isSent);
	}
}
