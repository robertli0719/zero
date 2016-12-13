# Zero API设计原则


## 名词定义：
* 无状态(stateless) 从客户端到服务器的每个请求都必须包含理解请求所必需的信息。
* 幂等性(Idempotence)：一次和多次请求某资源时可具有同样副作用
* 安全操作：执行后不会改变服务器端状态，并不对外发送任何信息的操作


## 设计理念
* 将HTTP视为应用层协议而非传输层协议
* URI设计要面向资源而非面向功能
* URI设计要基于客户端需求而非数据库结构
* 设计规范统一，并且无局限性

## 设计规范
* Request应为无状态的，因此不可依赖于session id
* 仅采用GET, POST, PUT, DELETE 4个方法
* GET为安全操作，其它方法为不安全操作
* POST为非幂等性操作，其他方法为幂等性操作
* POST和PUT时都在request body中提供资源的完备信息
* 使用PathVariable来区分资源
* 在GET和DELETE上用Parameters来限制资源
* 只在POST和PUT上使用request body
* 只在GET上使用response body
* URI统一为小写字母，‘-’分割单词

## 限制性规范
* 永远基于SSL进行传输
* 资源名称统一为名词的复数形式
* 返回格式默认采用JSON
* 除GET外，其他的请求参数统一采用JSON

## 编程约定
* 一项资源对应一个URL
* 一项资源采用的方法（GET、POST、PUT、DELETE）都公用同一个DTO
* access_token包含在DTO外部
* 只有文件相关请求支持3XX状态码，并且不使用DTO。
* API请求的结果要么成功，要么失败，没有第三种情况
* 当API返回成功时，返回2XX作为状态码，内容是DTO
* 当API返回失败时，返回3XX、4XX、5XX作为状态码，内容采用统一错误消息格式

### 统一错误消息格式
{
	status:"status",
	errors:[{
		type:"",
		source:"",
		message:"",
		detail:""
	},...]
}

* status: 错误的状态名称
* errors：错误列表，里面包含很多错误
* type： 错误的类型
* source: 错误的出处，比如字段名
* message：显示给用户的简单描述
* detail：显示给开发者的详细描述

### 错误类型
* FIELD_ERROR：DTO上发生的数据验证错误
* GLOBAL_ERROR：全局错误
* RUNTIME_EXCEPTION： 由Service抛出的运行时错误

## 权限控制
由于REST request 应为无状态的，因此不能将用户的登陆状态绑定到session之中，因此API应通过access token来控制访问权限。用户在登录时需要post其用户名与密码来获得服务器端生成的access token。服务器验证用户身份后发回token给客户端，客户端在本地一个安全的地方保存这一token，并在之后的每次request中都包含这一token来表明身份。  

考虑到JS webapp的web storage无法防御XSS (使用webpack后很多第三方lab被打包入同一个js)，因此推荐将token以http-only的形式存储入cookie中，使前端JS无法触碰到token。  

## 4方法与数据库CRUD的映射关系
* GET方法用于读取资源
* POST方法用于创建新资源(id由服务器端指定)
* PUT方法用于修改资源，或创建那些id由客户端所创建的资源
* DELETE方法用于删除资源

（解释：POST不可用于修改资源，因为修改操作应该是幂等性操作。对于id由客户端所创建的资源，不应提供POST操作，而将创建与修改操作合并到PUT来执行saveOrUpdate）  


## URI设计范例：

例子 | 解释
--- | ---
GET /productions | 获取所有产品信息列表
GET /productions?limit=10&start=100&orderby=name | 带各种限制条件的查询
GET /productions/1234 | 获取某个特定产品的信息，基于产品ID
GET /productions/1234/snapshot | 获取id为1234这一产品的包含更多detail的复合资源
GET /productions/1234/venders | 获取id为1234这一产品的供应商信息
POST /productions | 增加一个新的产品，数据格式为JSON放在RequestBody里
POST /productions/1234 | 增加一个vender给产品1234，vender数据放在RequestBody里
PUT /productions/1234 | 增加或修改某产品信息，数据格式为JSON放在RequestBody里
DELETE /productions | 删除所有产品信息
DELETE /productions/1234 | 删除某产品信息

