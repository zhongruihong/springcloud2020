package com.rui.springcloud.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.rui.springcloud.entity.CommonResult;
import com.rui.springcloud.entity.Payment;
import com.rui.springcloud.service.PaymentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j//打日志  控制台要打印彩色日志，在eclipse market搜索安装ansi
public class PaymentController {
	@Value("${server.port}")//@Value("${xxx.xx}")获取application.yml中配置的端口号，通过服务对应的端口号，看是具体调用的哪个服务
	private String port;
	@Resource
	private PaymentService service;
	@PostMapping(value = "/payment/create")//rest风格 浏览器直接访问发送的是get请求,所以用postman模拟post请求： http://localhost:8001/payment/create?serial=zrh
	public CommonResult<Payment> create(@RequestBody Payment p) {//要加@RequestBody，否则其他服务调用时，实体无法注入值...
		log.info("【服务端】开始post请求create，调用端口："+port);
		int i = service.create(p);
		log.info("插入结果:"+i);
		if(i > 0) {
			return new CommonResult(200,"插入成功，调用端口："+port,i);
		}else {
			return new CommonResult(444,"插入失败，调用端口："+port,null);
		}
	}
	@GetMapping(value = "/payment/get/{id}")//rest风格  浏览器直接访问：http://localhost:8001/payment/get/4
	public CommonResult<Payment>getPaymentById(@PathVariable("id")Long id) {
		log.info("【服务端】开始get请求，调用端口："+port);
		Payment p = service.getPaymentById(id);
		if(p != null) {
			return new CommonResult<Payment>(200,"查询成功，调用端口："+port,p);
		}else {
			return new CommonResult<Payment>(444,"无记录，调用端口："+port,null);
		}
	}
}
