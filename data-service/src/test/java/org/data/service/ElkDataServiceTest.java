package org.data.service;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;

import org.common.model.BuildingDTO;
import org.data.enums.DBTableEnum;
import org.data.utils.GeneratorUtils;
import org.elasticsearch.action.index.IndexResponse;
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
		IndexResponse response = elkDataService.index(buildingDTO);
		System.err.println(JSON.toJSONString(response));
	}
}
