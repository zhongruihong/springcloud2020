package com.rui.springcloud.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rui.springcloud.entity.Payment;
import com.rui.springcloud.mapper.PaymentMapper;

@Service
public class PaymentServiceImpl implements PaymentService{

	//@Autowired//@Autowired是spring的
	@Resource//@Resource是java自带
	private PaymentMapper mapper;
	@Override
	public int create(Payment p) {
		return mapper.create(p);
	}

	@Override
	public Payment getPaymentById(Long id) {
		return mapper.getPaymentById(id);
	}

}
