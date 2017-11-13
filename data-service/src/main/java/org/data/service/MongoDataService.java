package org.data.service;

import org.common.model.BuildingDTO;
import org.data.model.db.BuildingDO;

/**
 * @author gushu
 * @date 2017/11/13
 */
public interface MongoDataService {

	BuildingDO addDocument(BuildingDTO buildingDTO);
}
