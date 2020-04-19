package com.rui.springcloud.service;

public interface PaymentService {
	//模拟 正常访问
	String paymentInfo_OK(Integer id);
	//模拟 请求超时
	String paymentInfo_TimeOut(Integer id);
}
