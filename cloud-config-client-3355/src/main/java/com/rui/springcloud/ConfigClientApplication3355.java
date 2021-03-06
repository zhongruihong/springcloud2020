package com.rui.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
/*
 * Spring Framework Api：https://docs.spring.io/spring-framework/docs/current/javadoc-api/overview-summary.html
 *  本项目启动后可以通过 访问http://configserver.rui.com:3344/master/config2020-dev.yml
 *  前提是在windows的hosts文件中做了本地Ip的映射:127.0.0.1 configserver.rui.com
 */
@SpringBootApplication
@EnableEurekaClient //服务提供者 【eureka 客户端 】引入 spring-cloud-starter-netflix-eureka-client
@EnableDiscoveryClient//@EnableDiscoveryClient和@EnableEurekaClient共同点就是：都是能够让注册中心能够发现，扫描到该服务。不同点：@EnableEurekaClient只适用于Eureka作为注册中心，@EnableDiscoveryClient 可以是其他注册中心。
public class ConfigClientApplication3355 {
	/*mainboot自动提示生成，前提是pom.xml已经引入boot相关*/
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ConfigClientApplication3355.class, args);
	}
}
