package org.crawl.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSON;

import org.common.model.BuildingDTO;
import org.crawl.client.DataServiceClient;
import org.crawl.service.StoreService;
import org.crawl.utils.PoolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author gushu
 * @date 2017/10/18
 */
@Service("storeService")
public class StoreServiceImpl implements StoreService {

	@Autowired
    private DataServiceClient dataServiceClient;
	
	public void store() {
		List<BuildingDTO> buildingDTOList = PoolUtils.BUILDING_POOL;
		if(CollectionUtils.isEmpty(buildingDTOList)){
			return;
		}
		for(BuildingDTO item: buildingDTOList){
			dataServiceClient.addBuilding(JSON.toJSONString(item));
		}
	}

}
