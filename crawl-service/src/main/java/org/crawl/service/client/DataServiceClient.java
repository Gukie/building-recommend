package org.crawl.service.client;

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

	@RequestMapping(value = "/store", method = RequestMethod.POST,consumes={MediaType.APPLICATION_JSON_VALUE})
	String addBuilding(String buildingJsonStr);

	@RequestMapping("/test")
	String test(@RequestParam("test") String test);

	// @RequestMapping(value="/store",method=RequestMethod.POST,consumes =
	// "application/json")
	// String addBuilding( @RequestParam("buildingJsonStr") String
	// buildingJsonStr);

	// @RequestMapping(value="/store",method=RequestMethod.POST,consumes =
	// "application/json")
	// String addBuilding( String buildingJsonStr);

	// @RequestMapping(value="/store",method=RequestMethod.POST,consumes =
	// "application/json")
	// String addBuilding( @HeaderParam("buildingJsonStr") String
	// buildingJsonStr);

	// @RequestMapping(value="/store",method=RequestMethod.POST,consumes =
	// "application/json")
	// @RequestLine("POST /store")
	// String addBuilding( @Param("buildingJsonStr") String buildingJsonStr);

	// @RequestMapping(value="/store",method=RequestMethod.POST)
	// String addBuilding( String buildingJsonStr);

}
