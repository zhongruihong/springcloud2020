package com.rui.springcloud.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.rui.springcloud.service.PaymentFeignHystrixService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j//日志
//openfeign客户端controller层
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")//设置全局的异常回调方法
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
	//消费端使用hystrix进行服务降级:
/*
	@HystrixCommand(fallbackMethod= "paymentInfo_TimeOutHandler",commandProperties = {
		//设置激活回调方法的条件，此处设置3秒超时错误（本方法参数值id大于3后就可能触发），name和value是必填值，触发后调用fallbackMethod指定的方法
		//http://localhost/consumer/payment/hystrix/timeout/2 正常访问
		//http://localhost/consumer/payment/hystrix/timeout/4 消费端服务降级(消费端该方法设置的超时时间是3秒进行降级处理)
		//http://localhost/consumer/payment/hystrix/timeout/6 服务端服务降级(服务端对应方法设置的超时时间是5秒进行降级处理) 
		@HystrixProperty(name= "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
	})
*/
	@HystrixCommand//注解掉原来专门的回调设置，使用@HystrixCommand表示要hystrix处理，此时走的是全局的处理！
	//ps：有专门的回调设置就走专门的回调方法，没有就走@DefaultProperties设置的默认的回调方法
	//前提是方法上方都要有@HystrixCommand注解
	public String paymentInfo_TimeOut(@PathVariable("id")Long id) {
		int i = (int)(id / 0);//消费端设置出错 模拟有全局回调方法
		//openfeign-ribbon 客户端一般默认等待1秒钟，如果服务端处理业务超过1秒钟， 超过会报错：status 404 reading PaymentFeignService#paymentFeignTimeout()
		log.info("【feign&hystrix客户端】开始请求paymentInfo_TimeOut");
		return paymentFeignHystrixService.paymentInfo_TimeOut(id);
	}
	
	//feign.FeignException$NotFound: status 404 reading PaymentFeignHystrixService#paymentInfo_OK(Long)
	
	//模拟出错时回调的兜底方法
	public String paymentInfo_TimeOutHandler(Long id ) {//注意！！！！回调方法和原方法的参数列表（个数 、类型）一定要一致，否则会报错，找不到fallbackMethod指定的方法！
		return "消费端hystrix:系统忙，当前线程池:"+Thread.currentThread().getName()+"paymentInfo_TimeOutHandler,id"+id;
	}
	
	//全局回调方法
	public String payment_Global_FallbackMethod() {
		return "全局异常回调方法";
	}
}
