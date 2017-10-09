package org.data.app;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;

import org.common.model.BuildingDTO;
import org.common.result.BaseResult;
import org.data.service.DataService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */
@SpringBootApplication
@MapperScan("org.data.dao")
public class DataServiceApp 
{
    public static void main( String[] args )
    {
        SpringApplication.run(DataServiceApp.class, args);
    }
    
    @Resource(name="dataService")
    private DataService dataService;
    
    @RequestMapping("/store")
    public String store(String buildingJsonStr) {
		
    	BuildingDTO buildingDTO = JSON.parseObject(buildingJsonStr, BuildingDTO.class);
    	dataService.add(buildingDTO);
    	return BaseResult.SUCCESS;
	}
    
    
}
