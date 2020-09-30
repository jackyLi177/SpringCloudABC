- cloud_client:
    - service name:author
    - port: 20000 20002 
- cloud_client_2:
    - service name:book
    - port: 20001 20003
- cloud_server:
    - service name:jacky-spring-cloud-eureka
    - port: 10000
- fegindemo:
    - service name:service-feign
    - port:10002
-zuuldemo:
    - service name:zuul-gateway
    - port :10001

## nacos
 1. 根据安装包内的sql创建数据库表
 2. 修改配置文件数据库相关配置
 3. 修改cmd文件，启动方式为单机
 
 * ###依赖：
 
         <!-- https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-starter-alibaba-nacos-discovery -->
         <dependency>
             <groupId>com.alibaba.cloud</groupId>
             <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
             <version>2.2.3.RELEASE</version>
         </dependency>
 