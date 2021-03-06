package org.crawl.enums;

/**
 * @author gushu
 * @date 2017/10/18
 */
public enum DataSourceEnum {

	ZHU_JIA_YI(1,"zhujiayi","筑家易"),LIAN_JIA(2,"lianjia","链家");
	
	private int code;
	private String value;
	private String desc;
	private DataSourceEnum(int code, String value,String desc) {
		setCode(code);
		setValue(value);
		setDesc(desc);
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
}
