package org.data.service.impl;

import org.common.enums.DataSourceTypeEnum;
import org.common.model.BuildingDTO;
import org.data.model.db.BuildingDO;
import org.data.service.MongoDataService;
import org.springframework.stereotype.Service;

/**
 * @author gushu
 * @date 2017/11/13
 */
@Service("mongoDataService")
public class MongoDataServiceImpl extends BaseDataServiceImpl implements MongoDataService {

	
	@Override
	protected void initDataSource() {
		dataSourceType = DataSourceTypeEnum.MongoDB;
	}
	
	@Override
	public BuildingDO addDocument(BuildingDTO buildingDTO) {
		BuildingDO existingBuilding = getBuildingFromCache(buildingDTO.getName());
		if(existingBuilding!=null){
			update(buildingDTO, existingBuilding);
			return existingBuilding;
		}
		BuildingDO buildingDO = generateNewBuilding(buildingDTO);
		BuildingDO addedItem = mongoRepository.save(buildingDO);
		addBuildingIntoCache(addedItem);
		return addedItem;
	}

	@Override
	public void deleteAll() {
		mongoRepository.deleteAll();
	}
}
