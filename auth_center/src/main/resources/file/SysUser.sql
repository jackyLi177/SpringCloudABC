-- auto Generated on 2020-11-30
-- DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user(
	id BIGINT (15) NOT NULL AUTO_INCREMENT COMMENT 'id',
	u_name VARCHAR (50) NOT NULL COMMENT '用户名',
	u_pass VARCHAR (50) NOT NULL COMMENT '用户密码',
	remark VARCHAR (50) COMMENT '备注',
	u_tel VARCHAR (50) NOT NULL COMMENT '用户电话',
	u_id_num VARCHAR (50) COMMENT '用户身份证号',
	PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'sys_user';
