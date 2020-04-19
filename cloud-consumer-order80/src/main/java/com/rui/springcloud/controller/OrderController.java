package com.rui.springcloud.controller;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
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
 * 
 * @author zrh
 *
 * Ribbon是客户端负载均衡工具，主要提供客户端的软件负载均衡算法和服务调用，如连接超时、重试等。
 * 在配置文件中列出Load Balancer后面所有的机器 ，Ribbon会自动基于某种规则(如简单轮询、随机连接)去连接这些机器 ，
 * 使用Ribbon可以实现自定义的负载均衡算法，配合RestTemplate实现远程调用。
 * Ribbon是一个软负载均衡的客户端组件，可以和其他所需请求的客户端结合使用，和eureka的结合就是其中一个实例。新版的eureka client中已经整合了ribbon，可通过pom.xml的Dependency Hierarchy查看
 * 
 * 
 * Ribbon工作步骤：
 * 1、选择EurekaServer:优先选择在同一个区域内负载较少的server;
 * 2、再根据用户指定策略，从server取到的【服务列表】中选择一个地址
 * Ribbon提供7种?【策略】：轮询 、随机、响应时间加权....
 * 
 * 
 * Ribbon本地负载均衡和Nginx服务端负载均衡区别:
 * 		Nginx是服务器负载均衡，客户端所有请求会交给Nginx，由其实现转发请求，即负载均衡由服务端 实现
 * 		Ribbon本地负载均衡，在调用微服务接口时，会在注册中心上获取注册信息服务列表之后 缓存到JVM本地，从而在本地实现RPC远程服务调用！
 * 		Ribbon属于进程内的load balance 它只是一个类库，集成消费端进程
 * Ribbon目前已经进入维护(Maintenance)阶段，但依然大量使用，未来可以使用Spring Cloud LoadBalance替代
 *
 *
 */
@RestController
@Slf4j
public class OrderController {
	//public static final String PAYMENT_URL = "http://localhost:8001";//单机版暂时使用固定的服务地址
	public static final String PAYMENT_URL ="http://cloud-payment-service";//（服务名称大小写都可以吗?）集群版使用服务调用访问时，只认在注册中心对外显露注册的服务名称(spring.application.name值)，与ip和端口号无关
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
	//getForObject()返回对象为响应体中数据转换的对象，基本上可以理解为json
	//getForEntity()返回对象为ResponseEntity对象，包含了响应中的一些重要信息，比如headers响应头、status状态码、body响应体
	//可以通过getBody()获取 

	@GetMapping(value="/consumer/getForEntity/{id}")//http://localhost/consumer/getForEntity/4
	public CommonResult<Payment>getPayment2(@PathVariable("id")Long id){
		 ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL+"/payment/get/"+id, CommonResult.class);
		if(entity.getStatusCode().is2xxSuccessful()) {//返回成功
			log.info("消费端开始get请求:"+entity.getStatusCodeValue()+"\t"+entity.getHeaders());
			return entity.getBody();
		}
		return new CommonResult<Payment>(444,"操作失败");
	
	}
}
