package com.rui.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
/**
 * nacos-discovery集成了ribbon的~~~
 * @author zrh
 *
 */
@Configuration
public class ApplicationContextConfig {
	@Bean
	@LoadBalanced//用RestTemplate结合ribbon做负载均衡时，一定必须加此注解...
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
