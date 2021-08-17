package com.lxw.website;

import javafx.application.Application;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

//禁用默认的数据源配置
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@MapperScan("com.lxw.website.dao")
@EnableScheduling
public class WebsiteApplication {

	public static void main(String[] args) {
		//spring去除标识
		SpringApplication webSpringApplication= new SpringApplication(WebsiteApplication.class);
		//webSpringApplication.setBannerMode(Banner.Mode.OFF);
		webSpringApplication.run(args);
	}

}
