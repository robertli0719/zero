# Zero Java Framework 1.0-SNAPSHOT

我想把我写过的可复用的Java代码整理出来，然后开放给其他写Java Web Application的小伙伴们。

我的设计目标是要造一个开源项目，让所有想基于SSH框架搭建项目的人直接clone后开工做事，而避免每次不必要的重复配置工作。

这套代码是基于SSH框架 Spring4 + Struts2 + Hibernate4

程序会基于Maven自动下载所有需要的Jar包，只需要修改上自己的数据库链接信息就可以执行。
使用者可以很方便的基于Netbeans或其他IDE将我系统中的包robertli.zero更换为其自身网站的相关包名，用全文查找的方式顺便修改所有配置文件中的robertli.zero到自己的包名。

## 使用指南

###使用步骤
1. clone到本地
2. 用Netbeans打开工程文件
3. 修改app.properties内的jdbc信息到自己的数据库
4. 修改app.properties内的email邮件服务器信息到自己的
5. 修改app.properties内的file_storage.basepath到自己新建立的一个空文件夹
6. Clean and Build Project (Shift + F11)
7. 完成，一切正常后开始编写自己的业务逻辑

### 包的修改
1. 利用NetBeans将所有的robertli.zero开头的包修改为你自己的包名
2. 全文检索robertli.zero，并替换为你自己的包名
3. 修改META-INF/context.xml 下的path到你的项目名称

## 编程指南
### 添加新JSP页面
当想添加 http://yourdomain.com/Xxx 页面时

1. 在robertli.zero.action包下创建XxxAction.java
2. 在WEB-INF/default下添加Xxx.jsp
3. 在XxxAction中添加业务逻辑
4. 在jsp中编辑视图

### 添加JSON数据URL
当想让http://yourdomain.com/json/Yyy返回想要的数据时

1. 在robertli.zero.action包下创建YyyAction.java
2. 向YyyAction放入private的数据，并通过Netbeans自动生成好getter
3. 向YyyAction放入需要的若干function
4. 访问http://yourdomain.com/json/Yyy!funName 得到运行结果

## 其他功能项
### 目前支持的小组件
* 邮件发送器 emailSender
* 文件管理器 fileManager

（所有小组件全可通过Spring依赖注入到需要的地方）

### 目前包括的小工具
* AES
* Luhn
* MD5

我将于每周末抽空进行进一步优化和升级，也希望能有更多小伙伴们共同参与完善。

欢迎大家吐槽~！

email: li.liufv@gmail.com
wechat: robertli0719
[Github: https://github.com/robertli0719/zero](https://github.com/robertli0719/zero)

2016-09-17
Robert Li