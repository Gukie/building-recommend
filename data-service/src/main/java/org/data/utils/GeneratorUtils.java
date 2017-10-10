package org.data.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.data.enums.DBTableEnum;
import org.springframework.stereotype.Component;

/**
 * @author gushu
 * @date 2017/10/10
 */
@Component
public class GeneratorUtils {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

	public String generateId(DBTableEnum tableEnum) {
		String prefix = tableEnum.getValue();
		String value = dateFormat.format(new Date());
		return prefix + value;
	}
	
//	public static void main(String[] args) {
//		GeneratorUtils test = new GeneratorUtils();
//		System.out.println(test.generateId(DBTableEnum.building));
//	}
}
