package org.data.service;

import org.common.model.BuildingDTO;

/**
 * @author gushu
 * @date 2017/12/25
 */
public interface RedisDataSource {

	void createOrUpdate(BuildingDTO buildingDTO);
	
	BuildingDTO getById(String buildingId);
	
	/**
	 * 获取ID
	 * 
	 * @param buildingName
	 * @return buildingId
	 */
	String getByName(String buildingName);
}
