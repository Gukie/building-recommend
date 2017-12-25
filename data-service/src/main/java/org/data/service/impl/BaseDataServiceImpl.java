package org.data.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.common.enums.DataSourceTypeEnum;
import org.common.enums.RedisKeyEnum;
import org.common.model.BuildingDTO;
import org.data.dao.BuildingDAO;
import org.data.enums.DBTableEnum;
import org.data.model.db.BuildingDO;
import org.data.utils.CacheDataUtils;
import org.data.utils.GeneratorUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;

/**
 * @author gushu
 * @date 2017/11/13
 */
public abstract class BaseDataServiceImpl {
	
	@Autowired
	protected BuildingDAO buildingDAO;
	
//	@Autowired
//	protected MongoBuildingRepository mongoRepository;
	
	@Autowired
	protected GeneratorUtils generator;
	
	protected DataSourceTypeEnum dataSourceType;
	
	@Autowired
	protected MongoOperations mongoOperations;
	
	@Autowired
	protected StringRedisTemplate redisTemplate;
	
	protected HashOperations<String, Object, Object> redisHashOperations;
	
	@PostConstruct
	protected abstract void initDataSource() ;

	protected BuildingDO convert2DO(BuildingDTO buildingDTO) {
		BuildingDO target = new BuildingDO();
		BeanUtils.copyProperties(buildingDTO, target);
		return target;
	}
	
	protected List<BuildingDTO> convert2DTO(List<BuildingDO> buildingDOList) {
		List<BuildingDTO> result = new ArrayList<BuildingDTO>();
		if (CollectionUtils.isEmpty(buildingDOList)) {
			return result;
		}
		for (BuildingDO item : buildingDOList) {
			BuildingDTO dtoItem = new BuildingDTO();
			BeanUtils.copyProperties(item, dtoItem);
			result.add(dtoItem);
		}
		return result;
	}
	
	protected BuildingDO getBuildingFromCache(String buildingName) {
		if(DataSourceTypeEnum.MySQL == dataSourceType){
			return CacheDataUtils.buildingNameDOMap.get(buildingName);
		}
		if(DataSourceTypeEnum.MongoDB == dataSourceType){
			return CacheDataUtils.mongoBuildingNameDOMap.get(buildingName);
		}
		return null;
	}
	
	protected void update(BuildingDTO buildingDTO, BuildingDO existingBuilding) {
		String buildingId = existingBuilding.getId();
		buildingDTO.setId(buildingId);
		BeanUtils.copyProperties(buildingDTO, existingBuilding);
		
		if(DataSourceTypeEnum.MySQL == dataSourceType){
			buildingDAO.update(existingBuilding);
		}
		if(DataSourceTypeEnum.MongoDB == dataSourceType){
			existingBuilding.setGmtModified(new Date());
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(buildingId));
			List<BuildingDO>  rsult = mongoOperations.find(query, BuildingDO.class);
			for(BuildingDO item: rsult){
				item.setAvgPrice(existingBuilding.getAvgPrice());
				item.setAvgPriceTxt(existingBuilding.getAvgPriceTxt());
				item.setGmtModified(new Date());
				mongoOperations.save(item);
			}
		}
	}
	
	protected BuildingDO generateNewBuilding(BuildingDTO buildingDTO) {
		BuildingDO buildingDO = convert2DO(buildingDTO);
		String id = generator.generateId(DBTableEnum.building);
		buildingDO.setId(id);
		buildingDO.setGmtCreated(new Date());
		buildingDO.setGmtModified(new Date());
		return buildingDO;
	}
	
	protected void addBuildingIntoCache(BuildingDO buildingDO) {
		String name = buildingDO.getName();
		if(DataSourceTypeEnum.MongoDB == dataSourceType ){
			CacheDataUtils.mongoBuildingNameDOMap.put(name, buildingDO);
		}
		if(DataSourceTypeEnum.MySQL == dataSourceType){
			CacheDataUtils.buildingNameDOMap.put(name, buildingDO);
		}
		if(DataSourceTypeEnum.Redis == dataSourceType){
			redisHashOperations.put(RedisKeyEnum.buildingNameId.getValue(), buildingDO.getName(), buildingDO.getId());
		}
	}

}
