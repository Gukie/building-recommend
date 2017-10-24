package org.crawl.service.impl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

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
		BlockingQueue<BuildingDTO> buildingDTOList = PoolUtils.BUILDING_POOL;
		if(CollectionUtils.isEmpty(buildingDTOList)){
			return;
		}
		try {
			BuildingDTO buildingDTO = buildingDTOList.poll(100, TimeUnit.MILLISECONDS);
			if(buildingDTO!=null){
				dataServiceClient.addBuilding(JSON.toJSONString(buildingDTO));
				System.out.println("added: "+PoolUtils.ADDED_COUNTER.incrementAndGet());
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		for(BuildingDTO item: buildingDTOList){
//			dataServiceClient.addBuilding(JSON.toJSONString(item));
//		}
	}

}
