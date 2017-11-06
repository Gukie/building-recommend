package org.common.utils;

import java.io.File;
import java.io.IOException;

import org.common.constant.SpecialValues;

public class FileUtils {
	private final static String excel_suffix = ".xlsx";
	public static File generateTmpExcelFile(String dir) {
		File tmpFile = null;
		String prefix = DateUtils.getCurrentDateStr();
		try {
			if(dir == null || SpecialValues.EMPTY_STR.equals(dir.trim())) {
				tmpFile = File.createTempFile(prefix, excel_suffix);
			}
			File dirFile = new File(dir);
			if(!dirFile.exists()) {
				createDir(dir);
			}
			tmpFile = File.createTempFile(prefix, excel_suffix,dirFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tmpFile;
	}
	
	private static void createDir(String dir) {
		File dirFile = new File(dir);
		if(dirFile.exists()) {
			return;
		}
		dirFile.mkdirs();
	}

	public static boolean deleteTmpFile(String filePath) {
		File file = new File(filePath);
		if(file.exists()) {
			return file.delete();
		}
		return true;
	}
	
//	public static void main(String[] args) {
//		File tmpFile = generateTmpFile();
//		String tmpFilePath = null;
//		try {
//			tmpFilePath = tmpFile.getCanonicalPath();
//			System.err.println(tmpFilePath);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		boolean result = deleteTmpFile(tmpFilePath);
//		System.err.println(result);
//		
//	}
}
