package org.data.repository;

import org.data.model.db.BuildingDO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author gushu
 * @date 2017/11/13
 */
public interface MongoBuildingRepository extends MongoRepository<BuildingDO, String> {

}
