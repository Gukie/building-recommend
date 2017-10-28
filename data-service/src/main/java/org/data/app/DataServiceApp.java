package org.data.app;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import org.common.model.BuildingDTO;
import org.common.result.BaseResult;
import org.data.service.DataService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@SpringBootApplication(scanBasePackages = "org.data")
@ImportResource("classpath:mybatis-spring.xml")
//@MapperScan("org.data.dao")
@EnableEurekaClient
// @FeignClient
@RestController
public class DataServiceApp {
	public static void main(String[] args) {
		SpringApplication.run(DataServiceApp.class, args);
	}

	@Resource(name = "dataService")
	private DataService dataService;

	// @RequestMapping(value="/store",method=RequestMethod.POST)
	// public String store(@RequestParam("buildingJsonStr") String
	// buildingJsonStr) {
	// @RequestMapping(value="/store",method=RequestMethod.POST,consumes =
	// "application/json")
	// public String store(@RequestParam("buildingJsonStr") String
	// buildingJsonStr) {

	/**
	 * not work
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "/hello")
	public String hello(String content) {

		String decodedContent = null;
		try {
			decodedContent = URLDecoder.decode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BuildingDTO buildingDTO = JSON.parseObject(decodedContent, BuildingDTO.class);
		dataService.add(buildingDTO);
		return BaseResult.SUCCESS;
	}

	@RequestMapping(value = "/test")
	public String test(String test) {
		System.out.println(test);
		return BaseResult.SUCCESS;
	}
	
	// start from here, we use it.
	@RequestMapping(value = "/store", method = RequestMethod.POST)
	public String store(@RequestBody BuildingDTO buildingDTO) {
		dataService.add(buildingDTO);
		return BaseResult.SUCCESS;
	}

	@RequestMapping(value = "/getExistingBuildingName", method = RequestMethod.GET)
	public String getExistingBuildingName() {
//		dataService.add(buildingDTO);
		List<String> existingBuildingNameList = dataService.getExistingBuildingName();
		return JSONArray.toJSONString(existingBuildingNameList);
	}
}
