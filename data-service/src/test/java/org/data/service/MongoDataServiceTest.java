package org.data.service;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;

import org.common.enums.PlateTypeEnum;
import org.common.model.BuildingAvgPriceDTO;
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
	
	@Test
	public void testDeleteAll(){
		dataService.deleteAll();
	}
	
	@Test
	public void testUpdate(){
		BuildingDTO buildingDTO = new BuildingDTO();
		buildingDTO.setId("BD15109101593952");
		buildingDTO.setAvgPrice(23333);
		buildingDTO.setAvgPriceTxt("23333");
		buildingDTO.setGmtModified(new Date());
		buildingDTO.setName("江滨澜廷");
		BuildingDO result = dataService.addDocument(buildingDTO);
		System.err.println(JSON.toJSONString(result));
	}
	
	@Test
	public void testGetAvgPriceByPlateType(){
		List<BuildingAvgPriceDTO>  result = dataService.getAvgPriceByPlateType(PlateTypeEnum.PER);
		org.junit.Assert.assertNotNull(result);
		for(BuildingAvgPriceDTO item: result){
			System.err.println(item.getPlate()+":"+item.getAvgPrice());
		}
		
		try {
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
