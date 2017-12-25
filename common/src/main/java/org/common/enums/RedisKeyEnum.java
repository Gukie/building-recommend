package org.common.enums;

/**
 * @author gushu
 * @date 2017/12/25
 */
public enum RedisKeyEnum {

	building(1,"building","building"),
	buildingNameId(2,"buildingNameId","building name id mapping");
	
	private int code;
	private String value;
	private String desc;
	
	private RedisKeyEnum(int code, String value,String desc) {
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
