package org.crawl.app;

import java.net.URLEncoder;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;

import org.common.model.BuildingDTO;
import org.common.result.BaseResult;
import org.crawl.client.DataServiceClient;
import org.crawl.service.CrawlService;
import org.crawl.service.StoreService;
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
@SpringBootApplication(scanBasePackages={"org.crawl.service.bean","org.crawl.service"})
@EnableEurekaClient
@EnableFeignClients(basePackages="org.crawl.client")
@Controller
public class CrawlServiceApp 
{
    public static void main( String[] args )
    {
//    	HystrixThreadPoolProperties.Setter().withMaximumSize(10);
//		HystrixThreadPoolProperties.Setter().withMaxQueueSize(100);
        SpringApplication.run(CrawlServiceApp.class, args);
    }
    
    @Autowired
    private DataServiceClient dataServiceClient;
    
    @Resource(name="crawlService")
    private CrawlService crawlService;
    
    @Resource(name="storeService")
    private StoreService storeService;
    
    
    @RequestMapping("/start")
    public String start(){
//    	BuildingDTO buildingDTO = new BuildingDTO();
//    	buildingDTO.setName("first building");
//    	buildingDTO.setPlate("川西");
//    	try {
//    		dataServiceClient.addBuilding(JSON.toJSONString(buildingDTO));
//    		return BaseResult.SUCCESS;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    	return BaseResult.FAIL;
    	
    	try {
    		Thread thread = new Thread(new Runnable() {
				public void run() {
					crawlService.crawl();
		    		storeService.store();
				}
			});
    		thread.start();
    		return BaseResult.SUCCESS;
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return BaseResult.FAIL;
    }
    
    @RequestMapping("/hello")
    public String hello(){
    	BuildingDTO buildingDTO = new BuildingDTO();
    	buildingDTO.setName("first building");
    	buildingDTO.setPlate("川西");
    	try {
    		dataServiceClient.hello(URLEncoder.encode(JSON.toJSONString(buildingDTO),"UTF-8"));
    		return BaseResult.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return BaseResult.FAIL;
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
