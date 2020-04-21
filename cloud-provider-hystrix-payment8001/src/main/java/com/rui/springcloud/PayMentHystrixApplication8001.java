package com.rui.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
/*
 * Spring Framework Api：https://docs.spring.io/spring-framework/docs/current/javadoc-api/overview-summary.html
 * 
 */
@SpringBootApplication
@EnableEurekaClient //服务提供者 【eureka 客户端 】引入 spring-cloud-starter-netflix-eureka-client
@EnableDiscoveryClient//@EnableDiscoveryClient和@EnableEurekaClient共同点就是：都是能够让注册中心能够发现，扫描到该服务。不同点：@EnableEurekaClient只适用于Eureka作为注册中心，@EnableDiscoveryClient 可以是其他注册中心。
@EnableCircuitBreaker//启动动类激活hystrix回调，代表回路
//消费者和服务提供者都可以做hystrix服务被降级
public class PayMentHystrixApplication8001 {
	/*mainboot自动提示生成，前提是pom.xml已经引入boot相关*/
	public static void main(String[] args) throws Exception {
		SpringApplication.run(PayMentHystrixApplication8001.class, args);
	}
}