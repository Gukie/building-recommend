package org.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmSS");

	public static String getCurrentDateStr() {
		return dateFormat.format(new Date());
	}
}
