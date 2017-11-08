package org.data.model.biz;

/**
 * @author gushu
 * @date 2017/11/01
 */
public class BuildingAvgPriceDO {

//	location, AVG(avg_price) as avg_price_per_area
	private String plate; //  like '滨江'
	private Double avgPrice;
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public Double getAvgPrice() {
		return avgPrice;
	}
	public void setAvgPrice(Double avgPrice) {
		this.avgPrice = avgPrice;
	}
}
