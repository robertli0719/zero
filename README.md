# Zero Java Framework 1.0-SNAPSHOT

为了缩短创建一个新项目的搭建时间，我想把我写过的可复用的Java代码整理出来，然后开放给其他写Java Web Application的小伙伴们。

## 我的设计目标
* 让开发者将主要精力用于业务逻辑，而不是软件架构和功能实现
* 联合大家伙共同商议出公认最优化的Java SSH架构和编程约定
* 多项目共享同一软件架构和编程约定，实现代码复用

## 当前结构
* 这套代码是基于SSH框架 Spring4 + Struts2 + Hibernate4
* 基于Maven自动下载Jar包。
* 具体到实际项目的相关信息，集中存放在了配置文件之中，可供使用者修改
* 基于Spring的依赖注入，使得各方可以方面的更换实现

## 系统分层
### 表现层
* JSP/JSON 提供result
*  robertli.zero.action..*Action 映射URL
*  robertli.zero.struts2.* 提供各种Interceptor功能

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
 1. 由于我的设计目标是给约5人左右的小型开发团队提供轻量级框架，所以我没有引入DTO。因此Entity无法被完全封装在DAO之下，而是必须贯穿全局。这样可以降低用户需求变化时所带来的级联修改的成本。
 2. robertli.zero.model.* 中的model并不是指MVC中的model，不是指数据库entity，不是指view model，也不是指DTO，而是表示一组有结构关系的数据所构成的数据对象。有时候Service层的返回结果无法直接用Entity来表示，所以需要将Entity的计算结果转换为model再返回上去。由于一些查询结果需要通过MySQL事务的快照读来保证一致性，所以可以使用model对多个DAO查询结果进行封装。
 

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

### 目前支持的功能组件
* 用户注册
* 用户登陆
* 用户通过Google登陆
* 用户登出
* 用户找回密码（发邮件要求用户重设）
* 后台管理员登陆
* 后台管理员修改密码
* 后台Root管理员增删其他管理员
* 后台Root管理员挂起其他管理员
* 后台Root管理员重设其他管理员密码
* 结合事务回滚的文件存储服务

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

2016-09-27
Robert Li