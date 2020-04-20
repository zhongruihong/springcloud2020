package com.rui.springcloud.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rui.springcloud.service.PaymentFeignHystrixService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j//日志
//openfeign客户端controller层
public class OrderFeignHystrixController {
	
	@Resource
	private PaymentFeignHystrixService paymentFeignHystrixService;//feign客户端的controller层注入feign接口
	@GetMapping(value = "/consumer/payment/hystrix/ok/{id}")//consumer代表来自消费侧请求。
	//rest风格  浏览器直接访问：http://localhost/consumer/payment//hystrix/ok/
	public String paymentInfo_OK(@PathVariable("id")Long id) {
		log.info("【feign&hystrix客户端】开始请求paymentInfo_OK");
		//相当于又封装了一层，通过feign客户端接口，利用provider服务对外暴露的【服务名（sping.application.name）】和【地址（controller层）】，进而去注册中心调用【对应的】provider服务
		return paymentFeignHystrixService.paymentInfo_OK(id);
	}

	@GetMapping(value = "/consumer/payment/hystrix/timeout/{id}")
	//浏览器直接访问：http://localhost/consumer/payment/hystrix/timeout/5
	public String paymentInfo_TimeOut(@PathVariable("id")Long id) {
		//openfeign-ribbon 客户端一般默认等待1秒钟，如果服务端处理业务超过1秒钟， 超过会报错：status 404 reading PaymentFeignService#paymentFeignTimeout()
		log.info("【feign&hystrix客户端】开始请求paymentInfo_TimeOut");
		return paymentFeignHystrixService.paymentInfo_TimeOut(id);
	}
	
	//feign.FeignException$NotFound: status 404 reading PaymentFeignHystrixService#paymentInfo_OK(Long)
}
