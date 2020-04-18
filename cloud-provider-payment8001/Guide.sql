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
/*删除表过程*/
CREATE OR REPLACE PROCEDURE PROC_DROPIFEXISTS(P_TABLE IN VARCHAR2) IS
	V_COUNT NUMBER(10);
BEGIN
	SELECT COUNT(*)
	INTO V_COUNT
	FROM USER_OBJECTS
	WHERE OBJECT_NAME = UPPER(P_TABLE);
	IF V_COUNT > 0 THEN
		EXECUTE IMMEDIATE 'drop table ' || P_TABLE || ' purge';
	END IF;
END;
/
/*调用删除表存储过程*/
BEGIN
PROC_DROPIFEXISTS('payment');
END;

CREATE TABLE payment(  
	id NUMBER(20) NOT NULL PRIMARY KEY,
	serial VARCHAR(200) DEFAULT ''
);  
COMMENT ON COLUMN payment.id IS '主键';
COMMENT ON COLUMN payment.serial IS '流水';
/*oracle写过程实现主键自增
 注意：oracle要把mapper.xml文件中insert属性里的useGeneratedKeys的属性设置为false才可以成功插入数据， 
 因为useGeneratedKeys 要求数据库本身具备主键自动增长的功能，mysql，sqlserver可以使用useGeneratedKeys =true 这功能，
 oracle不行
 */
/*（1）创建序列*/
CREATE SEQUENCE payment_seq
	INCREMENT BY 1 /*每次增加1个*/
	START WITH 1/*从1开始计数*/
	NOMAXVALUE /*不设置最大值*/
	NOCYCLE /*直累加，不循环*/
	NOCACHE /*不建立缓冲区*/
/
/*（2）创建触发器*/
CREATE OR REPLACE TRIGGER payment_trigger
	BEFORE INSERT ON payment/*表名*/
	FOR EACH ROW /*WHEN (new.id is null) 设置主键存在时，不触发触发器*/
BEGIN
	SELECT payment_seq.NEXTVAL INTO :NEW.id FROM DUAL;
END;
INSERT INTO payment (serial) VALUES (trunc(100+dbms_random.value * 900));
SELECT * FROM payment ;
-- 产生一个任意大小的随机数
select dbms_random.random from dual;
-- 产生一个100以内的随机数
select abs(mod(dbms_random.random,100)) from dual;
-- 产生一个100～1000之间的随机数
select trunc(100+dbms_random.value * 900) from dual;
-- 产生一个0～1之间的随机数
select dbms_random.value from dual;
-- 产生一个10～20之间的随机数
select dbms_random.value(10,20) from dual;
