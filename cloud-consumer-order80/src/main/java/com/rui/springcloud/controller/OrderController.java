package com.rui.springcloud.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.rui.springcloud.entity.CommonResult;
import com.rui.springcloud.entity.Payment;

import lombok.extern.slf4j.Slf4j;
/**
 * 消费者是去调用服务提供者，因此消费者（客户端）只有Controller层，无service层和dao层
 * 两个服务之间的调用，原始的用httpClient，现在用spring的RestTemplate，对httpClient进行了封装
 * RestTemplate提供了多种访问远程http服务的方法，
 * 是一各简单便捷的访问restful服务模板类，是spring提供的用于访问Rest服务的客户端模拟 工具集
 * RestTemplate Api:https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html
 * 采用注解方式依赖注入，进行配置
 * @author zrh
 *
 */
@RestController
@Slf4j
public class OrderController {
	public static final String PAYMENT_URL = "http://localhost:8001";//暂时使用固定的服务地址
	@Resource//在Config中配置，再在此注入
	private RestTemplate restTemplate;//使用RestTemplate进行rest风格的服务调用
	@GetMapping(value="/consumer/payment/create")//消费者无需知道端口，默认80，postman模拟get请求：http://localhost/consumer/payment/create?serial=zrh
	public CommonResult<Payment>create(Payment p){
		log.info("消费端开始get请求create");
		//这里有个问题，请求到服务提供者时，实体为null，什么原因? 服务提供者实体前没有加@RequestBody
		return restTemplate.postForObject(PAYMENT_URL+"/payment/create",p ,CommonResult.class);
	}
	@GetMapping(value="/consumer/get/{id}")//消费者无需知道端口，默认80，postman模拟get请求：http://localhost/consumer/get/4
	public CommonResult<Payment>getPayment(@PathVariable("id")Long id){
		log.info("消费端开始get请求");
		return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id, CommonResult.class);
	}
}
