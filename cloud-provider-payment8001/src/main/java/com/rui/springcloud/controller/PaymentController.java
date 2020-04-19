package com.rui.springcloud.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
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
	@Value("${spring.application.name}")
	private String serviceId;
	@Resource
	private PaymentService service;
	@Resource
	private DiscoveryClient discoveryClient;//注册进注册中心后，服务自己的基础信息。对于注册进eureka里面的服务，可以通过服务发现来获得该服务的信息

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
	@GetMapping(value="/payment/discovery")//  http://localhost:8001/payment/discovery
	public Object discovery() {
		List<String> services = discoveryClient.getServices();//获取的是所有已经注册进注册中心的应用的名称
		for (String service : services) {
			log.info(service);
		}
		List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);//获取服务名称下的所有服务，serviceId是对外显露的应用名称
		for (ServiceInstance instance : instances) {
			log.info(instance.getHost()+"\t"+instance.getInstanceId()+"\t"+instance.getUri());//ip【对外！】 应用名称  url
		}
		return this.discoveryClient;
	}
	//用于模拟自定义负载均衡调用
	@GetMapping(value = "/payment/mylb")
	public String getPaymentLB() {
		return port;
	}
	//模拟消费端调用服务提供才超时报错
	@GetMapping(value = "/payment/feign/timeout")//对外暴露的rest风格地址，Feign客户端调用
	public String paymentFeignTimeout() {
		try {
			//模拟服务提供者复杂业务处理要用时3秒钟  
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return port;
	}
}
