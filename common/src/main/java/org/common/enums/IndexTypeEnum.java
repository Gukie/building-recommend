package org.common.enums;

/**
 * @author gushu
 * @date 2017/11/09
 */
public enum IndexTypeEnum {

	building(1,"building","楼盘");
	
	private int code;
	private String value;
	private String desc;
	
	private IndexTypeEnum(int code, String value, String desc) {
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
