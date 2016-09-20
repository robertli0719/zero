# Zero Java Framework 1.0-SNAPSHOT

为了缩短创建一个新项目的搭建时间，我想把我写过的可复用的Java代码整理出来，然后开放给其他写Java Web Application的小伙伴们。

##我的设计目标
* 让开发者将主要精力用于业务逻辑，而不是软件架构和功能实现
* 联合大家伙共同商议出公认最优化的Java SSH架构和编程约定
* 多项目共享同一软件架构和编程约定，实现代码复用

##当前结构
* 这套代码是基于SSH框架 Spring4 + Struts2 + Hibernate4
* 基于Maven自动下载Jar包。
* 具体到实际项目的相关信息，集中存放在了配置文件之中，可供使用者修改
* 基于Spring的依赖注入，使得各方可以方面的更换实现


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
当想添加 http://yourdomain.com/Xxx 页面时（Struts2 约定优于配置）

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

### 目前正在开发中的功能组件
* 用户注册、登陆、登出、找回密码（发邮件要求重设）

### 目前支持的小组件
* 邮件发送器 emailSender
* 文件管理器 fileManager
* URL抓取服务 WebService
* 随机字串生成器 RandomCodeCreater

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

2016-09-19
Robert Li