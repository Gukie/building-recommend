package org.data.service;

import com.alibaba.fastjson.JSON;

import org.common.model.BuildingDTO;
import org.data.model.db.BuildingDO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gushu
 * @date 2017/11/13
 */
public class MongoDataServiceTest extends ServiceBaseTest{

	@Autowired
	private MongoDataService dataService;
	
	@Test
	public void testAdd(){
		BuildingDTO buildingDTO = new BuildingDTO();
		buildingDTO.setName("junit test");
		buildingDTO.setLocation("yuhang");
		buildingDTO.setPlate("no");
		BuildingDO result = dataService.addDocument(buildingDTO);
		System.err.println(JSON.toJSONString(result));
	}
}
