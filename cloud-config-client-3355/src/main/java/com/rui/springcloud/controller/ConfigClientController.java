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
	//访问：  http://localhost:3355/testinfo
	//如果修改github上对应的配置文件，在不重启Config服务端3344和Config客户端3355的情况下：
	//通过访问3344，获得的配置文件内容是修改后的内容，而通过访问3355，获取的内容依然是修改前的内容（可以通过重启服务再次访问获取修改后的内容~）
	//
	@GetMapping("/testinfo")
	public String getTestInfo() {
		return testInfo;
	}
}
