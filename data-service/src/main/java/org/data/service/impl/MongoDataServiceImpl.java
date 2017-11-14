package org.data.service.impl;

import java.util.List;

import org.common.model.BuildingDTO;
import org.common.query.BuildingQuery;
import org.data.model.db.BuildingDO;
import org.data.repository.MongoBuildingRepository;
import org.data.service.MongoDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gushu
 * @date 2017/11/13
 */
@Service("mongoDataService")
public class MongoDataServiceImpl extends BaseDataServiceImpl implements MongoDataService {

	@Autowired
	private MongoBuildingRepository repository;

	@Override
	public BuildingDO addDocument(BuildingDTO buildingDTO) {
		BuildingDO buildingDO = convert2DO(buildingDTO);
		return repository.save(buildingDO);
	}

	@Override
	public List<BuildingDO> getByCondition(BuildingQuery query) {
//		return repository.findAll(example);
//		CriteriaDefinition criteriaDefinition = wher
//		Query query2 = new Query(where);
		// TODO might use map-reduce operations/ Faceted 
//		Aggregation aggregation = Aggregation.newAggregation(operations);
		return null;
	}

}
