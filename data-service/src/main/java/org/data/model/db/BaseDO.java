package org.data.model.db;

import java.util.Date;

import org.springframework.data.annotation.Id;

/**
 * @author gushu
 * @date 2017/09/29
 */
public class BaseDO {

	@Id
	private String id;
	private Date gmtCreated;
	private Date gmtModified;

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

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}
