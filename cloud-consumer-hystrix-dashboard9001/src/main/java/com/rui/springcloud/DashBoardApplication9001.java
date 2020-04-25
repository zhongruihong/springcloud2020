package com.rui.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard//开启hystrix仪表盘
//访问http://localhost:9001/hystrix输入要监控的地址，就可以使用豪猪进行监控了...

public class DashBoardApplication9001 {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(DashBoardApplication9001.class, args);
	}
}
