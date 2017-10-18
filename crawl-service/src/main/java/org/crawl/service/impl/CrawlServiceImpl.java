package org.crawl.service.impl;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.crawl.service.CrawlService;
import org.crawl.task.CrawlerTask;
import org.crawl.task.impl.LianjiaCrawler;
import org.crawl.task.impl.ZhujiayiCrawler;
import org.crawl.utils.CrawlerUtils;
import org.springframework.stereotype.Service;

/**
 * @author gushu
 * @date 2017/10/18
 */
@Service("crawlService")
public class CrawlServiceImpl implements CrawlService {

	public void crawl() {
		crawlLianjia();
		crawlZhujiayi();
		// wait util all task finished
	}
	
	private void crawlLianjia() {
		String targetUrl = CrawlerUtils.crawlClassTargetUrlMap.get(LianjiaCrawler.class);
		CrawlerTask lianjiaCrawler = new LianjiaCrawler(targetUrl);
		int totalPage = lianjiaCrawler.crawlTotalPage();
		System.err.println("total pages:"+totalPage);
		if(totalPage>0) {
			String subUrl = lianjiaCrawler.getSubUrl();
			for(int i = 0 ;i<totalPage;i++) {
				LianjiaCrawler crawler = new LianjiaCrawler(targetUrl+subUrl+i);
				Thread thread = new Thread(crawler);
				thread.start();
			}
		}
	}

	private void crawlZhujiayi() {
		
		int threadNum = Runtime.getRuntime().availableProcessors()+1;
		Executor executor = Executors.newFixedThreadPool(threadNum);
			
//		ExecutorCompletionService<CrawlerTask> executorCompletionService = new ExecutorCompletionService<CrawlerTask>(executor);
		
		String targetUrl = CrawlerUtils.crawlClassTargetUrlMap.get(ZhujiayiCrawler.class);
		ZhujiayiCrawler crawler  = new ZhujiayiCrawler(targetUrl);
		int totalPage = crawler.crawlTotalPage();
		
		if(totalPage>0) {
			String subUrl = crawler.getSubUrl();
			for(int i = 0 ;i<=totalPage;i++) {
				crawler = new ZhujiayiCrawler(targetUrl+subUrl+i);
				executor.execute(crawler);
			}
				
		}
		
		System.out.println("end...");
		
	}
}
