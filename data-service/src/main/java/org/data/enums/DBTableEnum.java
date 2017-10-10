package org.data.enums;

/**
 * @author gushu
 * @date 2017/10/10
 */
public enum DBTableEnum {

	building(1,"BD","building table");
	
	private int code;
	private String value;
	private String desc;
	
	private DBTableEnum(int code, String value, String desc) {
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
