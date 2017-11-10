package org.data.service;

import javax.annotation.Resource;

import org.common.model.BuildingDTO;
import org.data.enums.DBTableEnum;
import org.data.utils.GeneratorUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gushu
 * @date 2017/11/09
 */
public class ElkDataServiceTest extends ServiceBaseTest {

	@Resource(name = "elkDataService")
	private ElkDataService elkDataService;
	
	@Autowired
	private GeneratorUtils generator;

	@Test
	public void testIndex() {
		BuildingDTO buildingDTO  = new BuildingDTO();
		buildingDTO.setId(generator.generateId(DBTableEnum.building));
		buildingDTO.setName("古树");
		buildingDTO.setLocation("滨江");
		boolean isCreated =  elkDataService.index(buildingDTO);
		Assert.assertTrue(isCreated);
	}
}
