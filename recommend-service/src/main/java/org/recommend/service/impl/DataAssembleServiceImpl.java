package org.recommend.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import org.common.enums.PlateTypeEnum;
import org.common.model.BuildingAvgPriceDTO;
import org.common.model.BuildingDTO;
import org.common.query.BuildingQuery;
import org.recommend.client.DataServiceClient;
import org.recommend.excel.ExcelGenerator;
import org.recommend.service.DataAssembleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

/**
 * @author gushu
 * @date 2017/11/02
 */
//@Service("dataAssembleService")
public class DataAssembleServiceImpl implements DataAssembleService {

	@Autowired
	private DataServiceClient dataClient;
	
	private Integer priceRangeFrom = 0;
	private Integer priceRangeTo = Integer.MAX_VALUE;
	
	public Integer getPriceRangeFrom() {
		return priceRangeFrom;
	}

	public void setPriceRangeFrom(Integer priceRangeFrom) {
		this.priceRangeFrom = priceRangeFrom;
	}

	public Integer getPriceRangeTo() {
		return priceRangeTo;
	}

	public void setPriceRangeTo(Integer priceRangeTo) {
		this.priceRangeTo = priceRangeTo;
	}

	@Override
	public String assemblePlainTxt() {
		List<BuildingAvgPriceDTO> avgPriceDTOList4All = dataClient.getAvgPriceByPlateType(PlateTypeEnum.ALL.getValue());
		List<BuildingAvgPriceDTO> avgPriceDTOList4Per = dataClient.getAvgPriceByPlateType(PlateTypeEnum.PER.getValue());
		BuildingQuery buildingQuery = generateBuildingQuery();
		List<BuildingDTO>  buildignDTOList = dataClient.getBuildingByCondition(JSON.toJSONString(buildingQuery));
	
		return generatePlainTxt(avgPriceDTOList4All,avgPriceDTOList4Per,buildignDTOList);
	}

	private BuildingQuery generateBuildingQuery() {
		BuildingQuery buildingQuery = new BuildingQuery();
		buildingQuery.setAvgPriceRangeStart(priceRangeFrom);
		buildingQuery.setAvgPriceRangeEnd(priceRangeTo);
		return buildingQuery;
	}
	
	private String generatePlainTxt(List<BuildingAvgPriceDTO> avgPriceDTOList4All,
			List<BuildingAvgPriceDTO> avgPriceDTOList4Per, List<BuildingDTO> buildignDTOList) {
		StringBuilder result = new StringBuilder();
		String avgPrice4AllJsonTxt = JSONArray.toJSONString(avgPriceDTOList4All);
		String avgPrice4PerJsonTxt = JSONArray.toJSONString(avgPriceDTOList4Per);
		String matchedConditionJsonTxt = JSONArray.toJSONString(buildignDTOList);
		result.append("total avg:[").append(avgPrice4AllJsonTxt).append("]");
		result.append("per avg:[").append(avgPrice4PerJsonTxt).append("]");
		result.append("matched building:[").append(matchedConditionJsonTxt).append("]");
		return result.toString();
	}

	@Override
	public File assembleExcel() {
		BuildingQuery buildingQuery = generateBuildingQuery();
		
		List<BuildingAvgPriceDTO> avgPriceDTOList = getAvgPriceDTOList();
		List<BuildingDTO> conditionBuildingList = dataClient.getBuildingByCondition(JSON.toJSONString(buildingQuery));
		return ExcelGenerator.generateExcelFile(buildingQuery,avgPriceDTOList,conditionBuildingList);
	}


	private List<BuildingAvgPriceDTO> getAvgPriceDTOList() {
		List<BuildingAvgPriceDTO> result = new ArrayList<BuildingAvgPriceDTO>();
		List<BuildingAvgPriceDTO> avgPriceDTOList4All = dataClient.getAvgPriceByPlateType(PlateTypeEnum.ALL.getValue());
		List<BuildingAvgPriceDTO> avgPriceDTOList4Per = dataClient.getAvgPriceByPlateType(PlateTypeEnum.PER.getValue());
		
		if(!CollectionUtils.isEmpty(avgPriceDTOList4All)){
			result.addAll(avgPriceDTOList4All);
		}
		if(!CollectionUtils.isEmpty(avgPriceDTOList4Per)){
			result.addAll(avgPriceDTOList4Per);
		}
		return result;
	}

}
