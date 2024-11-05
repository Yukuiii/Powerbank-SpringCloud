## Powerbank-SpringCloud开发日记

该项目为本人学习SpringCloud的开发笔记，可供学习者参考

微服务端口

| 服务名称 | 端口号 |
|----------|--------|
| Powerbank-user | 8081 |
| Powerbank-auth | 8000 |
| Powerbank-gateway | 8080 |
| Powerbank-monitor | 8001 |

### 一、创建父工程

1.创建普通的SpringBoot 3.3.5项目，此项目作为父工程，主要用于管理子工程依赖

首先建立父工程pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.3.5</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.Yukuii</groupId>
    <artifactId>SpringCloud</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>SpringCloud</name>
    <description>SpringCloud</description>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF8</project.reporting.outputEncoding>
        <java.version>21</java.version>
        <spring-cloud.version>2023.0.3</spring-cloud.version>
        <spring-cloud-alibaba.version>2023.0.1.2</spring-cloud-alibaba.version>
        <spring-boot.version>3.3.5</spring-boot.version>
        <mybatis-plus.version>3.5.9</mybatis-plus.version>
        <mysql-connector.version>8.0.33</mysql-connector.version>
        <knife4j.version>4.5.0</knife4j.version>
        <druid.version>1.2.21</druid.version>
        <hutool.version>5.8.32</hutool.version>
        <spring-boot-admin.version>3.3.4</spring-boot-admin.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <!--Spring Cloud 相关依赖-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!--Spring Cloud Alibaba 相关依赖-->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>${spring-cloud-alibaba.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!--数据库相关依赖-->
            <dependency>
                <groupId>com.mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>${mysql-connector.version}</version>
            </dependency>
            <dependency>
                <groupId>com.baomidou</groupId>
                <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
                <version>${mybatis-plus.version}</version>
            </dependency>
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid-spring-boot-starter</artifactId>
                <version>${druid.version}</version>
            </dependency>
            <!--Knife4j API文档生产工具-->
            <dependency>
                <groupId>com.github.xiaoymin</groupId>
                <artifactId>knife4j-gateway-spring-boot-starter</artifactId>
                <version>${knife4j.version}</version>
            </dependency>
            <dependency>
                <groupId>com.github.xiaoymin</groupId>
                <artifactId>knife4j-openapi3-jakarta-spring-boot-starter</artifactId>
                <version>${knife4j.version}</version>
            </dependency>
            <!--Hutool Java工具包-->
            <dependency>
                <groupId>cn.hutool</groupId>
                <artifactId>hutool-all</artifactId>
                <version>${hutool.version}</version>
            </dependency>
            <!--集成SpringBoot Admin监控-->
            <dependency>
                <groupId>de.codecentric</groupId>
                <artifactId>spring-boot-admin-starter-server</artifactId>
                <version>${spring-boot-admin.version}</version>
            </dependency>
            <dependency>
                <groupId>de.codecentric</groupId>
                <artifactId>spring-boot-admin-starter-client</artifactId>
                <version>${spring-boot-admin.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
    <build>
        <pluginManagement>
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                    <version>${spring-boot.version}</version>
                </plugin>
            </plugins>
        </pluginManagement>
        <plugins>
            <!--    解决Parameter Name Retention 问题
        SpringBoot 3.x 系列（包括 3.3.5）默认情况下并不会保留参数名称信息。在编译过程中，方法参数的实际名称会被丢弃，只保留类型信息。
        使用Swagger进行API文档生成需要配置
-->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <compilerArgs>
                        <arg>-parameters</arg>
                    </compilerArgs>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <!-- 配置阿里云镜像仓库 -->
    <repositories>
        <repository>
            <id>aliyunmaven</id>
            <name>aliyun</name>
            <url>https://maven.aliyun.com/repository/public</url>
        </repository>
        <repository>
            <id>central2</id>
            <name>central2</name>
            <url>https://repo1.maven.org/maven2/</url>
        </repository>
    </repositories>

</project>

```

这里小伙伴们配置完pom文件后猛刷新maven会发生爆红现象，怎么都不下载依赖。这是因为我们现在只是使用`dependencyManagement`进行依赖声明，并不会引入依赖，只是管理版本号

### 二、微服务划分

#### 公共微服务

我们先建立好公共微服务，比如Powerbank-common，Powerbank-monitor,Powerbank-gateway，Powerbank-auth等等公共服务



#### 统一认证授权服务 Powerbank-auth

一般有两种认证授权方案：

1、统一认证服务方案

将认证授权抽离出来为一个单独的微服务，网关中配置全局拦截器进行校验token

2、统一Gateway网关认证方案

集成sa-token在gateway网关配置全局过滤器进行鉴权操作

**创建子模块 Powerbank-auth**

> 注意是创建Java项目，不要创建SpringBoot项目

![image-20241101135120856](https://i0.hdslb.com/bfs/article/90e0c891658e767d8b188058f01c60792716473.png)

![image-20241101135326002](https://i0.hdslb.com/bfs/article/e23a094cc7806e3430db62a47887af9a2716473.png)

配置Powerbank-auth依赖文件pom.xml

```xml
```

