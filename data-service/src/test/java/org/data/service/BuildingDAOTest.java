package org.data.service;

import java.util.List;

import org.data.dao.BuildingDAO;
import org.data.model.biz.BuildingAvgPriceDO;
import org.data.model.db.BuildingDO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public class BuildingDAOTest extends BaseDAOTest {

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
		result.setId("BD101709291957");
		result.setName("building-test");
		result.setAvgPriceTxt("24000元/㎡");
		result.setAvgPriceTxt("24000");
		result.setLocation("unknow");
		result.setPlate("滨江");

		return result;
	}

	@Test
	public void testGetExistingBuildingName() {
		List<String> result = buildingDAO.getExistingBuildingName();
		Assert.assertNotNull(result);
		for (String item : result) {
			System.out.println(item);
		}
	}

	@Test
	public void getGetAll() {
		List<BuildingDO> buildingDOList = buildingDAO.getAll();
		Assert.assertNotNull(buildingDOList);
		printBuildingList(buildingDOList);
	}

	private void printBuildingList(List<BuildingDO> buildingDOList) {
		if (CollectionUtils.isEmpty(buildingDOList)) {
			return;
		}
		for (BuildingDO item : buildingDOList) {
			System.out.println(item);
		}
	}

	@Test
	public void testUpdate() {
		// BD150885419158920
		// BuildingDO buildingDO = new BuildingDO();
		String buildingId = "BD150885419158920";
		BuildingDO buildingDO = buildingDAO.getById(buildingId);

		String originalName = buildingDO.getName();
		buildingDO.setName(originalName + "-update");
		int updated = buildingDAO.update(buildingDO);
		Assert.assertEquals(1, updated);
	}

	@Test
	public void testGetAvgPrice() {
		double avgPrice = buildingDAO.getAvgPrice();
		System.out.println(avgPrice);
	}

	@Test
	public void testGetAvgPricePerArea() {
		List<BuildingAvgPriceDO> result = buildingDAO.getAvgPricePerArea();
		for (BuildingAvgPriceDO item : result) {
			System.out.println(item.getLocation() + ":" + item.getAvgPricePerArea());
		}
	}
}
