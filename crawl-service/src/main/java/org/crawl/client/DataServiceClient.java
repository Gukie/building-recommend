package org.crawl.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author gushu
 * @date 2017/10/10
 */
@FeignClient("data-service")
public interface DataServiceClient {

	@RequestMapping("/test")
	String test();
	
	@RequestMapping("/hello")
	String hello(@RequestParam("content") String content);

	// useful api.
	@RequestMapping(value = "/store", method = RequestMethod.POST,consumes={MediaType.APPLICATION_JSON_VALUE})
	String addBuilding(String buildingJsonStr);
	
	
	@RequestMapping(value = "/getExistingBuildingName", method = RequestMethod.GET)
	String getExistingBuildingName();
	
	@RequestMapping(value = "/createIndex", method = RequestMethod.POST,consumes={MediaType.APPLICATION_JSON_VALUE})
	String createIndex(String buildingJsonStr);
	
	@RequestMapping(value = "/addDocument", method = RequestMethod.POST,consumes={MediaType.APPLICATION_JSON_VALUE})
	String addDocument(String buildingJsonStr);

	@RequestMapping(value="addDoc2Redis",method=RequestMethod.POST,consumes={MediaType.APPLICATION_JSON_VALUE})
	String addDoc2Redis(String buildingJsonStr);
	
}
