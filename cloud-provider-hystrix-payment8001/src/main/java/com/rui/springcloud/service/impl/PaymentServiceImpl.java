package com.rui.springcloud.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.rui.springcloud.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
	//模拟 正常访问
	@Override
	public String paymentInfo_OK(Integer id) {
		// hystrix处理用的是tomcat里面的线程池
		return "线程池：" + Thread.currentThread().getName() + "paymentInFfo_OK()" + id;
	}
	//模拟 请求超时
	@Override
	public String paymentInfo_TimeOut(Integer id) {
		try {
			int time = 4;//模拟耗时4秒
			TimeUnit.SECONDS.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "线程池：" + Thread.currentThread().getName() + "paymentInFfo_TimeOut()" + id;

	}

}
