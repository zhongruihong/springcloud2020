package com.rui.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "cloud-payment-hystrix-service")//这里调用的是有hystrix的服务~
//openfeign实现：  业务逻辑接口（和provider的controller层一一对应）+@FeignClient配置调用provider服务(provider服务在eureka中注册的application.name值 )
//该feign接口在自身的controller层注入供调用
public interface PaymentFeignHystrixService {
	//业务逻辑接口（和provider的controller层一一对应）
	@GetMapping(value = "/payment/hystrix/ok/{id}")//rest风格  浏览器直接访问：http://localhost:8001/payment/hystrix/get/4
	String paymentInfo_OK(@PathVariable("id")Long id) ;
	//feign接口，通过provider服务对外暴露的【服务名】（"cloud-payment-hystrix-service"）和【地址】("/payment/hystrix/get/{id}")，进而去注册中心调用【对应的】provider服务

	@GetMapping(value = "/payment/hystrix/timeout/{id}")
	String paymentInfo_TimeOut(@PathVariable("id")Long id);
}
