package com.rui.springcloud.loadbalance;

import java.util.List;

import org.springframework.cloud.client.ServiceInstance;

/**
 * 手写负载均衡接口
 * @author zrh
 *
 */
public interface LoadBalancer {
	/**
	 * 传入所有服务实例集合，通过自定义负载均衡算法(轮询)->获取要调用的服务实例
	 */
	ServiceInstance getInstanceByLX(List<ServiceInstance> serviceInstances);
}
