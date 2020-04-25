package com.rui.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 编码方式配置路由规则
 * 
 * @author zrh
 *
 */
@Configuration
public class GateWayCofig {

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		RouteLocatorBuilder.Builder routes = builder.routes();
		//访问http://localhost:9527/baidu时将会转发到http://www.baidu.com
		routes.route("pathId", r -> r.path("/baidu").uri("http://www.baidu.com")).build();
		return routes.build();
	}
	
	@Bean
	public RouteLocator customRouteLocator2(RouteLocatorBuilder builder) {
		RouteLocatorBuilder.Builder routes = builder.routes();
		//访问http://localhost:9527/sina时将会转发到http://www.sina.com.cn
		routes.route("pathId", r -> r.path("/sina").uri("http://www.sina.com.cn")).build();
		return routes.build();
	}
}
