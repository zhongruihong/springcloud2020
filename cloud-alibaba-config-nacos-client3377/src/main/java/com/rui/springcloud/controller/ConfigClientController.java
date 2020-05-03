package com.rui.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope//支持Nacos的动态自动刷新配置~~~
public class ConfigClientController {

	@Value("${test.info}")//前提是nacos上有项目yml中配置的配置文件，并在nacos上对应的配置文件中要配置这个字段 ！否则启动报错
	//本项目yml上述配置，在nacos上对应的【Data ID】，即文件名为: nacos-config-client-dev.yaml  注意：后缀名是.yaml
	private String testInfo;
	// http://localhost:3377/test/info  
	@GetMapping("/test/info")
	public String getConfigInfo() {
		return testInfo;
	}
}
