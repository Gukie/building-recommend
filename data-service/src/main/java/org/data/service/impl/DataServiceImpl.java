package org.data.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.common.enums.PlateTypeEnum;
import org.common.model.BuildingAvgPriceDTO;
import org.common.model.BuildingDTO;
import org.common.query.BuildingQuery;
import org.data.dao.BuildingDAO;
import org.data.enums.DBTableEnum;
import org.data.model.biz.BuildingAvgPriceDO;
import org.data.model.db.BuildingDO;
import org.data.service.DataService;
import org.data.utils.CacheDataUtils;
import org.data.utils.GeneratorUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author gushu
 * @date 2017/10/09
 */
@Service("dataService")
public class DataServiceImpl extends BaseDataServiceImpl implements DataService {

	@Autowired
	private BuildingDAO buildingDAO;

	@Autowired
	private GeneratorUtils generator;

	@Override
	@Transactional
	public String add(BuildingDTO buildingDTO) {
		BuildingDO existingBuilding = getBuildingFromCache(buildingDTO.getName());
		if (existingBuilding != null) {
			String buildingId = existingBuilding.getId();
			update(buildingDTO, existingBuilding);
			System.out.println("updated:" + buildingId);
			return buildingId;
		}

		BuildingDO buildingDO = convert2DO(buildingDTO);
		String id = generator.generateId(DBTableEnum.building);
		buildingDO.setId(id);
		try {
			buildingDAO.insert(buildingDO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return buildingDO.getId();
	}

	private void update(BuildingDTO buildingDTO, BuildingDO existingBuilding) {
		String buildingId = existingBuilding.getId();
		buildingDTO.setId(buildingId);
		BeanUtils.copyProperties(buildingDTO, existingBuilding);
		buildingDAO.update(existingBuilding);
	}

	private BuildingDO getBuildingFromCache(String buildingName) {
		return CacheDataUtils.buildingNameDOMap.get(buildingName);
	}

	

	@Override
	public List<String> getExistingBuildingName() {
		List<String> exitingBuildingNameList = buildingDAO.getExistingBuildingName();
		if (CollectionUtils.isEmpty(exitingBuildingNameList)) {
			return new ArrayList<String>();
		}
		return exitingBuildingNameList;
	}

	@Override
	public List<BuildingDTO> getBuildingByCondition(BuildingQuery query) {
		List<BuildingDO> buildingDOList = buildingDAO.getBuildingByCondition(query);
		if (CollectionUtils.isEmpty(buildingDOList)) {
			return new ArrayList<BuildingDTO>();
		}
		return convert2DTO(buildingDOList);
	}

	@Override
	public List<BuildingAvgPriceDTO> getAvgPriceByPlateType(PlateTypeEnum plateType) {
		List<BuildingAvgPriceDTO> result = new ArrayList<BuildingAvgPriceDTO>();

		if (PlateTypeEnum.ALL == plateType) {
			Double avgPrice = buildingDAO.getAvgPrice();
			BuildingAvgPriceDTO dtoItem = generateBuildingAvgPriceDTO(plateType.getValue(), avgPrice);
			result.add(dtoItem);
		} else if (PlateTypeEnum.PER == plateType) {
			List<BuildingAvgPriceDO> avgPriceMapList = buildingDAO.getAvgPricePerArea();
			if (CollectionUtils.isEmpty(avgPriceMapList)) {
				return result;
			}
			for (BuildingAvgPriceDO item : avgPriceMapList) {
				BuildingAvgPriceDTO dtoItem = new BuildingAvgPriceDTO();
				BeanUtils.copyProperties(item, dtoItem);
				result.add(dtoItem);
			}
		}
		return result;
	}

	private BuildingAvgPriceDTO generateBuildingAvgPriceDTO(String plate, Double avgPrice) {
		BuildingAvgPriceDTO dtoItem = new BuildingAvgPriceDTO();
		dtoItem.setAvgPrice(avgPrice);
		dtoItem.setPlate(plate);
		return dtoItem;
	}

}
