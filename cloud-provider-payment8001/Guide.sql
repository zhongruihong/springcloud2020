/*
 【Eclipse操作数据库】步骤
1、Eclipse设置数据库连接：
Window->Show View -> Data Source Explorer 
 控制台Database Connections右键->New->MySql|Oracle->设置连接name->Drivers右边*号：
 	Name/Type:选择driver版本，设置driver name
 	JAR List :Add JAR/ZIP，添加连接jar包：oracle的可以用oracle客户端的，mysql直接用本地maven仓库中的
 	Properties:
 		oracle:
 			Connection RUL: jdbc:oracle:thin:@localhost:1521:orcl
			Database Name:orcl
			Driver Class: oracle.jdbc.driver.OracleDriver
			Password:scott
			User ID:scott
		mysql:
			ConnectionRUL:jdbc:mysql://localhost:3306/db_springcloud
			Database Name:db_springcloud
			Driver Class:com.mysql.jdbc.Driver
			User ID:root
			Password:123456
next->Test Connection
2、Eclipse编写sql语句：
	上步新建的"连接name"右键->Open SQL Scrapbook->弹出 空白文本框，写sql另存为...
3、执行sql
	（1）上步文本空白处右键->Set Connection Info->Connection profile Name：新建或者选择已有，
	（2）sql文本空白处右键->Execute All【Ctrl+Alt+x】 | Execute Selected Text【Alt+x】 | Execute Current Text【Alt+s】
*/

/*【mysql】 在创建mysql表时，表名和字段名外面的符号 ` 不是单引号，而是英文输入法的反单引号，同键盘~(左上角)同一位置。*/
/*eclipse中字母大小写转换快捷键 ctrl+shift+x 转为大写 ctrl+shift+y 转为小写*/
/*建表*/
DROP TABLE IF EXISTS `payment`;
CREATE TABLE IF NOT EXISTS `payment`(
	`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`serial` VARCHAR(200) DEFAULT '',
	PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8
/*ENGINE 设置存储引擎，CHARSET 设置编码。*/
/*插入*/
INSERT INTO payment(id,serial) VALUES('1','TEST');
/*查看*/
SELECT * FROM PAYMENT;
/*【oracle】*/
DROP TABLE IF EXISTS 't_stu';
CREATE TABLE t_stu(  
	stuid      NUMBER(10)   PRIMARY KEY,  
	stuname    VARCHAR2(20) NOT NULL,  
	stusex     VARCHAR2(2)  DEFAULT '男' CHECK(stusex IN('男','女'))
);  
