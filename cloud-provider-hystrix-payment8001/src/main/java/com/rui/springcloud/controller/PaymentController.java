package com.rui.springcloud.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rui.springcloud.service.PaymentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PaymentController {
	@Resource
	private PaymentService paymentService;
	@Value("${server.port}")
	private String port;

	// http://localhost:8001/payment/hystrix/ok/5
	@GetMapping(value = "/payment/hystrix/ok/{id}")
	public String paymentInfo_OK(@PathVariable("id") Integer id) {
		String paymentInfo_OK = paymentService.paymentInfo_OK(id);
		log.info(paymentInfo_OK);
		return paymentInfo_OK;

	}

	// http://localhost:8001/payment/hystrix/timeout/5 正常访问
	// http://localhost:8001/payment/hystrix/timeout/10 正常访问
	@GetMapping(value = "/payment/hystrix/timeout/{id}")
	public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
		String paymentInfo_TimeOut = paymentService.paymentInfo_TimeOut(id);
		log.info(paymentInfo_TimeOut);
		return paymentInfo_TimeOut;

	}
	//jmeter下载地址： http://jmeter.apache.org/download_jmeter.cgi
	//双击bin目录下的jmeter.bat  Options->Choose Language->Chinese （Simplified）
	//添加本次测试计划 （右键-->添加-->Threads（Users）-->线程组）
	//设置线程数 （所谓线程数就是并发用户数）:200、循环次数:100，保存。200*100就是两万个并发！
	//右键->添加->取样器->HTTP请求，添加协议及相关配置信息（保存）：
	//ip:localhost，端口：8001，方法：get，路径：http://localhost:8001/payment/hystrix/timeout/5
	//启动测试
	//分别在浏览器访问：http://localhost:8001/payment/hystrix/ok/5   http://localhost:8001/payment/hystrix/timeout/5
	//看ok方法是否卡顿
	
	//****************服务降级->服务熔断->服务恢复调用链路
	
	//http://localhost:8001/payment/circuit/1  正数正常
	//http://localhost:8001/payment/circuit/-1 负数抛异常 回调方法
	//http://localhost:8001/payment/circuit/-1 访问很多次（此例为10次）之后（一直负数刷新页面），就算输入正值（http://localhost:8001/payment/circuit/正数）访问，也不会打开正常页面
	@GetMapping("/payment/circuit/{id}")
	public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
		String result = paymentService.paymentCircuitBreaker(id);
		log.info(result);
		return result;
	}
	
}
