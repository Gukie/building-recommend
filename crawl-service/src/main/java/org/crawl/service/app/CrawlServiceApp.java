package org.crawl.service.app;

import com.alibaba.fastjson.JSON;

import org.common.model.BuildingDTO;
import org.common.result.BaseResult;
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
//    	HystrixThreadPoolProperties.Setter().withMaximumSize(10);
//		HystrixThreadPoolProperties.Setter().withMaxQueueSize(100);
        SpringApplication.run(CrawlServiceApp.class, args);
    }
    
//    @Bean
//    @LoadBalanced
//    RestTemplate restTemplate(){
//      return new RestTemplate();
//    }
    
    @Autowired
    private DataServiceClient dataServiceClient;
    
//    @Autowired
//    private RestTemplate restTemplate;
    
    @RequestMapping("/start")
    public String start(){
    	BuildingDTO buildingDTO = new BuildingDTO();
    	buildingDTO.setName("first building");
    	buildingDTO.setPlate("川西");
    	try {
    		dataServiceClient.addBuilding(JSON.toJSONString(buildingDTO));
    		return BaseResult.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return BaseResult.FAIL;
//    	Map<String, String> params = new HashMap<String, String>();
//    	params.put("buildingJsonStr", JSON.toJSONString(buildingDTO));
//    	
//    	return restTemplate.postForObject("http://data-service/store", JSON.toJSONString(buildingDTO), String.class,params);
    	
    }
    
    @RequestMapping("/test")
    public String test(){
    	return dataServiceClient.test("test");
//    	Map<String, String> params = new HashMap<String, String>();
//    	params.put("buildingJsonStr", JSON.toJSONString(buildingDTO));
//    	
//    	return restTemplate.postForObject("http://data-service/store", JSON.toJSONString(buildingDTO), String.class,params);
    	
    }
}
