package com.rui.springcloud.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rui.springcloud.entity.CommonResult;
import com.rui.springcloud.entity.Payment;
import com.rui.springcloud.service.PaymentFeignService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j//日志
//openfeign客户端controller层
public class OrderFeignController {
	
	@Resource
	private PaymentFeignService paymentFeignService;//feign客户端的controller层注入feign接口
	@GetMapping(value = "/consumer/payment/get/{id}")//rest风格  浏览器直接访问：http://localhost/consumer/payment/get/4
	public CommonResult<Payment>getPaymentById(@PathVariable("id")Long id) {
		log.info("【feign客户端】开始get请求");
		//相当于又封装了一层，通过feign客户端接口，利用provider服务对外暴露的【服务名】和【地址】，进而去注册中心调用【对应的】provider服务
		return paymentFeignService.getPaymentById(id);
	}
}
