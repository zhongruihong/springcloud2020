package com.rui.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementServerProperties.Servlet;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
/*
 * Spring Framework Api：https://docs.spring.io/spring-framework/docs/current/javadoc-api/overview-summary.html
 * 
 */
@SpringBootApplication
@EnableEurekaClient //服务提供者 【eureka 客户端 】引入 spring-cloud-starter-netflix-eureka-client
@EnableDiscoveryClient//@EnableDiscoveryClient和@EnableEurekaClient共同点就是：都是能够让注册中心能够发现，扫描到该服务。不同点：@EnableEurekaClient只适用于Eureka作为注册中心，@EnableDiscoveryClient 可以是其他注册中心。
@EnableCircuitBreaker//启动动类激活hystrix回调，代表回路
//消费者和服务提供者都可以做hystrix服务被降级
public class PayMentHystrixApplication8001 {
	/*mainboot自动提示生成，前提是pom.xml已经引入boot相关*/
	public static void main(String[] args) throws Exception {
		SpringApplication.run(PayMentHystrixApplication8001.class, args);
	}

	//注意：新版本Hystrix使用dashboard监控其他项目时，需要在主启动类中指定监控路径，不然访问9001主页输入要监控的项目路径会报:Unable to connect to Command Metric Stream 404
	//在需要监控的微服务项目中配置以下Servlet此配置为了服务监控，与服务容错本身无关！
	@Bean
	public ServletRegistrationBean<HystrixMetricsStreamServlet>  getServlet(){
		HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
		ServletRegistrationBean<HystrixMetricsStreamServlet> registrationBean = new ServletRegistrationBean<HystrixMetricsStreamServlet>(streamServlet);
		registrationBean.setLoadOnStartup(1);
		registrationBean.addUrlMappings("/hystrix.stream");//ServletRegistrationBean因为默认路径不是"/hystrix.stream"，要自己配置
		registrationBean.setName("HystrixMetricsStreamServlet");
		return registrationBean;
	}
	//监控步骤：
	//1、9001主页输入http://localhost:8001/hystrix.stream  对8001进行监控
	//2、访问 http://localhost:8001/payment/circuit/1   (正常访问)
	//	访问 http://localhost:8001/payment/circuit/-1   (内部错误异常)
	//3、监控主页说明：
	// Circuit Closed（绿色字体） 当不断请求错误时，变为Circuit Opend（红色字体）
	// 7色:Success（深绿） | Short-Circuited （蓝）| Bad Request （浅绿）| Timeout （橙）| Rejected （紫）| Failure （红）| Error （灰）
	// 1圈:通过颜色的变化代表实例的健康程度-绿色<黄色<橙色<红色递减；通过大小代表请求流量的变化。综上，可以通过圆圈快速发现故障服务和高压力服务
	// 1线:峰值线
	
	//后面直接用Sentinel实现熔断与限流！

}
