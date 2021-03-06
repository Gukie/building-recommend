package org.crawl.task.impl;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;
import org.common.enums.BuildingSourceEnum;
import org.common.model.BuildingDTO;
import org.crawl.bean.BuildingPageInfo;
import org.crawl.utils.DataRefineUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author gushu
 * @date 2017/10/18
 */
public abstract class AbstractCrawlerTask  implements Callable<List<BuildingDTO>>{

	protected List<BuildingDTO> crawledDataList;
	
	protected BuildingSourceEnum sourceEnum;
	
	protected abstract void init();
	public abstract String getTargetElement();

	public abstract String getTargetUrl();

	public void doCrawl() {
		String targetUrl = getTargetUrl();
		try {
			Document document = Jsoup.connect(targetUrl).get();
			parseCurrentPageInfo(document);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void parseCurrentPageInfo(Document document) {
		String targetElement = getTargetElement();
		Elements elementList = document.select(targetElement);
		Iterator<Element> iterator = elementList.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			parseElement(element);
		}
	}

	public void parseElement(Element element) {
		String buildingName = parseBuildingNameStr(element);
		String plate = parsePlateStr(element);
		String location = parseLocationStr(element);
		String averageTxt = parseAveragePriceStr(element);
		
		BuildingDTO buildingDTO = new BuildingDTO();
		buildingDTO.setName(buildingName);
		buildingDTO.setLocation(location);
		buildingDTO.setPlate(plate);
		buildingDTO.setAvgPriceTxt(averageTxt);
		buildingDTO.setSource(sourceEnum.getValue());
		DataRefineUtils.refineBuilding(buildingDTO);
		crawledDataList.add(buildingDTO);
//		PoolUtils.BUILDING_POOL.add(buildingDTO);
//		System.out.println(buildingName + "," + location + "," + area + "," + average);
	}

	
	private String parseAveragePriceStr(Element element) {
		Elements elements = parseAveragePrice(element);
		String tmpResult = getElementsText(elements);
		if(tmpResult.contains("：")) {
			return trimTxt(tmpResult.split("：")[1]);
		}
		
		if(tmpResult.contains(":")) {
			return trimTxt(tmpResult.split(":")[1]);
		}
		return tmpResult;
	}

	private String parseLocationStr(Element element) {
		Elements elements = parseArea(element);
		return getElementsText(elements);
	}

	private String parsePlateStr(Element element) {
		Elements elements = parseLocation(element);
		return getElementsText(elements);
	}

	private String parseBuildingNameStr(Element element) {
		Elements elements = parseBuildingName(element);
		return getElementsText(elements);
	}

	public abstract Elements parseAveragePrice(Element element);

	public abstract Elements parseArea(Element element);

	public abstract Elements parseLocation(Element element);

	public abstract Elements parseBuildingName(Element element);

	private String getElementsText(Elements elements) {
		Iterator<Element> iterator = elements.iterator();
		if (iterator.hasNext()) {
			Element header = iterator.next();
			return trimTxt(header.text());
		}
		return "";
	}
	
	private String trimTxt(String txt){
		if(StringUtils.isEmpty(txt)){
			return txt;
		}
		return txt.trim();
	}

	public int crawlTotalPage() {

		String targetUrl = getTargetUrl();
		BuildingPageInfo pageInfo = null;
		try {
			Document document = Jsoup.connect(targetUrl).get();
			pageInfo = parsePageInfo(document);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return pageInfo == null ? 0 : Integer.parseInt(pageInfo.getTotalPage());
	} 

	abstract BuildingPageInfo parsePageInfo(Document document);


}
