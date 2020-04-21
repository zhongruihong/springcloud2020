package com.rui.springcloud.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.rui.springcloud.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
	//模拟 正常访问
	@Override
	public String paymentInfo_OK(Integer id) {
		// hystrix处理用的是tomcat里面的线程池
		return "线程池：" + Thread.currentThread().getName() + "paymentInFfo_OK()" + id;
	}
	//模拟 请求超时 
	@Override
	@HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",//被注解方法出错，设置类中的其他方法兜底，如此处的paymentInfo_TimeOutHandler()方法
	commandProperties = {
		//设置激活回调方法的条件，此处设置3秒超时错误（本方法参数值id大于3后就可能触发），name和value是必填值，触发后调用fallbackMethod指定的方法
		//http://localhost:8001/payment/hystrix/timeout/2 正常访问
		//http://localhost:8001/payment/hystrix/timeout/4 服务降级
		@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
	})
	//一旦调用方法失败，抛出异常之后 会自动调用@HystrixCommand标注好的fallbackMethod调用类中指定的方法
	public String paymentInfo_TimeOut(Integer id) {
		try {
			int time = id;//模拟耗时id秒  如果id值大于3秒，可能(线程睡3秒不一定是刚好3秒?)会回调本类方法上方注解指定的回调方法
			int i = id /0 ;//模拟调用方法出错 ，同样会回调本方法上方注解指定的回调方法
			TimeUnit.SECONDS.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "线程池：" + Thread.currentThread().getName() + "paymentInFfo_TimeOut()" + id;
//服务端设置调用时间峰值
	}
	//模拟出错时回调的兜底方法
	public String paymentInfo_TimeOutHandler(Integer id ) {
		return "系统忙，当前线程池:"+Thread.currentThread().getName()+"paymentInfo_TimeOutHandler,id"+id;
	}
}
