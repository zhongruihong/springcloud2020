package com.rui.springcloud.service.impl;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import com.rui.springcloud.service.ImessageProvider;

import lombok.extern.slf4j.Slf4j;
//@Service   不再需要注解@Service....
@EnableBinding(Source.class)//定义消息生产者，推送消息。注意：Source.class是org.springframework.cloud.stream.messaging.Source
//消息提供方是Source.class
//消息消费方是Sink.class
@Slf4j
public class MessageProviderImpl implements ImessageProvider{

	@Resource
	private MessageChannel output;//消息发送通道！！ MessageChannel变量名只能是output??????否则项目启动都报错!!!!!!
	
	@Override
	public String send() {
		
		//绑定消息  用integration.support.MessageBuilder 
		String serial = UUID.randomUUID().toString();
		output.send(MessageBuilder.withPayload(serial).build());
		//通过通道output发送信息到rabbitmq
		log.info(serial);
		return null;
	}

}
