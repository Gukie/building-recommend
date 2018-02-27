package org.data.controller;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author gushu
 * @date 2018/02/27
 */
public class ControllerTpsTest {

	private RestTemplate restTemplate;

	@Before
	public void init() {
		restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin"));
		restTemplate.getMessageConverters().add(fastJsonHttpMessageConverter());
	}

	public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setCharset(StandardCharsets.UTF_8);

		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		// fastConverter.getSupportedMediaTypes().add(new
		// MediaType("application","json"));
		return fastConverter;
	}

	@Test
	public void testTps4CarnumberQuery() {

		int loops = 210;
		List<Long> consumedTime = new ArrayList<>();
		Long total = 0L;
		for(int i = 0;i<loops;i++){
			long start = System.currentTimeMillis();
			String carNumber = "皖B6W4C4";
//			String detectorDeviceId = "device-1234";
			String detectorDeviceId = "";
			String url = "http://localhost:8005/xxx/xxx";
			HttpEntity<MultiValueMap<String, Object>> request = buildRequest(carNumber, detectorDeviceId);
			String result = restTemplate.postForObject(url, request, String.class);
			long end = System.currentTimeMillis();
			long perConsumed = end-start;
			consumedTime.add(perConsumed);
			total+= perConsumed;
		}
		long average = total / loops;
		System.err.println("average:"+average);
		consumedTime.stream().filter((time) -> time > average).forEach(System.err::println);
		
	}

	private HttpEntity<MultiValueMap<String, Object>> buildRequest(String param1, String param2) {
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		param.add("param1", param1);
		if(StringUtils.isNotBlank(param2)){
			param.add("param2", param2);
		}

		HttpHeaders httpHeaders = new HttpHeaders();
		return new HttpEntity<>(param, httpHeaders);
	}


}
