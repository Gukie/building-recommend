package org.crawl.service.app;

import com.alibaba.fastjson.JSON;

import org.common.model.BuildingDTO;
import org.crawl.service.client.DataServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * crawl service
 *
 */
@SpringBootApplication(scanBasePackages="org.crawl.service.bean")
@EnableEurekaClient
@EnableFeignClients(basePackages="org.crawl.service.client")
@Controller
public class CrawlServiceApp 
{
    public static void main( String[] args )
    {
        SpringApplication.run(CrawlServiceApp.class, args);
    }
    
    @Autowired
    private DataServiceClient dataServiceClient;
    
    @RequestMapping("/start")
    public String start(){
    	BuildingDTO buildingDTO = new BuildingDTO();
    	buildingDTO.setName("first building");
    	buildingDTO.setPlate("川西");
    	return dataServiceClient.addBuilding(JSON.toJSONString(buildingDTO));
    }
}
