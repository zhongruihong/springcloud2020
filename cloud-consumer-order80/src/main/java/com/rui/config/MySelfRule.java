package com.rui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RetryRule;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.WeightedResponseTimeRule;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
/**
 * 自定义Ribbon负载策略
 * 同时要在启动类上加上@RibbonClient(name="",configuration=)配置才起作用！
 * @author zrh
 *
 */
@Configuration
//注意：自定义配置类，不能放在@ComponentScan所扫描的当前包以及子包下，否则自定义的这个配置类就会被所有的Ribbon客户端 所共享，达不到特殊定制化的目的
//启动OrderApplication80.java中注解@SpingBootApplication中的@ComponentScan扫描不到本类(MyRule.java)，因为本类和启动类不在同一包下
public class MySelfRule {//注意，类名不要和下面方法名mySelf()一样
	//Ribbon自带的7种负载规则 
		/**
		 * for ribbon 7 kinds of algorithm: 
		 * BestAvailableRule  优先过滤由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个【并发最小】的服务
		 * AvailabilityFilteringRule 先过滤故障实例，再选择【并发较小】的实例
		 * WeightedResponseTimeRule  对轮询的扩展，响应【速度越快】的实例选择【权重越大】，越容易被选择
		 * RetryRule  先按照轮询策略获取服务，如果获取服务失败则在【指定赶时间】内会进行重试，获取可用服务
		 * RoundRobinRule 轮询 
		 * RandomRule 随机
		 * ZoneAvoidanceRule 默认规则 ，复合判断server所在区域的性能和server的可用性选择服务
		 */
		//IRule是Ribbon的核心组件
		@Bean // for ribbon also for feign: because feign has integrateed ribbon and eureka
		public IRule myRule() {
			  new RetryRule();
			  new BestAvailableRule();
			  new AvailabilityFilteringRule();
			  new WeightedResponseTimeRule();
			  new RoundRobinRule();
			  new ZoneAvoidanceRule();
			return  new RandomRule();
		}
}
