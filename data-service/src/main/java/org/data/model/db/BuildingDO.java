package org.data.model.db;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="building")
public class BuildingDO extends BaseDO {

	private String name;
	private String plate;
	private String location;
	private String avgPriceTxt;
	private Integer avgPrice;
	private String totalPrice;
	private String source; // zhujiayi, lianjia
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(Integer avgPrice) {
		this.avgPrice = avgPrice;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public String getAvgPriceTxt() {
		return avgPriceTxt;
	}

	public void setAvgPriceTxt(String avgPriceTxt) {
		this.avgPriceTxt = avgPriceTxt;
	}

	@Override
	public String toString() {
		StringBuilder result =new StringBuilder();
		result.append("{");
		result.append(getId()).append(":[");
		result.append(getName()).append("]-[");
		result.append(getPlate()).append("]-[");
		result.append(getLocation()).append("]-[");
		result.append(getAvgPriceTxt()).append("]-[");
		result.append(getAvgPrice()).append("]-[");
		result.append(getTotalPrice()).append("]-[");
		result.append(getSource()).append("]");
		result.append("}");
		return result.toString();
	}
}
