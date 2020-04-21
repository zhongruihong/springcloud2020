package com.rui.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient//@EnableDiscoveryClient和@EnableEurekaClient共同点就是：都是能够让注册中心能够发现，扫描到该服务。不同点：@EnableEurekaClient只适用于Eureka作为注册中心，@EnableDiscoveryClient 可以是其他注册中心。
@EnableFeignClients////openfeign客户端实现：  业务逻辑接口（和provider的controller层一一对应）+@FeignClient配置调用provider服务(provider服务在eureka中注册的application.name值 )
@EnableHystrix//消费端启动Hystrix。注意：服务端启动hystrix时启动类上使用的是注解@EnableCircuitBreaker
public class OrderFeignHystrixApplication80 {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(OrderFeignHystrixApplication80.class, args);
	}

}
