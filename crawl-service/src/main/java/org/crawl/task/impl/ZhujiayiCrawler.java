package org.crawl.task.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.common.enums.BuildingSourceEnum;
import org.common.model.BuildingDTO;
import org.crawl.bean.BuildingPageInfo;
import org.crawl.task.CrawlerTask;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ZhujiayiCrawler extends AbstractCrawlerTask implements CrawlerTask{
	
	String targetElement = ".bshadow.lpMode";
	
	String subUrl = "all_";
	
	private String targetUrl;

	public ZhujiayiCrawler(String targetUrl) {
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
	
	@Override
	public String getTargetElement() {
		return targetElement;
	}
	
//	public void run() {
//		doCrawl();
//	}

	@Override
	public Elements parseAveragePrice(Element element) {
		return element.select(".bshadow.lpMode").select(".fl.lptool").select(".lp_price");
	}

	@Override
	public Elements parseArea(Element element) {

		Elements elements = element.select(".bshadow.lpMode").select(".fl.lpInfo").select(".lpdata").select(".lp_d_line");
		if(elements.size() > 2) {
			Elements spanElements = elements.get(2).select("span");
			
			if(spanElements.size()>1) {
				return spanElements.get(1).getAllElements();
			}
		}
		return null;
	}

	@Override
	public Elements parseLocation(Element element) {
		Elements elements = element.select(".bshadow.lpMode").select(".fl.lpInfo").select(".lpdata").select(".lp_d_line");
		if(elements.size() > 1) {
			Elements spanElements = elements.get(1).select("span");
			
			if(spanElements.size()>1) {
				return spanElements.get(1).children();
			}
		}
		return null;
	}

	@Override
	public Elements parseBuildingName(Element element) {
		return element.select(".bshadow.lpMode").select(".fl.lpInfo").select(".lpName").select("a");
	}

	@Override
	BuildingPageInfo parsePageInfo(Document document) {
		Elements pageInfoEle = document.select("div.pager").select("ul").select("li.last").select("a");// <div class="page-box house-lst-page-box"></div
		Iterator<Element> iterator = pageInfoEle.iterator();
		if(iterator.hasNext()) {
			Element page = iterator.next();
			String totalPage = page.attr("data-page");
			BuildingPageInfo result = new BuildingPageInfo();
			result.setTotalPage(totalPage);
			return result;
		}
		return null;
	}

	@Override
	protected void init() {
		sourceEnum = BuildingSourceEnum.ZHU_JIA_YI;
		crawledDataList = new ArrayList<BuildingDTO>();
	}

	public List<BuildingDTO> call() throws Exception {
		doCrawl();
		return crawledDataList;
	}

	

}
