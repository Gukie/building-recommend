package org.data.service.impl;

import java.util.Date;

import com.alibaba.fastjson.JSON;

import org.apache.commons.lang.StringUtils;
import org.common.enums.DataSourceTypeEnum;
import org.common.enums.RedisKeyEnum;
import org.common.model.BuildingDTO;
import org.data.enums.DBTableEnum;
import org.data.service.RedisDataSource;
import org.springframework.stereotype.Service;

/**
 * @author gushu
 * @date 2017/12/25
 */
@Service("redisDataSource")
public class RedisDataSourceImpl extends BaseDataServiceImpl implements RedisDataSource{

	@Override
	protected void initDataSource() {
		dataSourceType = DataSourceTypeEnum.Redis;
		redisHashOperations = redisTemplate.opsForHash();
	}
	
	@Override
	public void createOrUpdate(BuildingDTO buildingDTO) {
		String buildingId = getByName(buildingDTO.getName());
		buildingDTO.setGmtCreated(new Date());
		if(StringUtils.isNotBlank(buildingId)){
			updateField(buildingDTO, buildingId);
		}
		
		if(StringUtils.isBlank(buildingDTO.getId())){
			buildingId = generator.generateId(DBTableEnum.building);
			buildingDTO.setId(buildingId);
		}
		redisHashOperations.put(RedisKeyEnum.building.getValue(), buildingDTO.getId(), JSON.toJSONString(buildingDTO));
		redisHashOperations.put(RedisKeyEnum.buildingNameId.getValue(), buildingDTO.getName(), buildingDTO.getId());
	}
	
	private void updateField(BuildingDTO newBuilding, String buildingId) {
		BuildingDTO olDto = getById(buildingId);
		if(olDto!=null){
			newBuilding.setId(buildingId);
			newBuilding.setGmtCreated(olDto.getGmtCreated());
			newBuilding.setGmtModified(new Date());
		}
	}

	@Override
	public BuildingDTO getById(String buildingId) {
		String buildingJsonStr = (String) redisHashOperations.get(RedisKeyEnum.building.getValue(), buildingId);
		if(StringUtils.isNotBlank(buildingJsonStr)){
			return JSON.parseObject(buildingJsonStr, BuildingDTO.class);
		}
		return null;
	}

	@Override
	public String getByName(String buildingName) {
		String buildingId = (String) redisHashOperations.get(RedisKeyEnum.buildingNameId.getValue(), buildingName);
		return buildingId;
	}

}
