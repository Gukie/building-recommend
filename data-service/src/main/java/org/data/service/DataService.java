package org.data.service;

import java.util.List;

import org.common.model.BuildingDTO;

/**
 * @author gushu
 * @date 2017/10/09
 */
public interface DataService {

	String add(BuildingDTO buildingDTO);

	List<String> getExistingBuildingName();

}
