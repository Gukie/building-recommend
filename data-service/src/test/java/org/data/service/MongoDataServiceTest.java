package org.data.service;

import java.util.Date;

import com.alibaba.fastjson.JSON;

import org.common.model.BuildingDTO;
import org.data.enums.DBTableEnum;
import org.data.model.db.BuildingDO;
import org.data.utils.GeneratorUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gushu
 * @date 2017/11/13
 */
public class MongoDataServiceTest extends ServiceBaseTest{

	@Autowired
	private MongoDataService dataService;
	
	@Autowired
	private GeneratorUtils generator;
	
	@Test
	public void testAdd(){
		BuildingDTO buildingDTO = new BuildingDTO();
		buildingDTO.setId(generator.generateId(DBTableEnum.building));
		buildingDTO.setName("junit test");
		buildingDTO.setLocation("yuhang");
		buildingDTO.setPlate("no");
		buildingDTO.setGmtCreated(new Date());
		buildingDTO.setGmtModified(new Date());
		BuildingDO result = dataService.addDocument(buildingDTO);
		System.err.println(JSON.toJSONString(result));
	}
}
