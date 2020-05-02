package com.rui.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope//配置文件自动刷新，实现配置、实例热加载
public class ConfigClientController {
	@Value("${test.info}")//配置信息以Rest风格显露  从3344配置中心读取config.info的内容！
	//注意:本项目bootstrap.yml中配置的配置中心地址中访问路径下的配置文件中（http://localhsot:3344/master/config2020-dev.yml）
	//一定要有test:info:的信息，不然启动3355客户端会报错，因为通过3344配置中心找不到这个字段的信息
	//比如将bootstrap.yml中的label改为dev,由于dev分支上没有对应的配置文件，更没有test.info的内容。
	//所以启动就报错：Could not resolve placeholder 'test.info' in value "${test.info}"
	private String testInfo;
	
	@Value("${server.port}")
	private String serverPort;
	//访问：  http://localhost:3366/testinfo
	//如果修改github上对应的配置文件，在不重启Config服务端3344和Config客户端3355的情况下：
	//通过访问3344，获得的配置文件内容是修改后的内容，而通过访问3355，获取的内容依然是修改前的内容（可以通过重启服务再次访问获取修改后的内容~）
	//
	@GetMapping("/testinfo")
	public String getTestInfo() {
		return "serverPort:"+serverPort+"\ttestInfo:"+testInfo;
	}
	/**
	 * 1、先具备RabbitMQ环境
	 * 2、演示广播效果，增加复杂度，以3355为模板新建3366
	 * 设计思想：
	 * 	（1）小道通知：利用消息总线触发一个【客户端】/bus/refresh,而刷新所有【客户端】配置，就像传染一样，一传多
	 * 		这种方式违背了微服务各司其职（职责单一性）的理念，因为微服务本身是业务模块，本不应该承担配置刷新的职责....
	 * 		打破了微服务各节点的对等性
	 * 		有一定的局限性，例如微服务正在迁移，它的网络地址常常发生变化 ，此时如果想要做到时自动刷新 ，会增加更多的修改
	 * 	（2）总控通知：利用消息总线触发一个【服务端】/bus/refresh端点,而刷新所有【客户端】配置，分发
	 *  技术选型采用（2）...
	 * 给3344配置中心服务端、3355、3366客户端添加消息总线支持
	 * 
	 *
	 *
	 */
}
