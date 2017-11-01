package org.common.enums;

import java.util.Objects;

/**
 * @author gushu
 * @date 2017/11/01
 */
public enum PlateTypeEnum {

	ALL(1,"ALL","全表"),PER(2,"PER","每一个板块");
	
	private int code;
	private String value;
	private String desc;
	
	private PlateTypeEnum(int code, String value, String desc){
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

	public static PlateTypeEnum parseTxt(String plateType) {
		for(PlateTypeEnum item: PlateTypeEnum.values()){
			if(Objects.equals(item.getValue(), plateType)){
				return item;
			}
		}
		return null;
	}
}
