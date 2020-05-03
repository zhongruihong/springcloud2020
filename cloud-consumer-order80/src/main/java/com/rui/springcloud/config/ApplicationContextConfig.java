package com.rui.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RetryRule;
/**
 * 采用注解方式依赖注入
 * @author zrh
 *
 */
@Configuration//配置
//注意：自定义配置类，不能放在@ComponentScan所扫描的当前包以及子包下，否则自定义的这个配置类就会被所有的Ribbon客户端 所共享，达不到特殊定制化的目的
//所以本类ApplicationContextConfig.java中不能写负载均衡规则
//因为ApplicationContextConfig.java会被启动类OrderApplication80.java中注解@SpingBootApplication中的@ComponentScan可以扫描到
public class ApplicationContextConfig {
	@Bean//向spring容器中注入这个对象
	//注释注解@LoadBalanced，用自己手写的负载均衡算法。
	//@LoadBalanced//单机版本只有一个服务，客户端url写死，不需要@LoadBalanced进行负载均衡；
	//但集群版一个服务名称(spring.application.name)下有多个服务，客户端需要提供负载均衡能力@LoadBalanced，客户端才能通过服务名称调用（@LoadBalanced负载均衡默认方式是轮询）。
	//如果没有该注解会报错，因为客户端只通过调用服务名称，并不知道自己要访问服务名称下的哪个服务（端口）
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
