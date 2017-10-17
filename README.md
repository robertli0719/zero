# Zero Java Framework Demo

* 这是一个Zero Framework的使用Demo
* 参见 [Github: https://github.com/robertli0719/zero-framework](https://github.com/robertli0719/zero-framework)
* 为了让程序员们更轻松的创建项目，降低Java项目的开发门槛，我将可复用的Java代码整理出来，写成可通过Spring装配的组件，开放给所有写Java Web Application的小伙伴们。

## 当前结构
* 前端基于 React + Redux + Boostrap
* 前端基于 NPM 安装依赖包，基于webpack实现打包
* 后端基于 Spring Framework + Hibernate5.2
* 后端基于Maven自动下载Jar包。
* 通过将配置文件设置在框架之外，实现框架与具体应用项目的解耦
* 基于Spring的依赖注入，使框架中所有组件都可根据需求来更换实现
* 前后端通过REST API解耦
* [点击查看API设计原则](docs/ApiDesign.md)

## 使用指南

### 使用步骤

1. clone https://github.com/robertli0719/zero-framework 到本地后Clean and Build Project (Shift + F11)
2. clone https://github.com/robertli0719/zero 到本地
3. 打开MySQL创建数据库
4. 用Netbeans打开工程文件
5. 修改app.properties内的jdbc信息到自己的数据库
6. 修改app.properties内的email邮件服务器信息到自己的
7. 修改app.properties内的file_storage.basepath到自己新建立的一个空文件夹
8. 打开spring.xml注释掉“<value>file:${ZERO_PROPERTIES}</value>”
9. 解开注释掉的行“<value>classpath:app.properties</value>”
10. Clean and Build Project (Shift + F11)
11. 完成，一切正常后开始编写自己的业务逻辑

### 数据库的创建
建库语句：
create database zero default character set utf8mb4 default collate utf8mb4_general_ci;
* 为了支持苹果系统中长度为4字节的utf8表情符号，我们将默认的字符集设置为utf8mb4
* 在使用了utf8mb4后，mysql的所有index的最大长度都不能超过190个字符
* 我们的设计原则是不通过mysqld修改数据库默认设置来实现所有需求
* 由于系统基于Hibernate Entity自动建表，因此无须执行额外的建表语句


## 编程指南
### 建立新页面
（文档尚未完成）

## 其他功能项

## 其他分支项目
* 我还另外提供了一个采用传统的Struts2（不采用SpringMVC）的解决方案，适用于传统JSP开发者
* [Github: https://github.com/robertli0719/ZeroSSH](https://github.com/robertli0719/ZeroSSH)

我将于每天晚上抽个人时间对该项目做维护和升级，也希望能有更多的小伙伴们共同参与完善。


欢迎大家吐槽~！

* email: li.liufv@gmail.com
* wechat: robertli0719
* [Github: https://github.com/robertli0719/zero](https://github.com/robertli0719/zero)

2017-10-16
Robert Li