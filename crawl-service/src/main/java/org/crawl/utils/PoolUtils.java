package org.crawl.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.common.model.BuildingDTO;

/**
 * @author gushu
 * @date 2017/10/18
 */
public class PoolUtils {

//	private static final int factor = 2048;
	
	public static final BlockingQueue<BuildingDTO> BUILDING_POOL = new LinkedBlockingQueue<BuildingDTO>();
	
	public static final AtomicInteger ADDED_COUNTER = new AtomicInteger(0);
}
