package org.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author gushu
 * @date 2017/10/09
 */
public class BuildingDTO implements Serializable{

	private static final long serialVersionUID = -5668809030346231774L;

	private String id;
	private String name;
	private String location;
	private String avgPriceTxt;
	private Integer avgPrice;
	private String plate;
	private String totalPrice;
	private Date gmtCreated;
	private Date gmtModified;
	private String source; // zhujiayi, lianjia
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
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
	public Integer getAvgPrice() {
		return avgPrice;
	}
	public void setAvgPrice(Integer avgPrice) {
		this.avgPrice = avgPrice;
	}
}
