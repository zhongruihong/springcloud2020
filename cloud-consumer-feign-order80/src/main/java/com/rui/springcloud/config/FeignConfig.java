package com.rui.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;
/**
 *   
	#feign提供了日志打印功能，可以配置调整日志级别，了解feign中的http请求细节
	#feign日志级别：
	#NONE 默认，不显示 日志
	#BASIC 仅记录【请求方法】、【URL】、【响应状态码】、【执行时间】
	#HEADERS 除BASIC中定义的信息外，还有请求和响应的【头信息】
	#FULL 除HEADERS中定义的信息之外 ，还有请求和响应的【正文】及【元数据】
 * @author zrh
 *
 */
@Configuration
public class FeignConfig {
	
	@Bean//配置feign打印日志信息
	Logger.Level feignLoggerLevel(){
		return Logger.Level.FULL;
	}
}
