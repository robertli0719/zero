# Zero Java Framework 1.0-SNAPSHOT

<p>我想把我写过的可复用的Java代码整理出来，然后开放给其他写Java Web Application的小伙伴们。</p>
        <p>我的设计目标是要造一个开源项目，让所有想基于SSH框架搭建项目的人直接clone后开工做事，而避免每次不必要的重复配置工作。</p>

        <p>这套代码是基于SSH框架 Spring4 + Struts2 + Hibernate4</p>

        <p>程序会基于Maven自动下载所有需要的Jar包，只需要修改上自己的数据库链接信息就可以执行。</p>
        <p>使用者可以很方便的基于Netbeans或其他IDE将我系统中的包robertli.zero更换为其自身网站的相关包名，用全文查找的方式顺便修改所有配置文件中的robertli.zero到自己的包名。</p>

        <h1>使用指南</h1>
        <h3>使用步骤</h3>
        <ol>
            <li>clone到本地</li>
            <li>用Netbeans打开工程文件</li>
            <li>修改app.properties内的jdbc信息到自己的数据库</li>
            <li>修改app.properties内的email邮件服务器信息到自己的</li>
            <li>修改app.properties内的file_storage.basepath到自己新建立的一个空文件夹</li>
            <li>Clean and Build Project (Shift + F11)</li>
            <li>完成，一切正常后开始编写自己的业务逻辑</li>
        </ol>

        <h3>包的修改</h3>
        <ol>
            <li>利用NetBeans将所有的robertli.zero开头的包修改为你自己的包名</li>
            <li>全文检索robertli.zero，并替换为你自己的包名</li>
            <li>修改META-INF/context.xml 下的path到你的项目名称</li>
        </ol>

        <h1>编程指南</h1>

        <h3>添加新JSP页面</h3>
        <p>当想添加http://yourdomain.com/Xxx 页面时</p>
        <ol>
            <li>在robertli.zero.action包下创建XxxAction.java</li>
            <li>在WEB-INF/default下添加Xxx.jsp</li>
            <li>在XxxAction中添加业务逻辑</li>
            <li>在jsp中显示视图</li>
        </ol>

        <h3>添加JSON数据URL</h3>
        <p>当想让http://yourdomain.com/json/Yyy返回想要的数据时</p>
        <ol>
            <li>在robertli.zero.action包下创建YyyAction.java</li>
            <li>向YyyAction放入private的数据，并通过Netbeans自动生成好getter</li>
            <li>向YyyAction放入需要的若干function</li>
            <li>访问http://yourdomain.com/json/Yyy!funName 得到运行结果</li>
        </ol>

        <h1>其他功能项</h1>
        <h3>目前支持的小组件有：</h3>
        <ul>
            <li>邮件发送器 emailSender</li>
            <li>文件管理器 fileManager</li>
        </ul>

        <p>所有小组件全可通过Spring依赖注入到需要的地方</p>

        <h3>目前包括的小工具有</h3>
        <ul>
            <li>AES</li>
            <li>Luhn</li>
            <li>MD5</li>
        </ul>

        <p>我将于每周末抽空进行进一步优化和升级，也希望能有更多小伙伴们共同参与完善。</p>
        
        <p>联系邮件email: li.liufv@gmail.com</p>
        <p><a href="https://github.com/robertli0719/zero">Github：https://github.com/robertli0719/zero</a></p>
        wechat: robertli0719<br>
        2016-09-17<br>
        Robert Li<br>