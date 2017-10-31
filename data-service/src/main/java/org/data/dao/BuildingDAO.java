package org.data.dao;

import java.util.List;

import org.data.model.BuildingDO;

public interface BuildingDAO extends BaseDAO{

	int insert(BuildingDO building);
	
	BuildingDO getById(Integer id);

	List<String> getExistingBuildingName();
	
	List<BuildingDO> getAll();

	int update(BuildingDO buildingDO);
}
