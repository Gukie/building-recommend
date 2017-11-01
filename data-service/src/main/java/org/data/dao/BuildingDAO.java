package org.data.dao;

import java.util.List;

import org.common.query.BuildingQuery;
import org.data.model.biz.BuildingAvgPriceDO;
import org.data.model.db.BuildingDO;

public interface BuildingDAO extends BaseDAO{

	int insert(BuildingDO building);
	
	BuildingDO getById(String id);

	List<String> getExistingBuildingName();
	
	List<BuildingDO> getAll();

	int update(BuildingDO buildingDO);
	
	/**
	 * Gets the avg price for all building in HZ
	 * 
	 * @return
	 */
	double getAvgPrice();
	
	/**
	 * Gets the avg price for per area, like '滨江'
	 * @return
	 */
	List<BuildingAvgPriceDO> getAvgPricePerArea();
	
	List<BuildingDO> getBuildingByCondition(BuildingQuery query);
}
