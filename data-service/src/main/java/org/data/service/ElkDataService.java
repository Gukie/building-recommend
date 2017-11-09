package org.data.service;

import org.common.model.BuildingDTO;
import org.elasticsearch.action.index.IndexResponse;

/**
 * @author gushu
 * @date 2017/11/09
 */
public interface ElkDataService {

	IndexResponse index(BuildingDTO buildingDTO);
}
