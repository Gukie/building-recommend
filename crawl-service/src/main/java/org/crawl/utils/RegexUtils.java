package org.crawl.utils;

import org.apache.commons.lang.StringUtils;
import org.common.constant.SpecialValues;

/**
 * @author gushu
 * @date 2017/11/01
 */
public class RegexUtils {

	private static String getDigit(String digitTxt) {
		if (StringUtils.isEmpty(digitTxt)) {
			return SpecialValues.EMPTY_STR;
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

	public static int getDigitNum(String digitTxt) {
		String tmpDigit = getDigit(digitTxt);
		if (StringUtils.isEmpty(tmpDigit)) {
			return 0;
		}
		return Integer.parseInt(tmpDigit);
	}

	public static boolean isTotalPriceTxt(String priceTxt) {
		if (StringUtils.isEmpty(priceTxt)) {
			return false;
		}
		return priceTxt.contains(SpecialValues.TOTAL_PRICE_KEY_WORD);
	}

	public static String getTotalPriceDigitStr(String priceTxt) {
		if (StringUtils.isEmpty(priceTxt)) {
			return SpecialValues.EMPTY_STR;
		}
		if (priceTxt.contains(SpecialValues.RANGE_PRICE_KEY_WORD)) {
			return getRangeDigitStr(priceTxt);
		}
		return getDigit(priceTxt);
	}

	private static String getRangeDigitStr(String priceTxt) {
		StringBuilder result = new StringBuilder();
		String[] priceTxtArr = priceTxt.split(SpecialValues.RANGE_PRICE_KEY_WORD);
		if (priceTxtArr != null && priceTxtArr.length > 1) {
			result.append(priceTxtArr[0]).append(SpecialValues.RANGE_PRICE_KEY_WORD);
			result.append(getDigit(priceTxtArr[1]));
		}
		return result.toString();
	}
}
