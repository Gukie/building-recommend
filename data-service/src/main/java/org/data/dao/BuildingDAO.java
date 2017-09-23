package org.data.dao;

import org.data.model.BuildingDO;

public interface BuildingDAO {

	Integer  insert(BuildingDO building);
	
	BuildingDO getById(Integer id);
}
