package org.recommend.app;

import javax.annotation.Resource;

import org.common.result.BaseResult;
import org.recommend.service.EmailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * recommend service
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages="org.recommend.client")
@Controller
public class RecommendServiceApp 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(RecommendServiceApp.class, args);
    }
    
    @Resource(name="emailService")
    private EmailService emailService;
    
    @RequestMapping("/sendEmail")
    public String sendEmail(String email){
    	emailService.recommend(email);
    	return BaseResult.SUCCESS;
    }
}
