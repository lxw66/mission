package com.lxw.website.jdbc;

import com.lxw.website.enums.DataTypeEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.read")
    public DataSource readDataSource(){
            return DataSourceBuilder.create().build();
    }


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.write")
    public DataSource writeDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSource myRountingDataSorce(@Qualifier("readDataSource")DataSource readDataSource,
                                          @Qualifier("writeDataSource")DataSource writeDataSource){
        Map<Object,Object> dataSouceMapper=new HashMap<Object,Object>();
        dataSouceMapper.put(DataTypeEnum.READ,readDataSource);
        dataSouceMapper.put(DataTypeEnum.WRITE,writeDataSource);
        MyRoutingDataSource myRoutingDataSource=new MyRoutingDataSource();
        //默认为读数据库
        myRoutingDataSource.setDefaultTargetDataSource(readDataSource);
        myRoutingDataSource.setTargetDataSources(dataSouceMapper);
        return myRoutingDataSource;
    }

}
