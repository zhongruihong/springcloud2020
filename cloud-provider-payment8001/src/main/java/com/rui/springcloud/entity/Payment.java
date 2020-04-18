package com.rui.springcloud.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 主实体  
 * @author zrh
 */
/*Eclipse lombok插件安装：
  下载lombok.jar
   在其所有文件夹执行java -jar .\lombok.jar
 Specify location选择Eclipse安装位置eclipse.exe->Instanll/Update->Success!
 更新maven,重启
 */
@Data//set、get
@AllArgsConstructor//全参构造
@NoArgsConstructor//空参构造
//@Accessors(chain=true）注解的英文意思链式访问，加上这个注解就是可以user.setId(1).setName("zhangsan).这样访问属性
public class Payment implements Serializable{
	private static final long serialVersionUID = -665059598337031867L;
	private Long id;//Long对应mysql类型bigint
	private String serial;
}
