package com.rui.springcloud.service;

import org.apache.ibatis.annotations.Param;

import com.rui.springcloud.entity.Payment;

public interface PaymentService {
	int create(Payment p);

	Payment getPaymentById(@Param("id") Long id);
}
