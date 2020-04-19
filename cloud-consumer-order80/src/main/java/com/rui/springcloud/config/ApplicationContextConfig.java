package com.rui.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
/**
 * 采用注解方式依赖注入
 * @author zrh
 *
 */
@Configuration//配置
public class ApplicationContextConfig {
	@Bean//向spring容器中注入这个对象
	@LoadBalanced//单机版本只有一个服务，客户端url写死，不需要@LoadBalanced进行负载均衡；
	//但集群版一个服务名称(spring.application.name)下有多个服务，客户端需要提供负载均衡能力@LoadBalanced，客户端才能通过服务名称调用（@LoadBalanced负载均衡默认方式是轮询）。
	//如果没有该注解会报错，因为客户端只通过调用服务名称，并不知道自己要访问服务名称下的哪个服务（端口）
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
