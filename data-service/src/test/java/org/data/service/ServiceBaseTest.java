package org.data.service;

import org.data.app.DataServiceApp;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author gushu
 * @date 2017/11/09
 */
@RunWith(SpringRunner.class)
@SpringBootTest (classes=DataServiceApp.class,webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServiceBaseTest {

}
