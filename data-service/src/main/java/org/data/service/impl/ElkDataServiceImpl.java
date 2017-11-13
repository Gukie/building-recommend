package org.data.service.impl;

import java.io.IOException;
import java.util.Base64;

import com.alibaba.fastjson.JSON;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.common.constant.SpecialValues;
import org.common.enums.es.IndexEnum;
import org.common.model.BuildingDTO;
import org.data.enums.DBTableEnum;
import org.data.service.ElkDataService;
import org.data.utils.GeneratorUtils;
import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gushu
 * @date 2017/11/09
 */
// @Service("elkDataService")
public class ElkDataServiceImpl extends BaseDataServiceImpl implements ElkDataService {

	private Logger logger = LoggerFactory.getLogger(ElkDataServiceImpl.class);

	private String host;
	private Integer port;
	private String scheme; // http, https
	private String username;
	private String password;
	private RestHighLevelClient restClient;
	private RestClient lowLevelClient;
	
	@Autowired
	private GeneratorUtils generator;

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
		lowLevelClient = clientBuilder.build();
		restClient = new RestHighLevelClient(lowLevelClient);
	}

	public void release() {
		if (lowLevelClient != null) {
			try {
				lowLevelClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String index(BuildingDTO buildingDTO) {
		String id = generator.generateId(DBTableEnum.building);
		buildingDTO.setId(id);
		IndexRequest request = new IndexRequest(IndexEnum.building.getIndex(), IndexEnum.building.getType(),
				buildingDTO.getId());
		request.source(JSON.toJSONString(buildingDTO), XContentType.JSON);
		try {
			Header header = new BasicHeader("Authorization", generateToken());
			IndexResponse response = restClient.index(request, header);
			if (Result.CREATED == response.getResult()) {
				logger.debug("index succeffully");
				return response.getId();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SpecialValues.EMPTY_STR;
	}

	/**
	 * refer:
	 * <li>
	 * https://www.elastic.co/guide/en/shield/current/_using_elasticsearch_http_rest_clients_with_shield.html
	 * <li>https://developer.mozilla.org/en-US/docs/Web/HTTP/Authentication
	 * 
	 * @return
	 */
	private String generateToken() {
		StringBuilder encodStr = new StringBuilder();
		encodStr.append(username);
		encodStr.append(SpecialValues.COLON_STR);
		encodStr.append(password);
		String encodedToken = Base64.getEncoder().encodeToString(encodStr.toString().getBytes());
		return "Basic " + encodedToken;
	}

}
