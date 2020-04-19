package com.rui.springcloud.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rui.springcloud.service.PaymentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PaymentController {
	@Resource
	private PaymentService paymentService;
	@Value("${server.port}")
	private String port;

	// http://localhost:8001/payment/hystrix/ok/5
	@GetMapping(value = "/payment/hystrix/ok/{id}")
	public String paymentInfo_OK(@PathVariable("id") Integer id) {
		String paymentInfo_OK = paymentService.paymentInfo_OK(id);
		log.info(paymentInfo_OK);
		return paymentInfo_OK;

	}

	// http://localhost:8001/payment/hystrix/timeout/5 正常访问
	// http://localhost:8001/payment/hystrix/timeout/10 正常访问
	@GetMapping(value = "/payment/hystrix/timeout/{id}")
	public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
		String paymentInfo_OK = paymentService.paymentInfo_TimeOut(id);
		log.info(paymentInfo_OK);
		return paymentInfo_OK;

	}
}
