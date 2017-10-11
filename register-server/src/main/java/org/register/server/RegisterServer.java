package org.register.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * refer: 
 * <li> http://www.baeldung.com/spring-cloud-netflix-eureka
 * <li> https://www.mkyong.com/spring-boot/spring-boot-profile-based-properties-and-yaml-example/
 *
 */
@SpringBootApplication
@EnableEurekaServer
//@EnableHystrix
@EnableHystrixDashboard
public class RegisterServer 
{
    public static void main( String[] args )
    {
       SpringApplication.run(RegisterServer.class, args);
    }
}
