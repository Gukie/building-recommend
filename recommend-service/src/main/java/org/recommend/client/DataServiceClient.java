package org.recommend.client;

import java.util.List;

import org.common.model.BuildingAvgPriceDTO;
import org.common.model.BuildingDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author gushu
 * @date 2017/11/01
 */
@FeignClient("data-service")
public interface DataServiceClient {

	@RequestMapping(value = "/getAvgPriceByPlateType")
	List<BuildingAvgPriceDTO> getAvgPriceByPlateType(String plateType);
	
	@RequestMapping(value = "/getBuildingByCondition")
	List<BuildingDTO> getBuildingByCondition(String buildingQueryTxt) ;
}
