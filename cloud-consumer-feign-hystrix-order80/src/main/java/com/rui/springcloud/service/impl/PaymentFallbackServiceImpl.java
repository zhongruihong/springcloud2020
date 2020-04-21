package com.rui.springcloud.service.impl;

import org.springframework.stereotype.Component;

import com.rui.springcloud.service.PaymentFeignHystrixService;

@Component
public class PaymentFallbackServiceImpl implements PaymentFeignHystrixService{

	@Override
	public String paymentInfo_OK(Long id) {
		return "PaymentFeignHystrixService fallback :paymentInfo_OK()远程调用出错";
	}

	@Override
	public String paymentInfo_TimeOut(Long id) {
		return "PaymentFeignHystrixService fallback :paymentInfo_TimeOut()远程调用出错";
	}

}
