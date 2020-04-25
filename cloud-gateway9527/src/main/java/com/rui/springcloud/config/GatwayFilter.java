package com.rui.springcloud.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 自定义全局过滤器。yml配置也可以，但多了麻烦
 * @author zrh
 *
 */
@Configuration
@Order(0)//优先级  也可以实现Ordered接口
@Slf4j
public class GatwayFilter implements GlobalFilter,Ordered{

	@Override
	public int getOrder() {
		return 0;//返回数字越小，优先级越高   也可以使用注解@Order(0)
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String uname = exchange.getRequest().getQueryParams().getFirst("uname");//请求每次带uname
		//http://localhost:9527/payment/get/3?uname=222  放行，正常访问
		//http://localhost:9527/payment/get/1?uname=    为空，不放行HTTP ERROR 406
		//http://localhost:9527/payment/get/1   没有参数uname，不放行HTTP ERROR 406
		if(StringUtils.isEmpty(uname)) {
			log.info("用户名为null，非法！");
			exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);//设置各应状态码，不被接受
			return exchange.getResponse().setComplete();//退出 
		}
		return chain.filter(exchange);//放行
	}

}
