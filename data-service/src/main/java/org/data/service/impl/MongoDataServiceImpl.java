package org.data.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.common.enums.DataSourceTypeEnum;
import org.common.enums.MongoCollectionEnum;
import org.common.enums.MongoFieldEnum;
import org.common.enums.PlateTypeEnum;
import org.common.model.BuildingAvgPriceDTO;
import org.common.model.BuildingDTO;
import org.data.model.db.BuildingDO;
import org.data.service.MongoDataService;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * @author gushu
 * @date 2017/11/13
 */
@Service("mongoDataService")
public class MongoDataServiceImpl extends BaseDataServiceImpl implements MongoDataService {

	
	@Override
	protected void initDataSource() {
		dataSourceType = DataSourceTypeEnum.MongoDB;
	}
	
	@Override
	public BuildingDO addDocument(BuildingDTO buildingDTO) {
		BuildingDO existingBuilding = getBuildingFromCache(buildingDTO.getName());
		if(existingBuilding!=null){
			update(buildingDTO, existingBuilding);
			return existingBuilding;
		}
		BuildingDO buildingDO = generateNewBuilding(buildingDTO);
//		BuildingDO addedItem = mongoRepository.save(buildingDO);
		mongoOperations.save(buildingDO);
		addBuildingIntoCache(buildingDO);
		return buildingDO;
	}

	@Override
	public void deleteAll() {
		mongoOperations.remove(new Query(), BuildingDO.class);
	}

	@Override
	public List<BuildingAvgPriceDTO> getAvgPriceByPlateType(PlateTypeEnum plateType) {
		
		// first, filter the avgPrice less than 0
		Criteria avgPriceGt0 = new Criteria(MongoFieldEnum.avgPrice.name());
		avgPriceGt0.gt(0);
		MatchOperation  matchAvgPriceGt0Operation = TypedAggregation.match(avgPriceGt0);
		
		GroupOperation groupOperation = TypedAggregation.group(MongoFieldEnum.plate.name()).avg(MongoFieldEnum.avgPrice.name()).as(MongoFieldEnum.avgPrice.name());
		ProjectionOperation projectionOperation = TypedAggregation.project(MongoFieldEnum.avgPrice.name()).and(MongoFieldEnum._id.name()).as(MongoFieldEnum.plate.name());
		
		
		Order order = new Order(Direction.DESC, MongoFieldEnum.avgPrice.name());
		Sort sort = new Sort(order);
		SortOperation sortOperation = TypedAggregation.sort(sort);
		
		AggregationOperation[] aggregationOperationArr = {matchAvgPriceGt0Operation,groupOperation,projectionOperation,sortOperation};
		TypedAggregation<BuildingDTO> aggregation = new TypedAggregation<>(BuildingDTO.class,aggregationOperationArr);
		AggregationResults<BuildingAvgPriceDTO> aggregationResults = mongoOperations.aggregate(aggregation,MongoCollectionEnum.building.name(), BuildingAvgPriceDTO.class);
		return aggregationResults.getMappedResults();
	}

	@Override
	public List<BuildingDTO> getAllData() {
		List<BuildingDTO> result = new ArrayList<>();
		
		
		
		return result;
	}
}
