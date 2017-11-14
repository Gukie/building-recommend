package org.data.service;

import java.util.List;

import org.common.model.BuildingDTO;
import org.common.query.BuildingQuery;
import org.data.model.db.BuildingDO;

/**
 * @author gushu
 * @date 2017/11/13
 */
public interface MongoDataService {

	BuildingDO addDocument(BuildingDTO buildingDTO);
	
	List<BuildingDO> getByCondition(BuildingQuery query);
}
