package com.rui.springcloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 前端返回通过对象，json封装体
 * 
 * @author zrh
 *
 */
@Data // set、get
@AllArgsConstructor // 全参构造
@NoArgsConstructor // 空参构造
public class CommonResult<T> {
	private Integer code;// 前端返回状态码
	private String message;// 前端返回提示信息
	private T data;// 前端返回数据体<泛型>，如Payment

	public CommonResult(Integer code, String message) {// 两参构造(重载全参)
		this(code, message, null);
	}
}
