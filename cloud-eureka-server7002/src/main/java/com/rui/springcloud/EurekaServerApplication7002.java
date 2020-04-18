package com.rui.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
/**
 * 
 * @author zrh
 *
 */
@SpringBootApplication
@EnableEurekaServer//eureka有两个注册组件 @EnableEurekaServer表示该项目是服务注册中心  引入 spring-cloud-starter-netflix-eureka-server
public class EurekaServerApplication7002 {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(EurekaServerApplication7002.class, args);
	}

}
