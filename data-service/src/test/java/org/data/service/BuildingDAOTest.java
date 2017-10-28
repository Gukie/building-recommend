package org.data.service;

import java.util.List;

import org.data.dao.BuildingDAO;
import org.data.model.BuildingDO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BuildingDAOTest extends BaseDAOTest{

	@Autowired
	private BuildingDAO buildingDAO;
	
	@Test
	public void testInsert() {
		BuildingDO buildingDO = generateDO();
		Integer insertedRow = buildingDAO.insert(buildingDO);
		Assert.assertEquals(1, insertedRow.intValue());
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	protected BuildingDO generateDO() {
		BuildingDO result = new BuildingDO();
		result.setId("BD201709291957");
		result.setName("building-test");
		result.setAvgPrice("2.8");
		result.setLocation("unknow");
		result.setPlate("滨江");
		
		return result;
	}
	
	@Test
	public void testGetExistingBuildingName(){
		List<String> result = buildingDAO.getExistingBuildingName();
		Assert.assertNotNull(result);
		for(String item: result){
			System.out.println(item);
		}
	}
}
