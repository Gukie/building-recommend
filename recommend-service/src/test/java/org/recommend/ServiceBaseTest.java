package org.recommend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.recommend.app.RecommendServiceApp;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author gushu
 * @date 2017/11/06
 */
@RunWith(SpringRunner.class)
//@SpringBootTest (classes=RecommendServiceApp.class)
@SpringBootTest (classes=RecommendServiceApp.class,webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServiceBaseTest {

	@Test
	public void test(){
		
	}
}
