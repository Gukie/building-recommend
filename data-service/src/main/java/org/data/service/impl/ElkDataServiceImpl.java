package org.data.service.impl;

import java.io.IOException;

import com.alibaba.fastjson.JSON;

import org.apache.http.HttpHost;
import org.common.enums.IndexTypeEnum;
import org.common.model.BuildingDTO;
import org.data.service.ElkDataService;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * @author gushu
 * @date 2017/11/09
 */
//@Service("elkDataService")
public class ElkDataServiceImpl implements ElkDataService {
	private String host;
	private Integer port;
	private String scheme; //http, https
	private RestHighLevelClient restClient;
	private RestClient lowLevelClient;

	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public void init() {
		RestClientBuilder clientBuilder = RestClient.builder(new HttpHost(host, port, scheme)); 
		lowLevelClient =  clientBuilder.build();
		restClient = new RestHighLevelClient(lowLevelClient);
	}
	
	public void release() {
		if(lowLevelClient!=null){
			try {
				lowLevelClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public IndexResponse index(BuildingDTO buildingDTO) {
		IndexRequest request = new IndexRequest(IndexTypeEnum.building.getValue(),"test");
		request.source(JSON.toJSONString(buildingDTO), XContentType.JSON);
		try {
			return restClient.index(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
