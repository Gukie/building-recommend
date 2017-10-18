package org.crawl.utils;

import java.util.HashMap;
import java.util.Map;

import org.crawl.task.impl.LianjiaCrawler;
import org.crawl.task.impl.ZhujiayiCrawler;

/**
 * @author gushu
 * @date 2017/10/18
 */
public class CrawlerUtils {
	public static Map<Class<? extends Object>, String> crawlClassTargetUrlMap = new HashMap<Class<? extends Object>, String>();
	static {
		crawlClassTargetUrlMap.put(LianjiaCrawler.class, "http://hz.fang.lianjia.com/loupan/");
		crawlClassTargetUrlMap.put(ZhujiayiCrawler.class, "http://hz.zje.com/loupan/");
	}
}
