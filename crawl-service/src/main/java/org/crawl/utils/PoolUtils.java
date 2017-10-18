package org.crawl.utils;

import java.util.ArrayList;
import java.util.List;

import org.common.model.BuildingDTO;

/**
 * @author gushu
 * @date 2017/10/18
 */
public class PoolUtils {

	private static final int factor = 2048;
	
	public static final List<BuildingDTO> BUILDING_POOL = new ArrayList<BuildingDTO>(factor);
}
