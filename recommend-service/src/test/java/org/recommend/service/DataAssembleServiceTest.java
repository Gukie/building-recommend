package org.recommend.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.recommend.ServiceBaseTest;

/**
 * @author gushu
 * @date 2017/11/06
 */
public class DataAssembleServiceTest extends ServiceBaseTest {

	@Resource(name="dataAssembleService")
	private DataAssembleService dataAssembleService;
	
	@Test
	public void testAssemblePlainTxt(){
		String plainTxt = dataAssembleService.assemblePlainTxt();
		System.err.println(plainTxt);
	}
}
