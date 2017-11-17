package org.common.enums;

/**
 * @author gushu
 * @date 2017/11/09
 */
public enum EsIndexEnum {

	building(1,"building","doc","building desc");
	
	private int code;
	private String index; //索引
	private String type;  //索引下面的类型
	private String desc;
	
	private EsIndexEnum(int code, String index,String type, String desc) {
		setCode(code);
		setIndex(index);
		setType(type);
		setDesc(desc);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
