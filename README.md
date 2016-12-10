# Zero Java Framework 1.0-SNAPSHOT

为了让程序员们更轻松的创建项目，降低Java项目的开发门槛，我将可复用的Java代码整理出来，写成可通过Spring装配的组件，开放给所有写Java Web Application的小伙伴们。

## 我的设计目标
* 让开发者将主要精力用于业务逻辑，而不是软件架构和功能实现
* 联合众多小伙伴共同讨论和提升编程技术
* 多项目共享同一软件架构和编程约定，实现代码复用

## 最新动态
* 我计划彻底放弃JSP,改用React重造前端
* 前端的所有代码放置在了/webapp下
* 初次运行程序前，请先进入/webapp目录下，通过npm install按照需要的文件
* 建议同时开启netbeans和vs code，分别编写前后端内容。
* 开发时使用webpack --watch可以实现前端的保存后自动更新

## 当前结构
* 前端基于 React + Redux + Boostrap
* 前端基于 NPM 安装依赖包，基于webpack实现打包
* 后端基于 Spring Framework + Hibernate5.2
* 后端基于Maven自动下载Jar包。
* 通过将配置文件设置在框架之外，实现框架与具体应用项目的解耦
* 基于Spring的依赖注入，使框架中所有组件都可根据需求来更换实现

## 系统分层
### 表现层
* 前端完全采用webapp方式实现
* 通过SpringMVC为前端提供REST Service
*  robertli.zero.interceptor.* 提供各种Interceptor功能

### 业务逻辑层
* robertli.zero.service.* 提供各种服务的接口
* robertli.zero.service.impl.* 提供各种接口的具体实现
* robertli.zero.core.* 提供高复用性且与数据库结构无关的服务的接口
* robertli.zero.core.impl.*为core下的接口提供实现

### 数据访问层
* robertli.zero.dao.* 提供数据访问对象的DAO的接口通常与entity一一对应
* robertli.zero.dao.impl.* 提供DAO的具体实现

### 贯串对象
* robertli.zero.entity.* 实体类，与数据库表对应
* robertli.zero.model.* 一组有结构关系的数据所构成的数据对象

#### 简单解释
 1. 由于我的设计目标是给约5人左右的小型开发团队提供轻量级框架，所以我没有引入大量的DTO。因此Entity无法被完全封装在DAO之下，而是必须贯穿全局。这样可以降低用户需求变化时所带来的级联修改的成本。
 2. 当系统遇到一些不得不采用DTO的情况时，我们将局部重构系统来区分开Entity和DTO
 

## 使用指南

### 使用步骤

1. clone到本地
2. 打开MySQL创建数据库
3. 用Netbeans打开工程文件
4. 修改app.properties内的jdbc信息到自己的数据库
5. 修改app.properties内的email邮件服务器信息到自己的
6. 修改app.properties内的file_storage.basepath到自己新建立的一个空文件夹
7. Clean and Build Project (Shift + F11)
8. 完成，一切正常后开始编写自己的业务逻辑

### 数据库的创建
建库语句：
create database zero default character set utf8mb4 default collate utf8mb4_general_ci;
* 为了支持苹果系统中长度为4字节的utf8表情符号，我们将默认的字符集设置为utf8mb4
* 在使用了utf8mb4后，mysql的所有index的最大长度都不能超过190个字符
* 我们的设计原则是不通过mysqld修改数据库默认设置来实现所有需求
* 由于系统基于Hibernate Entity自动建表，因此无须执行额外的建表语句

### 包的修改
1. 利用NetBeans将所有的robertli.zero开头的包修改为你自己的包名
2. 全文检索robertli.zero，并替换为你自己的包名
3. 修改META-INF/context.xml 下的path到你的项目名称

## 编程指南
### 建立新页面
（文档尚未完成）

### 建立数据库entity
当ER图画好后，按如下方式快速建立数据库（纯Hibernate知识）

1. 在robertli.zero.entity包下创建实体类，类名和数据库表名对应
2. 在class前添加@Entity
3. 考虑到MySQL在windows上不支持大写表名称，添加@Table(name="your_table_name")以保障未来迁移数据库时的一致性
4. 添加private成员，每一行对应数据库一个列
5. 用Netbeans快捷键自动生成全部getter setter
6. 在用于primary key的字段的setter前添加@Id，必要时添加@GeneratedValue实现自增
7. 通过@ManyToOne设置外键关系，必要时对应添加@OneToMany
8. 必要时通过@Column对列的非空、唯一、默认值进行具体设置
9. 必要时通过@Index追加索引以优化性能
10. 在Test包中随便执行一个会读写数据库的Service，Hibernate会自动扫描entity包下所有class并自动创建好所有的数据库表。

## 其他功能项

### 目前支持的功能组件
* [用户管理系统](docs/UserManagement.md)
* 结合事务回滚的文件存储服务

### 目前支持的小组件
* 邮件发送器 emailSender
* 文件管理器 fileManager
* URL抓取服务 WebService
* 随机字串生成器 RandomCodeCreater
* 图片处理服务 ImageService

（所有小组件全可通过Spring依赖注入到需要的地方）

### 目前包括的小工具
* AES
* Luhn
* MD5
* 邮箱格式验证工具

## 其他分支项目
* 我还另外提供了一个采用传统的Struts2（不采用SpringMVC）的解决方案，适用于传统JSP开发者
* [Github: https://github.com/robertli0719/ZeroSSH](https://github.com/robertli0719/ZeroSSH)

我将于每天晚上抽个人时间对该项目做维护和升级，也希望能有更多的小伙伴们共同参与完善。


欢迎大家吐槽~！

* email: li.liufv@gmail.com
* wechat: robertli0719
* [Github: https://github.com/robertli0719/zero](https://github.com/robertli0719/zero)

2016-12-10
Robert Li