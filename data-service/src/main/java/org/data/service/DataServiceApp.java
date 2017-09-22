package org.data.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
}
