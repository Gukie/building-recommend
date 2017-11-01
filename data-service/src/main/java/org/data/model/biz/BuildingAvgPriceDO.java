package org.data.model.biz;

/**
 * @author gushu
 * @date 2017/11/01
 */
public class BuildingAvgPriceDO {

//	location, AVG(avg_price) as avg_price_per_area
	private String location; //  like '滨江'
	private Double avgPricePerArea;
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Double getAvgPricePerArea() {
		return avgPricePerArea;
	}
	public void setAvgPricePerArea(Double avgPricePerArea) {
		this.avgPricePerArea = avgPricePerArea;
	}
	
}
