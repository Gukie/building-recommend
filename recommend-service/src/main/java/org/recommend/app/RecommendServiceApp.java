package org.recommend.app;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.common.constant.SpecialValues;
import org.common.result.BaseResult;
import org.recommend.service.RecommendService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * recommend service
 *
 */
@SpringBootApplication(scanBasePackages={"org.recommend"})
@EnableEurekaClient
@EnableFeignClients(basePackages="org.recommend.client")
@ImportResource(locations = { "classpath:spring-conf/spring-beans.xml" })
@Controller
public class RecommendServiceApp 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(RecommendServiceApp.class, args);
    }
    
    @Resource(name="recommendService")
    private RecommendService recommendService;
    
    @RequestMapping("/sendEmail")
    public String sendEmail(String email){
    	if(StringUtils.isEmpty(email)){
    		email = SpecialValues.DEFAULT_TO_EMAIL;
    	}
    	recommendService.recommend2Email(email);
    	return BaseResult.SUCCESS;
    }
}
