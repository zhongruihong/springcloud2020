package com.rui.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

import com.rui.config.MySelfRule;
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient//@EnableDiscoveryClient和@EnableEurekaClient共同点就是：都是能够让注册中心能够发现，扫描到该服务。不同点：@EnableEurekaClient只适用于Eureka作为注册中心，@EnableDiscoveryClient 可以是其他注册中心。
//@RibbonClient(name = "cloud-payment-service",configuration=MySelfRule.class)//启动自定义的负载均衡策略(MySelfRule.class)，注意：name值要和调用的服务提供者的应用名称(spring.application.name)一致

public class OrderApplication80 {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(OrderApplication80.class, args);
	}

}
