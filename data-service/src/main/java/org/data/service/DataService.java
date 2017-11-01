package org.data.service;

import java.util.List;

import org.common.enums.PlateTypeEnum;
import org.common.model.BuildingAvgPriceDTO;
import org.common.model.BuildingDTO;
import org.common.query.BuildingQuery;

/**
 * @author gushu
 * @date 2017/10/09
 */
public interface DataService {

	String add(BuildingDTO buildingDTO);

	List<String> getExistingBuildingName();

	List<BuildingAvgPriceDTO> getAvgPriceByPlateType(PlateTypeEnum plateType);
	
	List<BuildingDTO> getBuildingByCondition(BuildingQuery query); 
}
