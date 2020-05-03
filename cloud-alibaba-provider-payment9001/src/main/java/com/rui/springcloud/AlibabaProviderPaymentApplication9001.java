package com.rui.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@SpringBootApplication
@EnableDiscoveryClient
//不再使用@Eureka...
public class AlibabaProviderPaymentApplication9001 {
	/*mainboot自动提示生成，前提是pom.xml已经引入boot相关*/
	public static void main(String[] args) throws Exception {
		SpringApplication.run(AlibabaProviderPaymentApplication9001.class, args);
	}
}
