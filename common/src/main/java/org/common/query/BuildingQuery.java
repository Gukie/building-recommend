package org.common.query;

/**
 * @author gushu
 * @date 2017/11/01
 */
public class BuildingQuery {

	/**
	 * 均价范围的开始值
	 */
	private Integer avgPriceRangeStart;
	
	/**
	 * 均价范围的结束值
	 */
	private Integer avgPriceRangeEnd;

	public Integer getAvgPriceRangeStart() {
		return avgPriceRangeStart;
	}

	public void setAvgPriceRangeStart(Integer avgPriceRangeStart) {
		this.avgPriceRangeStart = avgPriceRangeStart;
	}

	public Integer getAvgPriceRangeEnd() {
		return avgPriceRangeEnd;
	}

	public void setAvgPriceRangeEnd(Integer avgPriceRangeEnd) {
		this.avgPriceRangeEnd = avgPriceRangeEnd;
	}
}
