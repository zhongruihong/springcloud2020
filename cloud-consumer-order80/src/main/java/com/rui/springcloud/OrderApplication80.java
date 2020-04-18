package com.rui.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
@SpringBootApplication
@EnableEurekaClient
public class OrderApplication80 {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(OrderApplication80.class, args);
	}

}
