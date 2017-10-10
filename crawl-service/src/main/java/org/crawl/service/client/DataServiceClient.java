package org.crawl.service.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author gushu
 * @date 2017/10/10
 */
@FeignClient("data-service")
public interface DataServiceClient {

	@RequestMapping("/store")
	String addBuilding(String buildingJsonStr);
}
