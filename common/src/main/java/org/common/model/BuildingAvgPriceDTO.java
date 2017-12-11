package org.common.model;

import org.common.constant.SpecialValues;

/**
 * @author gushu
 * @date 2017/11/01
 */
public class BuildingAvgPriceDTO {

	/**
	 * 板块. 如果是全表的均价，则该值为ALL. {@link SpecialValues#PLATE_NAME_FOR_ALL}
	 */
	private String plate;
	
	/**
	 * 均价
	 */
	private Double avgPrice;

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public Double getAvgPrice() {
		return Math.ceil(avgPrice);
	}

	public void setAvgPrice(Double avgPrice) {
		this.avgPrice = avgPrice;
	}
	
	
}
