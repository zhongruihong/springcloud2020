package com.rui.springcloud.config;

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
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
