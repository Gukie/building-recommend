package org.recommend.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.recommend.app.RecommendServiceApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author gushu
 * @date 2017/11/02
 */
@RunWith(SpringRunner.class)
@SpringBootTest (classes=RecommendServiceApp.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//@Transactional
public class EmailServiceTest {

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
