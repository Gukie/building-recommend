package org.data.utils;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.data.enums.DBTableEnum;
import org.springframework.stereotype.Component;

/**
 * @author gushu
 * @date 2017/10/10
 */
@Component
public class GeneratorUtils {

	private AtomicInteger counter = new AtomicInteger(1);

	public String generateId(DBTableEnum tableEnum) {
		StringBuilder result = new StringBuilder();
		String prefix = null;
		if(tableEnum != null){
			prefix = tableEnum.getValue();
		}
		if(StringUtils.isNotBlank(prefix)){
			result.append(prefix);
		}
		result.append(System.currentTimeMillis()).append(counter.incrementAndGet());
		return result.toString();
	}
	
	public static void main(String[] args) {
		GeneratorUtils test = new GeneratorUtils();
		System.out.println(test.generateId(DBTableEnum.building));
		
		System.out.println(System.currentTimeMillis());
	}
}
