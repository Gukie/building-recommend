package org.data.service;

import java.util.List;

import org.common.enums.PlateTypeEnum;
import org.common.model.BuildingAvgPriceDTO;
import org.common.model.BuildingDTO;
import org.data.model.db.BuildingDO;

/**
 * @author gushu
 * @date 2017/11/13
 */
public interface MongoDataService {

	BuildingDO addDocument(BuildingDTO buildingDTO);
	
	void deleteAll();
	
	List<BuildingAvgPriceDTO> getAvgPriceByPlateType(PlateTypeEnum plateType);

	List<BuildingDTO> getAllData();
}
