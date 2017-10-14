package org.data.app;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;

import org.common.model.BuildingDTO;
import org.common.result.BaseResult;
import org.data.service.DataService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@SpringBootApplication(scanBasePackages="org.data")
@MapperScan("org.data.dao")
@EnableEurekaClient
//@FeignClient
@RestController
public class DataServiceApp 
{
    public static void main( String[] args )
    {
        SpringApplication.run(DataServiceApp.class, args);
    }
    
    @Resource(name="dataService")
    private DataService dataService;
    
//    @RequestMapping(value="/store",method=RequestMethod.POST)
//    public String store(@RequestParam("buildingJsonStr") String buildingJsonStr) {
//    @RequestMapping(value="/store",method=RequestMethod.POST,consumes = "application/json")
//    public String store(@RequestParam("buildingJsonStr") String buildingJsonStr) {
    
    
    @RequestMapping(value="/store")
    public String store(String buildingJsonStr) {
		
    	BuildingDTO buildingDTO = JSON.parseObject(buildingJsonStr, BuildingDTO.class);
    	dataService.add(buildingDTO);
    	return BaseResult.SUCCESS;
	}
    
    @RequestMapping(value="/test")
    public String test(String test) {
    	System.out.println(test);
    	return BaseResult.SUCCESS;
	}
    
    
}
