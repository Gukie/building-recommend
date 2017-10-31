package org.data.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.common.model.BuildingDTO;
import org.data.dao.BuildingDAO;
import org.data.enums.DBTableEnum;
import org.data.model.BuildingDO;
import org.data.service.DataService;
import org.data.utils.CacheDataUtils;
import org.data.utils.GeneratorUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author gushu
 * @date 2017/10/09
 */
@Service("dataService")
public class DataServiceImpl implements DataService{

	@Autowired
	private BuildingDAO buildingDAO;
	
	@Autowired
	private GeneratorUtils generator;
	
	@Transactional
	public String add(BuildingDTO buildingDTO) {
		BuildingDO existingBuilding = getBuildingFromCache(buildingDTO.getName());
		if(existingBuilding !=null){
			update(buildingDTO,existingBuilding);
			return existingBuilding.getId();
		}
		
		BuildingDO buildingDO = convert2DO(buildingDTO);
		String id = generator.generateId(DBTableEnum.building);
		buildingDO.setId(id);
		try {
			buildingDAO.insert(buildingDO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return buildingDO.getId();
	}

	private void update(BuildingDTO buildingDTO, BuildingDO existingBuilding) {
		String buildingId = existingBuilding.getId();
		buildingDTO.setId(buildingId);
		BeanUtils.copyProperties(buildingDTO, existingBuilding);
		buildingDAO.update(existingBuilding);
	}

	private BuildingDO getBuildingFromCache(String buildingName) {
		BuildingDO  buildingDO = CacheDataUtils.buildingNameDOMap.get(buildingName);
		if(buildingDO == null){
			return null;
		}
		return buildingDO;
	}

	private BuildingDO convert2DO(BuildingDTO buildingDTO) {
		BuildingDO target = new BuildingDO();
		BeanUtils.copyProperties(buildingDTO, target);;
		return target;
	}

	public List<String> getExistingBuildingName() {
		List<String> exitingBuildingNameList = buildingDAO.getExistingBuildingName();
		if(CollectionUtils.isEmpty(exitingBuildingNameList)){
			return new ArrayList<String>();
		}
		return exitingBuildingNameList;
	}

}
