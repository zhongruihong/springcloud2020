package com.rui.springcloud.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.rui.springcloud.service.PaymentService;

import cn.hutool.core.util.IdUtil;

@Service
public class PaymentServiceImpl implements PaymentService {
	//模拟 正常访问
	@Override
	public String paymentInfo_OK(Integer id) {
		// hystrix处理用的是tomcat里面的线程池
		return "线程池：" + Thread.currentThread().getName() + "paymentInFfo_OK()" + id;
	}
	//**************************************服务降级
	//模拟 请求超时 
	@Override
	@HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",//被注解方法出错，设置类中的其他方法兜底，如此处的paymentInfo_TimeOutHandler()方法
	commandProperties = {
		//设置激活回调方法的条件，此处设置3秒超时错误（本方法参数值id大于5后就可能触发），name和value是必填值，触发后调用fallbackMethod指定的方法
		//http://localhost:8001/payment/hystrix/timeout/2 正常访问 （如果不设置id/0错误）
		//http://localhost:8001/payment/hystrix/timeout/6 服务降级
		@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
	})
	//一旦调用方法失败，抛出异常之后 会自动调用@HystrixCommand标注好的fallbackMethod调用类中指定的方法
	public String paymentInfo_TimeOut(Integer id) {
		try {
			int time = id;//模拟耗时id秒  如果id值大于3秒，可能(线程睡3秒不一定是刚好3秒?)会回调本类方法上方注解指定的回调方法
			//int i = id /0 ;//模拟调用方法出错 ，同样会回调本方法上方注解指定的回调方法
			TimeUnit.SECONDS.sleep(time);//参数单位：秒
			//TimeUnit.MICROSECONDS.sleep(time);//参数单位:毫秒
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "线程池：" + Thread.currentThread().getName() + "paymentInFfo_TimeOut()" + id;
//服务端设置调用时间峰值
	}
	//模拟出错时回调的兜底方法
	public String paymentInfo_TimeOutHandler(Integer id ) {
		return "服务端hystrix:系统忙，当前线程池:"+Thread.currentThread().getName()+"paymentInfo_TimeOutHandler,id"+id;
	}
	
	//***********************************服务熔断   恢复调用链路  circuit-breaker:保险丝
	//配置多次访问出错触发服务熔断的条件：
	//开启、
	///请求总数阀值：在快照时间窗内，必须满足请求总数阀值才有资格熔断，默认是20次！意味着在10秒（快照时间窗）内，hystrix命令的调用次数足20次，即使所有的请求都超时或者其他原因失败，断路器都不会打开
	//快照时间窗：断路器确定是否打开需要统计一些请求和错误次数，而统计的时间范围就是快照时间窗，默认为最近的10秒。打开熔断持续到一定时间后进入半熔断状态：部分请求根据规则调用当前服务，如果请求成功后且符合规则就认为当前恢复正常，关闭熔断、
	//触发熔断的错误百分比阀值 :默认50%，当请求总数在快照时间窗内超过了阀值 ，例如在这30次调用中，有15次发生了异常，错误率达到50%，此时会打开断路 器
	@HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties= {//以下配置参考HystrixCommandProperties.java很多默认属性值，自己没有通过注解配置参数，就使用该类默认的值
		@HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开启断路器
		@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求峰值次数
		@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),///时间窗口期、时间范围,半开状态。。窗口期open后，继续尝试调用，看是否恢复
		@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")//失败率(此处为60%)，达到或者超过就断路 
	}
	)
	@Override
	public String paymentCircuitBreaker(@PathVariable("id")Integer id) {
		if(id < 0 ) {//模拟调用出错 
			throw new RuntimeException("id不能为负数 ");
		}
		String serialNumber = IdUtil.simpleUUID();//hutool-all工具包 hutool.cn 等价于UUID.randomUUID().toString();
		return Thread.currentThread().getName()+"\t"+"调用成功,流水号:"+serialNumber;
	}
	//总结：
	//1、当满足一定的阀值时，默认10秒内20次请求
	//2、当满足一定失败率时，默认10秒内50%
	//达到以上阀值时，断路器开启
	//当开启断路器时，所有请求不能进行转发
	//当一段时间后，默认5秒，断路器变成半开状态，会让其中一个请求进行转发
	//如果成功，断路 器关闭，如果失败，继续开启，5秒后又尝试转发
	//熔断回调方法
	public String paymentCircuitBreaker_fallback(@PathVariable("id")Integer id) {
		return "id不能为负数 ";
	}
	//微服务太多，看log恼火，看图形化界面...DashBoard仪表盘
}
