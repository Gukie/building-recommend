package org.crawl.utils;

import org.apache.commons.lang.StringUtils;

/**
 * @author gushu
 * @date 2017/11/01
 */
public class RegexUtils {

	private static String getDigit(String digitTxt) {
		if (StringUtils.isEmpty(digitTxt)) {
			return "";
		}
		if(digitTxt.contains("套") || digitTxt.contains("-")){
			return "";
		}
		int sz = digitTxt.length();
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < sz; i++) {
			if (!Character.isDigit(digitTxt.charAt(i))) {
				break;
			}
			result.append(digitTxt.charAt(i));
		}
		return result.toString();
	}
	
	public static int getDigitNum(String digitTxt){
		String tmpDigit = getDigit(digitTxt);
		if(StringUtils.isEmpty(tmpDigit) ){
			return 0;
		}
		return Integer.parseInt(tmpDigit);
	}
}
