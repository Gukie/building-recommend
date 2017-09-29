package org.data.dao;

import org.data.model.BuildingDO;

public interface BuildingDAO extends BaseDAO{

	int insert(BuildingDO building);
	
	BuildingDO getById(Integer id);
}
