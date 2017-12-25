package org.crawl.service.impl;

import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.alibaba.fastjson.JSON;

import org.common.model.BuildingDTO;
import org.crawl.client.DataServiceClient;
import org.crawl.service.CrawlService;
import org.crawl.task.CrawlerTask;
import org.crawl.task.impl.LianjiaCrawler;
import org.crawl.task.impl.ZhujiayiCrawler;
import org.crawl.utils.CrawlerUtils;
import org.crawl.utils.PoolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author gushu
 * @date 2017/10/18
 */
@Service("crawlService")
public class CrawlServiceImpl implements CrawlService {
	
	private CompletionService<List<BuildingDTO>> completionService;
	
	@Autowired
    private DataServiceClient dataServiceClient;

	@Override
	public void crawl() {
		initCompletionService();
		long start = System.currentTimeMillis();
		crawlLianjia();
		crawlZhujiayi();
		long end = System.currentTimeMillis();
		System.out.println("all tasked finished. time consumed:"+(end-start));
	}

	private void initCompletionService() {
		int threadNum = Runtime.getRuntime().availableProcessors()+1;
		ExecutorService executorService =  Executors.newFixedThreadPool(threadNum);
		completionService = new ExecutorCompletionService<List<BuildingDTO>>(executorService);
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
				completionService.submit(crawler);
			}
		}
		store(totalPage);
		System.out.println(" crawlLianjia finished... ");
	}

	private void store(int taskNum) {
		if(taskNum <1) {
			return;
		}
		for(int i = 0;i<taskNum;i++) {
			try {
				Future<List<BuildingDTO>> result = completionService.take();
				List<BuildingDTO> buildingDTOList = result.get();
				if(CollectionUtils.isEmpty(buildingDTOList)) {
					continue;
				}
				for(BuildingDTO item: buildingDTOList) {
					String buildingJsonStr = JSON.toJSONString(item);
//					dataServiceClient.addBuilding(buildingJsonStr);
//					dataServiceClient.createIndex(buildingJsonStr);
//					dataServiceClient.addDocument(buildingJsonStr);
					dataServiceClient.addDoc2Redis(buildingJsonStr);
					System.out.println("added:"+PoolUtils.ADDED_COUNTER.incrementAndGet());
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	private void crawlZhujiayi() {
		
//		int threadNum = Runtime.getRuntime().availableProcessors()+1;
//		Executor executor = Executors.newFixedThreadPool(threadNum);
//			
//		ExecutorCompletionService<CrawlerTask> executorCompletionService = new ExecutorCompletionService<CrawlerTask>(executor);
		
		String targetUrl = CrawlerUtils.crawlClassTargetUrlMap.get(ZhujiayiCrawler.class);
		ZhujiayiCrawler crawler  = new ZhujiayiCrawler(targetUrl);
		int totalPage = crawler.crawlTotalPage();
		
		if(totalPage>0) {
			String subUrl = crawler.getSubUrl();
			for(int i = 0 ;i<=totalPage;i++) {
				crawler = new ZhujiayiCrawler(targetUrl+subUrl+i);
				completionService.submit(crawler);
			}
				
		}
		store(totalPage);
		System.out.println(" crawlZhujiayi finished... ");
	}
}
