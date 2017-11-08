package org.crawl.utils;

import org.apache.commons.lang.StringUtils;
import org.common.constant.SpecialValues;
import org.common.enums.BuildingSourceEnum;
import org.common.model.BuildingDTO;

/**
 * @author gushu
 * 数据修剪工具类. 主要将数据修剪为适合当前系统的数据
 * @date 2017/11/08
 */
public class DataRefineUtils {

	public static void refineBuilding(BuildingDTO buildingDTO) {
		refineBuildingPrice(buildingDTO);
		refineBuildingLocation(buildingDTO);
	}

	private static void refineBuildingLocation(BuildingDTO buildingDTO) {
		String source = buildingDTO.getSource();
		BuildingSourceEnum sourceEnum = BuildingSourceEnum.parseTxt(source);
		if(BuildingSourceEnum.LIAN_JIA == sourceEnum){
			// plate: 西湖-之江转塘象山路与鸡山路交叉口西南角 
			String plate = buildingDTO.getPlate();
			
			String newPlate = getRefinedPlate(plate);
			String newLocation = getRefinedLocation(plate);
			buildingDTO.setPlate(newPlate);
			buildingDTO.setLocation(newLocation);
		}
	}

	private static void refineBuildingPrice(BuildingDTO buildingDTO) {
		String averageTxt = buildingDTO.getAvgPriceTxt();
		if(RegexUtils.isTotalPriceTxt(averageTxt)){
			buildingDTO.setTotalPrice(RegexUtils.getTotalPriceDigitStr(averageTxt));
		}else{
			buildingDTO.setAvgPrice(RegexUtils.getDigitNum(averageTxt));
		}
	}

	/**
	 * 
	 * @param plate 西湖-之江转塘象山路与鸡山路交叉口西南角 
	 * @return 之江转塘象山路与鸡山路交叉口西南角
	 */
	private static String getRefinedLocation(String plate) {
		if(StringUtils.isEmpty(plate) ){
			return SpecialValues.EMPTY_STR;
		}
		if(!plate.contains(SpecialValues.DELIMITER_STR)){
			return plate;
		}
		int firstDelimiterIndex = plate.indexOf(SpecialValues.DELIMITER_STR);
		return plate.substring(firstDelimiterIndex+1);
	}

	/**
	 * 
	 * @param plate  西湖-之江转塘象山路与鸡山路交叉口西南角 
	 * @return 西湖区
	 */
	private static String getRefinedPlate(String plate) {
		if(StringUtils.isEmpty(plate) || !plate.contains(SpecialValues.DELIMITER_STR)){
			return SpecialValues.EMPTY_STR;
		}
		int firstDelimiterIndex = plate.indexOf(SpecialValues.DELIMITER_STR);
		String newPlate = plate.substring(0, firstDelimiterIndex);
		if(newPlate.contains(SpecialValues.CITY_SUFFIX)){
			return newPlate;
		}
		if(!newPlate.contains(SpecialValues.PLATE_SUFFIX)){
			newPlate= newPlate+SpecialValues.PLATE_SUFFIX;
		}
		return newPlate;
	}
	
//	public static void main(String[] args) {
////		String teString = "西湖区-之江转塘象山路与鸡山路交叉口西南角 ";
//		String teString = "诸暨市-之江转塘象山路与鸡山路交叉口西南角 ";
//		String plate = getRefinedPlate(teString);
//		String location = getRefinedLocation(teString);
//		System.err.println("plate:"+plate);
//		System.err.println("location:"+location);
//	}

}
