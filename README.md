# springboot-dubbo-zookeeper-demo-master
dubbo+zookeeper+spring boot的搭建demo

本demo是基于springboot2.0+ 即（2.1.4）的基础上搭建的，分为三个模块 
api + provider(服务提供者模块)+consumer(消费者模块),
搭建前提：会分模块搭建，zookeeper已安装，jdk8已配置
搭建过程：
先新建maven一个项目，然后配置pom.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.1.4.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.wgu</groupId>
	<artifactId>demo</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>pom</packaging>

	<modules>
		<module>dubbo-api</module>
		<module>dubbo-consumer</module>
		<module>dubbo-provider</module>
	</modules>

	<properties>
		<java.version>1.8</java.version>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
		<curator-framework.version>4.0.1</curator-framework.version>
		<zookeeper.version>3.4.13</zookeeper.version>
		<dubbo.starter.version>0.2.0</dubbo.starter.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>com.alibaba.boot</groupId>
			<artifactId>dubbo-spring-boot-starter</artifactId>
			<version>${dubbo.starter.version}</version>
		</dependency>

		<dependency>
			<groupId>org.apache.curator</groupId>
			<artifactId>curator-framework</artifactId>
			<version>${curator-framework.version}</version>
		</dependency>

		<dependency>
			<groupId>org.apache.zookeeper</groupId>
			<artifactId>zookeeper</artifactId>
			<version>${zookeeper.version}</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>

```
再依次新建几个子模块
dubbo-api：其中pom.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>demo</artifactId>
        <groupId>com.wgu</groupId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>dubbo-api</artifactId>


</project>
```
dubbo-provider：服务提供者模块
pom.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<artifactId>demo</artifactId>
		<groupId>com.wgu</groupId>
		<version>0.0.1-SNAPSHOT</version>
	</parent>
	<groupId>com.wgu</groupId>
	<artifactId>dubbo-provider</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>dubbo-provider</name>
	<description>服务提供模块</description>

	<properties>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>com.wgu</groupId>
			<artifactId>dubbo-api</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>

```
配置文件 application.properties

```
spring.application.name = dubbo-provider
server.port = 9090

#指定当前服务/应用的名字（同样的服务名字相同，不要和别的服务同名）
dubbo.application.name = dubbo-provider

demo.service.version = 1.0.0

dubbo.protocol.name = dubbo
dubbo.protocol.port = 20880

#指定注册中心的位置
dubbo.registry.address = zookeeper://localhost:2181

#统一设置服务提供方的规则
dubbo.provider.timeout = 1000

```
最后一个模块，dubbo-consumer 消费者模块
pom.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<artifactId>demo</artifactId>
		<groupId>com.wgu</groupId>
		<version>0.0.1-SNAPSHOT</version>
	</parent>
	<groupId>com.wgu</groupId>
	<artifactId>dubbo-consumer</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>dubbo-consumer</name>
	<description>消费者模块</description>

	<properties>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>com.wgu</groupId>
			<artifactId>dubbo-api</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>

```
配置文件 application.properties

```
spring.application.name = dubbo-consumer
server.port = 9091

#指定当前服务/应用的名字（同样的服务名字相同，不要和别的服务同名）
dubbo.application.name = dubbo-consumer

demo.service.version = 1.0.0

dubbo.protocol.name = dubbo
dubbo.protocol.port = 20880

#指定注册中心的位置
dubbo.registry.address = zookeeper://localhost:2181

#统一设置服务提供方的规则
dubbo.consumer.timeout = 5000

```
启动类上加上注解：@EnableDubbo
OK，准备工作完成了，下面写一个测试用例
在api模块写一个service

```
package com.wgu.api.service;

/**
 *服务Api接口类
 * @Author: w
 * @Date: 2019/4/24 14:08
 * @Version 1.0
 */
public interface DemoService {
    String sayHello(String name);
}

```
再在服务提供者模块，写一个实现类

```
@Service(version = "${demo.service.version}")
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name + " this is dubbo-zookeeper-spring boot-demo";
    }
}

```
最后就是消费者调用了

```
package com.wgu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wgu.api.service.DemoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消费者控制层
 * @Author: w
 * @Date: 2019/4/24 14:21
 * @Version 1.0
 */
@RestController
public class DemoController {
    @Reference(version = "${demo.service.version}")
    private DemoService demoService;

    @RequestMapping("/sayHello/{name}")
    public String sayHello(@PathVariable("name") String name) {
        return demoService.sayHello(name);
    }
}

```

欢迎大家讨论，不足之处请指出。
