package org.crawl.task.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.common.enums.BuildingSourceEnum;
import org.common.model.BuildingDTO;
import org.crawl.bean.BuildingPageInfo;
import org.crawl.task.CrawlerTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;

/**
 * refer: 
 * <li> https://www.ibm.com/developerworks/cn/java/j-lo-jsouphtml/index.html
 * 
 * @author lokia
 *
 */
public class LianjiaCrawler extends AbstractCrawlerTask implements CrawlerTask {
	
	String targetElement = ".info-panel";
	String subUrl = "/loupan/pg";
	
	private String targetUrl;
	
	public LianjiaCrawler(String targetUrl) {
		init();
		setTargetUrl(targetUrl);
	}
	
	@Override
	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	
	public String getSubUrl() {
		return subUrl;
	}

	public void setSubUrl(String subUrl) {
		this.subUrl = subUrl;
	}
	
	
	@Override
	public void doCrawl() {
		try {
			Document document = Jsoup.connect(targetUrl).get();
			parseCurrentPageInfo(document);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public BuildingPageInfo parsePageInfo(Document document) {
		Elements pageInfoEle = document.select("div.page-box.house-lst-page-box");// <div class="page-box house-lst-page-box"></div
		Iterator<Element> iterator = pageInfoEle.iterator();
		if(iterator.hasNext()) {
			Element page = iterator.next();
			String pageTotalInfo = page.attr("page-data");
			return JSON.parseObject(pageTotalInfo, BuildingPageInfo.class);
		}
		return null;
	}


	@Override
	public Elements parseAveragePrice(Element element) {
		return element.select(".col-2 .price .average .num");
	}


	@Override
	public Elements parseArea(Element element) {
		return element.select(".col-1 .area");
	}


	@Override
	public Elements parseLocation(Element element) {
		return  element.select(".col-1 .where .region");
	}


	@Override
	public Elements parseBuildingName(Element element) {
		return element.select("h2");
	}


//	public void run() {
//		doCrawl();
//	}
	

	@Override
	public String getTargetElement() {
		return targetElement;
	}

	public List<BuildingDTO> call() throws Exception {
		doCrawl();
		return crawledDataList;
	}

	@Override
	protected void init() {
		sourceEnum = BuildingSourceEnum.LIAN_JIA;
		crawledDataList = new ArrayList<BuildingDTO>();
	}

}
