package org.data.service;

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
}
