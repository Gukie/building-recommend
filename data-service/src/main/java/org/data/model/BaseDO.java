package org.data.model;

import java.util.Date;

/**
 * @author gushu
 * @date 2017/09/29
 */
public class BaseDO {

	private String id;
	private Date gmtCreated;
	private String gmtModified;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public String getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(String gmtModified) {
		this.gmtModified = gmtModified;
	}

}
