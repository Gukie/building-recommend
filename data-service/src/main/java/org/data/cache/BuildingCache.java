package org.data.cache;

import java.util.List;

import org.data.dao.BuildingDAO;
import org.data.model.BuildingDO;
import org.data.utils.CacheDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

/**
 * @author gushu
 * @date 2017/10/31
 */
//@Service("buildingCache")
public class BuildingCache {

	@Autowired
	private BuildingDAO buildingDAO;
	
	public void init(){
		List<BuildingDO> buildingDOList = buildingDAO.getAll();
		if(CollectionUtils.isEmpty(buildingDOList)){
			return;
		}
		for(BuildingDO item: buildingDOList){
			CacheDataUtils.buildingNameDOMap.put(item.getName(), item);
		}
	}
}
