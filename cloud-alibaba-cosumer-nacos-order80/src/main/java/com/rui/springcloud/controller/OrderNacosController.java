package com.rui.springcloud.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@RestController()
@Slf4j
public class OrderNacosController {

	@Resource
	private RestTemplate restTemplate;
	
	@Value("${server.port}")
	private String serverPort;
	
	@Value("${service-url.user-service}")
	private String url;
	//http://localhost/order/nacos/get/113 不断访问，消费端将负载均衡的调用服务提供方对应方法
	@GetMapping("/order/nacos/get/{id}")
	public String getPayment(@PathVariable("id")Integer id) {
		log.info(serverPort+"消费者开始消费,调用服务端...");
		return restTemplate.getForObject(url+"/payment/nacos/"+id,String.class);//服务提供方的方法对应地址
	}
}
