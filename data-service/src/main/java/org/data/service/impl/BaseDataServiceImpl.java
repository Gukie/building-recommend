package org.data.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.common.model.BuildingDTO;
import org.data.model.db.BuildingDO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

/**
 * @author gushu
 * @date 2017/11/13
 */
public class BaseDataServiceImpl {

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
}
