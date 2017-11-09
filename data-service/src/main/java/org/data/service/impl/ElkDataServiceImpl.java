package org.data.service.impl;

import java.io.IOException;
import java.util.Base64;

import com.alibaba.fastjson.JSON;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.common.constant.SpecialValues;
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
	private String username ;
	private String password ;
	private RestHighLevelClient restClient;
	private RestClient lowLevelClient;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
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
			Header header = new BasicHeader("Authorization",generateToken());
			return restClient.index(request,header);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * refer: 
	 * <li> https://www.elastic.co/guide/en/shield/current/_using_elasticsearch_http_rest_clients_with_shield.html
	 * <li> https://developer.mozilla.org/en-US/docs/Web/HTTP/Authentication
	 * @return
	 */
	private String generateToken() {
		StringBuilder encodStr = new StringBuilder();
		encodStr.append(username);
		encodStr.append(SpecialValues.COLON_STR);
		encodStr.append(password);
		String encodedToken = Base64.getEncoder().encodeToString(encodStr.toString().getBytes());
		return "Basic "+encodedToken;
	}
	
}
