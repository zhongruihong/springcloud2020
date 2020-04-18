package com.rui.springcloud.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.rui.springcloud.entity.Payment;

@Mapper // 推荐使用@Mapper，不使用@Repository
public interface PaymentMapper {
	
	int create(Payment p);

	Payment getPaymentById(@Param("id") Long id);
}
