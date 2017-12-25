package org.data.cache;

import java.util.List;

import org.data.dao.BuildingDAO;
import org.data.model.db.BuildingDO;
import org.data.repository.MongoBuildingRepository;
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
	
	@Autowired
	private MongoBuildingRepository mongoRep;
	
//	@Autowired
//	private StringRedisTemplate redisTemplate;
//	
//	private HashOperations<String, Object, Object> redisHashOperations;
	
	public void init(){
		initMysqlCache();
		initMongoCache();
//		initRedisCache();
	}

//	private void initRedisCache() {
//		redisHashOperations = redisTemplate.opsForHash();
//		Map<Object, Object> existingDatas = redisHashOperations.entries(RedisKeyEnum.building.getValue());
//		if(CollectionUtils.isEmpty(existingDatas)){
//			return;
//		}
//		
//		
//	}

	private void initMongoCache() {
		List<BuildingDO> buildingDOList = mongoRep.findAll();
		if(CollectionUtils.isEmpty(buildingDOList)){
			return;
		}
		for(BuildingDO item: buildingDOList){
			CacheDataUtils.mongoBuildingNameDOMap.put(item.getName(), item);
		}
	}

	private void initMysqlCache() {
		List<BuildingDO> buildingDOList = buildingDAO.getAll();
		if(CollectionUtils.isEmpty(buildingDOList)){
			return;
		}
		for(BuildingDO item: buildingDOList){
			CacheDataUtils.buildingNameDOMap.put(item.getName(), item);
		}
	}
}
