package org.recommend.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.common.model.BuildingAvgPriceDTO;
import org.common.model.BuildingDTO;
import org.common.utils.FileUtils;
import org.springframework.util.CollectionUtils;

/**
 * @author gushu
 * @date 2017/11/06
 */
public class ExcelGenerator {

	private final static String EXCEL_TMP_FOLDER = "./src/main/resources/excel";
	private final static String EXCEL_SHEET_NAME = "building weekly report";
	private static int rowIndex = 0; // the row index which is writing
	private static Map<BLOCK, List<String>> blockHeaderColumnMap = new HashMap<ExcelGenerator.BLOCK, List<String>>();
	private static Map<Integer, Field> columnIndexFieldClass4AvgPrice = new HashMap<Integer, Field>();
	private static Map<Integer, Field> columnIndexFieldClass4Condition = new HashMap<Integer, Field>();
	private static Set<String> excludedBuildingFields = new HashSet<String>();
	static {
		excludedBuildingFields.add("serialVersionUID");
		excludedBuildingFields.add("id");
		excludedBuildingFields.add("avgPrice");
		excludedBuildingFields.add("gmtCreated");
		excludedBuildingFields.add("gmtModified");
		initBlockHeaderColumnMap();
		initColumnIndexFieldClass4AvgPrice();
		initColumnIndexFieldClass4Condition();

	}

	private static void initBlockHeaderColumnMap() {
		List<String> avgPriceHeaderColumns = new ArrayList<String>();
		for (HEADER_COLUMNS_AVG_PRICE item : HEADER_COLUMNS_AVG_PRICE.values()) {
			avgPriceHeaderColumns.add(item.getValue());
		}
		List<String> conditionHeaderColums = new ArrayList<String>();
		for (HEADER_COLUMNS_CONDITION_MATCHED item : HEADER_COLUMNS_CONDITION_MATCHED.values()) {
			conditionHeaderColums.add(item.getValue());
		}
		blockHeaderColumnMap.put(BLOCK.avg_price, avgPriceHeaderColumns);
		blockHeaderColumnMap.put(BLOCK.condition_matched, conditionHeaderColums);
	}

	private static void initColumnIndexFieldClass4Condition() {
		Field[] fields = BuildingDTO.class.getDeclaredFields();
		int index = 0;
		for (Field field : fields) {
			if (excludedBuildingFields.contains(field.getName())) {
				continue;
			}
			columnIndexFieldClass4Condition.put(index, field);
			index++;
		}

	}

	private static void initColumnIndexFieldClass4AvgPrice() {
		Field[] fields = BuildingAvgPriceDTO.class.getDeclaredFields();
		int index = 0;
		for (Field field : fields) {
			columnIndexFieldClass4AvgPrice.put(index, field);
			index++;
		}
	}

	public static File generateExcelFile(List<BuildingAvgPriceDTO> buildingAvgPriceList,
			List<BuildingDTO> buildingList) {
		Workbook workbook = null;
		rowIndex = 0;
		try {
			workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet(EXCEL_SHEET_NAME);

			// avg price
			createBlockTitleRow(workbook, sheet, BLOCK.avg_price);
			createBlockHeaderRow(workbook, sheet, BLOCK.avg_price);
			createBlockDataRow(workbook, sheet, buildingAvgPriceList);

			// condition
			createBlockTitleRow(workbook, sheet, BLOCK.condition_matched);
			createBlockHeaderRow(workbook, sheet, BLOCK.condition_matched);
			createBlockDataRow(workbook, sheet, buildingList);

			File file = FileUtils.generateTmpExcelFile(EXCEL_TMP_FOLDER);
			System.err.println(file.getCanonicalPath());
			workbook.write(new FileOutputStream(file));
			
			for(int i =0;i < 7;i++){
				sheet.autoSizeColumn(i);
			}
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			closeWorkbook(workbook);
		}
		return null;
	}

	private static <T> void createBlockDataRow(Workbook workbook, Sheet sheet, List<T> itemList)
			throws IllegalArgumentException, IllegalAccessException {
		rowIndex++;
		CellStyle style = workbook.createCellStyle();
		style.setWrapText(false);
		style.setShrinkToFit(true);

		if (CollectionUtils.isEmpty(itemList)) {
			Row row = sheet.createRow(rowIndex);
			Cell cell = row.createCell(0);
			cell.setCellValue("no data");
			cell.setCellStyle(style);
			return;
		}

		for (T item : itemList) {
			Row row = sheet.createRow(rowIndex);
			if (item instanceof BuildingAvgPriceDTO) {
				BuildingAvgPriceDTO avgPriceItem = (BuildingAvgPriceDTO) item;
				for (Entry<Integer, Field> entryItem : columnIndexFieldClass4AvgPrice.entrySet()) {
					int cellIndex = entryItem.getKey();
					Field field = entryItem.getValue();
					field.setAccessible(true);
					Object value = field.get(avgPriceItem);
					createRowCell(style, row, cellIndex, value);
				}

			} else if (item instanceof BuildingDTO) {
				BuildingDTO buildingItem = (BuildingDTO) item;
				for (Entry<Integer, Field> enrtyItem : columnIndexFieldClass4Condition.entrySet()) {
					int cellNum = enrtyItem.getKey();
					Field field = enrtyItem.getValue();
					field.setAccessible(true);
					Object value = field.get(buildingItem);
					createRowCell(style, row, cellNum, value);
				}
			}
			rowIndex++;
		}
	}

	private static void createRowCell(CellStyle style, Row row, int cellIndex, Object value) {
		Cell cell = row.createCell(cellIndex);
		cell.setCellValue(value == null ? "" : value.toString());
		cell.setCellStyle(style);
	}

	private static void createBlockHeaderRow(Workbook workbook, Sheet sheet, BLOCK block) {
		rowIndex++;
		Row header = sheet.createRow(rowIndex);

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
		headerStyle.setFillPattern(FillPatternType.NO_FILL);
		headerStyle.setWrapText(false);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setShrinkToFit(true);

		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 12);
		font.setBold(true);
		headerStyle.setFont(font);

		List<String> headerColumnList = blockHeaderColumnMap.get(block);
		if (!CollectionUtils.isEmpty(headerColumnList)) {
			int columnIndex = 0;
			for (String item : headerColumnList) {
				Cell headerCell = header.createCell(columnIndex);
				headerCell.setCellValue(item);
				headerCell.setCellStyle(headerStyle);
				columnIndex++;
			}
		}
	}

	private static void createBlockTitleRow(Workbook workbook, Sheet sheet, BLOCK block) {
		rowIndex++;
		Row header = sheet.createRow(rowIndex);
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.YELLOW1.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		headerStyle.setWrapText(false);
		headerStyle.setShrinkToFit(true);

		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 16);
		font.setBold(true);
		headerStyle.setFont(font);

		Cell headerCell = header.createCell(0);
		headerCell.setCellValue(block.getValue());
		headerCell.setCellStyle(headerStyle);

		CellRangeAddress cellRange = new CellRangeAddress(rowIndex, rowIndex, 0, 7);
		sheet.addMergedRegion(cellRange);
	}

	private static void closeWorkbook(Workbook workbook) {
		if (workbook != null) {
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	enum HEADER_COLUMNS_AVG_PRICE {
		plate(1, "Plate"), avg_price(2, "Avg price");
		int code;
		String value;

		HEADER_COLUMNS_AVG_PRICE(int code, String value) {
			setCode(code);
			setValue(value);
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
	}

	enum HEADER_COLUMNS_CONDITION_MATCHED {
		name(1, "name"), location(2, "location"), avgPriceTxt(3, "avgPriceTxt"), plate(4, "plate"), totalPrice(5,
				"totalPrice"), source(6, "source");

		int code;
		String value;

		HEADER_COLUMNS_CONDITION_MATCHED(int code, String value) {
			setCode(code);
			setValue(value);
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
	}

	enum BLOCK {
		avg_price(1, "Average price per plate"), condition_matched(2, "Matched Condition");
		private int code;
		private String value;

		private BLOCK(int code, String value) {
			setCode(code);
			setValue(value);
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
	}

	public static void main(String[] args) {
		List<BuildingAvgPriceDTO> buildingAvgPriceList = new ArrayList<BuildingAvgPriceDTO>();
		List<BuildingDTO> buildingList = new ArrayList<BuildingDTO>();

		BuildingDTO building1 = new BuildingDTO();
		building1.setName("test1 深色的多个多个环境很简洁");
		building1.setLocation("location 1");

		BuildingDTO building2 = new BuildingDTO();
		building2.setName("test1");
		building2.setLocation("location 2 深色的多个多个环境很简洁");

		BuildingAvgPriceDTO avgPrice1 = new BuildingAvgPriceDTO();
		avgPrice1.setPlate("plate 11");
		avgPrice1.setAvgPrice(32.33D);
		BuildingAvgPriceDTO avgPrice2 = new BuildingAvgPriceDTO();
		avgPrice2.setPlate("plate 22深色的多个多个环境很简洁");
		avgPrice2.setAvgPrice(322.33D);

		buildingAvgPriceList.add(avgPrice1);
		buildingAvgPriceList.add(avgPrice2);
		buildingList.add(building1);
		buildingList.add(building2);

		generateExcelFile(buildingAvgPriceList, buildingList);
	}

}
