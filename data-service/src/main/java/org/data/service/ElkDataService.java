package org.data.service;

import java.util.List;

import org.common.model.BuildingAvgPriceDTO;
import org.common.model.BuildingDTO;

/**
 * @author gushu
 * @date 2017/11/09
 */
public interface ElkDataService {

	/**
	 * create index into ES.
	 * 
	 * @param buildingDTO
	 * @return index id, which is buildingId
	 */
	String index(BuildingDTO buildingDTO);

	/**
	 * create average price index.
	 * @param buildingAvgPriceList
	 * @return
	 */
	String indexAvgPrice(List<BuildingAvgPriceDTO> buildingAvgPriceList);

	String indexBuildingList(List<BuildingDTO> allBuildingData);
}
