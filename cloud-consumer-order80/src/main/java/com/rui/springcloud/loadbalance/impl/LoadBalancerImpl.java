package com.rui.springcloud.loadbalance.impl;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import com.rui.springcloud.loadbalance.LoadBalancer;

/**
 * 手写负载均衡实现类
 * @author zrh
 *
 */
@Component//注入，要扫描到
public class LoadBalancerImpl implements LoadBalancer{
	//高并发的情况下，i++无法保证原子性，往往会出现问题，所以引入AtomicInteger类。
	private AtomicInteger atomicInteger = new AtomicInteger(0);

	public final int getAndIncreacement() {
		int current;
		int next;//next代表第几次访问
		do {// 自旋锁
			current = this.atomicInteger.get();//初始是0
			next = current >= 2147483647 ? 0 : current + 1;// 如果大于数Integer.MAX_VALUE，赋值0，重新计数，因为有可能上线后，服务一直不重启，current累加超过最大整型值
		} while (!this.atomicInteger.compareAndSet(current, next));// cas 期望值current，修改值（当前值）next，一直在这里自旋，直到取到相等，取反跳出循环！
		System.out.println("访问次数next:" + next);//第一次访问，current = 0，next = 1，跳出循环
		return next;
	}
	@Override
	public ServiceInstance getInstanceByLX(List<ServiceInstance> serviceInstances) {
		int index  = getAndIncreacement()%serviceInstances.size();//选出哪个实例
		return serviceInstances.get(index);
	}

}
