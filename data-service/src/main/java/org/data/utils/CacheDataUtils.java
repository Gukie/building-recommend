package org.data.utils;

import java.util.HashMap;
import java.util.Map;

import org.data.model.db.BuildingDO;

/**
 * @author gushu
 * @date 2017/10/31
 */
public class CacheDataUtils {

	public static Map<String, BuildingDO> buildingNameDOMap = new HashMap<String, BuildingDO>();
	
	public static Map<String, BuildingDO> mongoBuildingNameDOMap = new HashMap<String, BuildingDO>();
}
