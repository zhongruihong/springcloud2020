package com.rui.springcloud.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rui.springcloud.service.ImessageProvider;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j//打日志  控制台要打印彩色日志，在eclipse market搜索安装ansi
public class SendMessageController {
	@Resource
	private ImessageProvider provider;
	//http://localhost:8801/sendmessage
	@GetMapping(value="/sendmessage")
	public String sendMessage() {
		log.info("==controller==");
		return provider.send();
	}
	
	//启动顺序：RabbitMQ环境(cmd：Rabbitmq-server)->注册中心->provider
	//启动provider8801报错：An unexpected connection driver error occured (Exception message: Connection reset)
	//java.net.SocketException: Socket Closed
	//检查配置的用户名、密码、虚拟空间，都正确的话，去rabbitmq检查该用户在指定空间下的权限，是否有读写和配置权限...
	
	//上述都启动成功后，登陆http://localhost:15672 在Exchanges导航中可以看到本项目配置的自定义的通道名名称"myExchange"，，，好像没有myExchange，只用output
	//访问http:localhost:8081/sendmessage ,可以在rabbit管理界面的overview导航和Exchanges导航对应的output中，看到监测的波峰图...
}
