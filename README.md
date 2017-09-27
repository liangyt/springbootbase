### spring-boot 的学习
spring-boot出来很长一段时间了，一直也没有时间去学习一下，正好这段时间项目没有那么忙了，学习记录一下。  

    由于东西感觉有些多，所以就分成几个分支分别记录了。
    整个学习过程以spring-boot-starter-thymeleaf为基础，在这个基础上再按照需要添加其它的组件.
#### learn.01
这个分支是比较基础的一些配置集成。
##### 一. mybatis-spring-boot-starter 和 druid
引入相关的依赖包
```$xslt
<!--mybatis mysql-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>${mybatis.springboot.version}</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
<!--datasource-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>${druid.version}</version>
</dependency>
```
spring-boot 的默认配置文件 application.properties 配件mysql 和 druid 相关的数据
```$xslt
# 数据库驱动配置信息
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
spring.datasource.url=jdbc:mysql://localhost/test?useUnicode=true&characterEncode=utf-8
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.jdbc.Driver

# druid连接池配置信息
# 初始化的大小、最小、最大
spring.druid.initial-size=5
spring.druid.min-idle=5
spring.druid.max-active=20
# 获取连接等待超时时间
spring.druid.max-wait=60000
# 间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
spring.druid.time-between-eviction-run-millis=60000
# 一个连接在池中最小生存的时间，单位是毫秒
spring.druid.min-evictable-idle-time-millis=30000
spring.druid.validation-query=select 1 from dual
spring.druid.test-while-idle=true
spring.druid.test-on-borrow=false
spring.druid.test-on-return=false
spring.druid.pool-prepared-statement-per-connection-size=20
# 监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
spring.druid.filters=stat,wall,log4j
# 通过connectProperties属性来打开mergeSql功能；慢SQL记录
spring.druid.connectionProperties=druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
```
要使用druid还需要做一些java配置的工作
具体看这个类:

    com.liangyt.config.db.DruidSource

里面有两个事情处理：
重新处理dataSource和事务  
还有另一个类也要关注一下：  

    com.liangyt.config.db.DruidConfig
这个类是针对druid本身servlet的处理，感觉如果不访问druid的客户界面的话不需要处理.

mybatis的配置:  
这个配置比较简单了，在application.properties文件中只有两行配置代码
```$xslt
# mybatis
mybatis.type-aliases-package=com.liangyt.entity
mybatis.mapper-locations=classpath:mybatis/**/*.xml,classpath:mybatis/*.xml
```
告诉mybatis需要处理实体的路径和需要扫描的mapper文件位置.  
同样在spring-boot启动的时候需要添加注解

    @MapperScan(basePackages = "com.liangyt.repository")
启动mapperScan扫描.  

##### 二. mybatis-generator 主要为了方便由数据库表到Entity、mapper和repository的生成.  

一个依赖
```$xslt
<dependency>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-maven-plugin</artifactId>
    <version>${mybatis.generator.version}</version>
</dependency>
```
一个插件
```$xslt
<!-- mvn mybatis-generator:generate -->
<plugin>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-maven-plugin</artifactId>
    <version>${mybatis.generator.version}</version>
    <configuration>
        <configurationFile>src/main/resources/generator/generatorConfig.xml</configurationFile>
        <overwrite>true</overwrite>
        <verbose>true</verbose>
    </configuration>
</plugin>
```
其中mybatis-generator的配置在文件(src/main/resources/generator/generatorConfig.xml)中已经有详细的说明，直接看就可以了。

持久层相关的处理基本上就是这些了。

##### 三. thymeleaf-layout-dialect
视图层使用的是spring-boot推荐的thymeleaf，由于以前习惯了使用sitemesh，所以这里也使用thymeleaf-layout-dialect配置类似于sitemesh的功能。  
由于spring-boot-starter-thymeleaf依赖已经包含了spring-layout-dialect，所以就不用重复添加依赖,直接配置就可以了。
说thymeleaf-layout-dialect之前先添加一个依赖吧,就是nekohtml。如果没有添加这个依赖的话，也行解释运行，但是对标签的解析很严格，稍微写错一点则编译错误，特别是引入前端库的话。  
引入也比较简单:
```$xslt
<!--加入这个主要是为了减少thymeleaf的严格检查,方便使用其它的前端库同时需要设置 spring.thymeleaf.mode=LEGACYHTML5-->
<dependency>
    <groupId>nekohtml</groupId>
    <artifactId>nekohtml</artifactId>
    <version>1.9.6.2</version>
</dependency>
```
同时在application.properties添加配置  

    spring.thymeleaf.mode=LEGACYHTML5
回到thymeleaf-layout-dialect上，使用它之前需要先配置一下LayoutDialect:
```$xslt
@Configuration
public class ThymeleafLayout{

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
```
配置一个Bean LayoutDialect,然后就可以使用了。
这里简单的配置了两个页面示意一下：
layout.html
```$xslt
<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/web/thymeleaf/layout">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>首页</title>
    <link rel="stylesheet" th:href="@{css/element-ui.css}" />
    <script th:src="@{js/vue.js}"></script>
    <script th:src="@{js/element-ui.js}"></script>
    <script th:src="@{js/axios.js}"></script>
</head>
<body>
test
<div layout:fragment="content"></div>
</body>
</html>
```
关注点主要有两个:  

    1.xmlns:layout="http://www.ultraq.net.nz/web/thymeleaf/layout"
    2.<div layout:fragment="content"></div>
这个布局文件一般是留一个位置用于存放动态变化的内容，也就是<code>layout:fragment="content"</code>这个Div的位置.

再来看看动态内容的文件：hello.html
```$xslt
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
    xmlns:layout="http://www.ultrag.net.nz/thymeleaf/layout"
    layout:decorator="layout/layout">
<head>
    <title>Hello</title>
    <link rel="stylesheet" th:href="@{css/hello.css}">
    <script th:src="@{js/hello.js}"></script>
</head>
<body>
<div layout:fragment="content">
    <span th:text="${hello}"></span>
</div>
</body>
</html>
```
这个文件的内容比较少关注的点有三个：

    1.html标签的内容
    2.head标签的内容
    3.layout:fragment="content"标签的内容
1.html标签的定义主要说明了这个文件需要使用的是哪一个布局文件。
2.head标签的内容会追加到布局文件的head标签的内容后面，并且title的内容会替换掉布局文件的title的内容.
3.<code>layout:fragment="content"</code>标签的内容的替换掉布局文件的<code><div layout:fragment="content"></div></code>的内容。

后面访问的时候，通过控制层直接访问动态内容的文件就可以了。

##### 四. 访问编译thymeleaf
如果是正常的通过controller查询内容，然后设置model，再然后跳转到页面的话，则Controller可以这样处理:
```$xslt
@Controller
@RequestMapping(value = "/")
public class TestController {

    @RequestMapping(value = "/hello")
    public String hello(Model model) {
        model.addAttribute("hello", "您好");
        return "hello";
    }
}
```
还有另外一种则是不通过Controller，直接跳转访问页面:  
这个需要实现<code>WebMvcConfigurerAdapter</code>类。 覆盖方法<code>addViewControllers</code>:
```$xslt
/**
 * 定义访问路径与视图之间的关系，不经过Controller
 * @param registry
 */
@Override
public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("index").setViewName("/index");
    super.addViewControllers(registry);
}
```
如果代码可以直接访问 /index 这个路径，即访问 /resource/templates/index.html页面。如果有其它的页面直接按类似的方法添加中进去就可以了，也可以使用表达式。

##### 五.web拦截器
拦截器的实现跟spring mvc 一样，都需要实现 <code>org.springframework.web.servlet.HandlerInterceptor</code>,这里简单的贴一下代码出来，仅供参考：
```$xslt
@Component
public class TestControllerInterceptor implements HandlerInterceptor {

    @Autowired
    private TestService testService;

    /**
     * 进入controller 之前的处理
     * @param request
     * @param response
     * @param handler
     * @return 返回 false 不进入controller 返回 true 进入controller
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());

//        if ("/api/test/all".equals(request.getRequestURI())) return false;
        return true;
    }

    /**
     * 进入controller 完成之后，但没有渲染页面之前处理，一般主要用于修改model的值
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        List<Test> list = testService.selectAll();
        System.out.println(list);
    }

    /**
     * 页面已渲染完成后的处理
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
```
主要是配置这个拦截器的地方，由原来的xml配置到java代码的配置.这个跟配置不通过Controller直接访问页面是同一个类，只是覆盖实现了另一个方法：
```$xslt
@Autowired
private TestControllerInterceptor testControllerInterceptor;
    
/**
 * 设置拦截器
 * @param registry
 */
@Override
public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(testControllerInterceptor).addPathPatterns("/api/test/**");
    super.addInterceptors(registry);
}
```
有一个需要注意的点是<code>TestControllerInterceptor</code>的实例需要由<code>@Autowired</code>注解生成，而不能由<code>new</code>生成，否则拦截器里面使用<code>@Autowired</code>可能得不到实例.

##### 六.hibernate validator
这个依赖也不需要单独引入，在spring-boot-starter-web下面已经有这个依赖了。  
要配置这个就比较简单了，在实体类里面使用注解的方法直接在属性处定义验证通过的条件，同时需要在<code>Controller</code>类里面使用注解<code>@Validated</code>说明该实体需要验证：
```$xslt
@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
public Object saveOrUpdate(@RequestBody @Validated Test test) {
    if (test.getId() != null) {
        testService.update(test);
    }
    else {
        test.setId((int)(Math.random() * 100));
        testService.insert(test);
    }
    return MessageReturn.success("成功");
}
```
具体使用方式网上的教程也不少,这里贴出注解:
```$xslt
注解         作用
@Valid	被注释的元素是一个对象，需要检查此对象的所有字段值
@Null	被注释的元素必须为 null
@NotNull	被注释的元素必须不为 null
@AssertTrue	被注释的元素必须为 true
@AssertFalse	被注释的元素必须为 false
@Min(value)	被注释的元素必须是一个数字，其值必须大于等于指定的最小值
@Max(value)	被注释的元素必须是一个数字，其值必须小于等于指定的最大值
@DecimalMin(value)	被注释的元素必须是一个数字，其值必须大于等于指定的最小值
@DecimalMax(value)	被注释的元素必须是一个数字，其值必须小于等于指定的最大值
@Size(max, min)	被注释的元素的大小必须在指定的范围内
@Digits (integer, fraction)	被注释的元素必须是一个数字，其值必须在可接受的范围内
@Past	被注释的元素必须是一个过去的日期
@Future	被注释的元素必须是一个将来的日期
@Pattern(value)	被注释的元素必须符合指定的正则表达式

2. Hibernate Validator 附加的 constraint

注解	         作用
@Email	被注释的元素必须是电子邮箱地址
@Length(min=, max=)	被注释的字符串的大小必须在指定的范围内
@NotEmpty	被注释的字符串的必须非空
@Range(min=, max=)	被注释的元素必须在合适的范围内
@NotBlank	被注释的字符串的必须非空
@URL(protocol=, host=,    port=,  regexp=, flags=)	被注释的字符串必须是一个有效的url
@CreditCardNumber 被注释的字符串必须通过Luhn校验算法， 银行卡，信用卡等号码一般都用Luhn 计算合法性
@ScriptAssert(lang=, script=, alias=)	要有Java Scripting API 即JSR 223 ("Scripting for the JavaTM Platform")的实现
@SafeHtml(whitelistType=, additionalTags=)	classpath中要有jsoup包

hibernate补充的注解中，最后3个不常用，可忽略。
主要区分下@NotNull  @NotEmpty  @NotBlank 3个注解的区别：
@NotNull           任何对象的value不能为null
@NotEmpty       集合对象的元素不为0，即集合不为空，也可以用于字符串不为null
@NotBlank        只能用于字符串不为null，并且字符串trim()以后length要大于0
```
在<code>com.liangyt.common.view.BaseController</code>基础控制类做了一个针对验证的全局拦截方法，统一处理返回的异常信息:
```$xslt
/**
     * 统一处理hibernate validator 不通过的数据  (BindException)
     *
     * @param ex 异常
     * @return 组装异常结果
     * {
     *     code: 0,
     *     message: "失败",
     *     data: {
     *         name: "姓名不能为空",
     *         ...
     *     }
     * }
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object validatorException(MethodArgumentNotValidException ex) {

        List<FieldError> errors = ex.getBindingResult().getFieldErrors();

        MessageReturn mr = MessageReturn.fail();
        for (FieldError fe : errors) {
            mr.add(fe.getField(), fe.getDefaultMessage());
        }

        return mr;
    }
```
* 第一个分支学习到这里全结束

#### learn.02
从这里开始是在分支<code>learn.01</code>的基础上进行的改进。
##### 一、logback
spring-boot 默认的日志插件就是logback,在spring-boot-starter下面默认依赖了
```$xslt
logback-classic
logback-core
slf4-api
jcl-over-slf4j
jul-to-slf4j
log4j-over-slf4j
```
spring-boot 也提供了一些参数用于设置日志，但相对于配置文件来说话稍微简单了一点，其中部分配置如下:
```$xslt
# 日志处理
# 默认情况下，spring boot从控制台打印出来的日志级别只有ERROR,WARN,INFO;如果希望放开DEBUG级别的话设置 debug=true;
debug=true
# 日志格式
logging.pattern.console=%-4relative [%thread] %-5level %logger{35} - %msg %n
logging.pattern.file=%-4relative [%thread] %-5level %logger{35} - %msg %n
# 针对不同的类或者包设置日志级别;root为父级别
logging.level.root=DEBUG
logging.level.com.liangyt=DEBUG
#logging.level.org.springframework.web=DEBUG
# 日志输出文件
logging.path=logs
logging.file=log.log
```
如果要求不是特别高的话，这个配置也基本够用了。当然这里也提供了一个引入日志配置文件的参数，不过我没有试过

    logging.config=logconfig.xml
除了使用application.properties配置以外，那就是直接配置logback.xml文件了。  
在logback.xml配置文件里面我做了详细的说明，这里就不再重复说了，看文件就知道每个标签是什么含义了。  
我试了一下把日志保存到mongodb里面，感觉还不错，配置也比较简单。
添加依赖:
```$xslt
<!--mongodb-->
<dependency>
    <groupId>org.mongodb</groupId>
    <artifactId>mongo-java-driver</artifactId>
    <version>3.4.2</version>
</dependency>
```
一个类和一部分日志配置：  
配置mongodb的连接配置，以及保存日志到数据库的一个类，使用class属性引入：
```$xslt
<!--日志保存到Mongondb-->
<appender name="MONGODB" class="com.liangyt.config.db.LogMongoDBAppender">
    <DbHost>localhost</DbHost>
    <DbPort>27017</DbPort>
    <DbName>exapp</DbName>
    <DbCollectionName>logging</DbCollectionName>
</appender>

<!--再配置一个logger-->

<logger name="com.liangyt" level="DEBUG">
    <appender-ref ref="MONGODB" />
</logger>
```
这样的话<code>com.liangyt</code>生成的日志就会进到这个类<code>com.liangyt.config.db.LogMongoDBAppender</code>,然后覆盖方法 <code>append</code>保存进数据库.

如果想让相关的日志信息保存到数据库中，则可以这样使用:
```$xslt
protected Logger mongodbLogger = LoggerFactory.getLogger("MONGODB");
mongodbLogger.info("保存到数据库中");
```

##### 二、添加AOP
因为<code>spring-boot-starter-thymeleaf</code>下已经有了 <code>spring-aop</code>,只是缺少了<code>org.aspectj</code>相关的两个包，所以只需要添加中两个依赖：
```$xslt
<!--aspectj-->
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjrt</artifactId>
    <version>${aspectj.version}</version>
</dependency>
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>${aspectj.version}</version>
</dependency>
```
就可以了。  
如果只是想单纯的玩玩aop的话，那可以起一个项目，引入 <code>spring-boot-starter-aop</code>,里面包含了spring-boot-starter 􏳪spring-aop 􏳪AspectJ Runtime 􏳪AspectJ Weaver 􏳪spring-core。  

利用Java注解的方式，只有一个类:

    com.liangyt.common.view.RestRequestMappingAspectInterceptor
 这个类详细说明了实现的细节。这里不再重复解说了，看这个类就知道了。  
 
 * 好了，第二个分支的内容就到这里了，下一个分支想把shiro集成进来，看有没有时间吧。
 
 #### learn.03
 这个分支没有写shiro,不过写了一些为集成shiro做准备的工作，把基本的用户管理，角色管理，功能管理，登录，退出工作处理了，还实现了简单的是否登录拦截跳转。如果不想集成shiro的话，
 也可以作为一个小系统工作了，这里简单说一下吧。  
 UI层面使用的是 [element ui](http://element.eleme.io/#/zh-CN)，使用的是最简单的方式集成进来的，直接引用相应的静态资源。
 ```$xslt
<link rel="stylesheet" href="../../static/css/element-ui.css" th:href="@{/css/element-ui.css}" />
<link rel="stylesheet" href="../../static/css/style.css" th:href="@{/css/style.css}">
<script src="../../static/js/jquery.3.0.min.js" th:src="@{/js/jquery.3.0.min.js}"></script>
<script src="../../static/js/vue.js" th:src="@{/js/vue.js}"></script>
<script src="../../static/js/element-ui.js" th:src="@{/js/element-ui.js}"></script>
<script src="../../static/js/axios.js" th:src="@{/js/axios.js}"></script>
```
以上这些就是所有的全局外部资源了。  
关于 [element ui](http://element.eleme.io/#/zh-CN) 的使用方法，需要的同学直接看它的官网文档就可以了。  
接着看一下数据结构，总共5个表：
```$xslt
用户表：

1	id	varchar	32	0	0		主键	0			0
0	username	varchar	20	0	0		员工编号			0
0	password	varchar	50	0	0		密码			0
0	name	varchar	50	0	0		员工名称			0
0	status	int	1	0	0	0	用户状态 0 正常	0	0	0

角色表：
1	id	varchar	32	0	0			0			0
0	rolename	varchar	100	0	0		角色名			0
0	status	int	1	0	0	0	角色状态	0	0	0
0	description	varchar	200	0	1		角色描述			0

功能表：
1	id	varchar	32	0	0		主键	0			0
0	permission_name	varchar	100	0	0		功能名称			0
0	permission_code	varchar	100	0	0		功能代码			0
0	status	int	1	0	0	0	功能状态	0	0	0
0	url	varchar	200	0	1					0
0	pid	varchar	32	0	0		父ID			0

用户跟角色关系表：
1	user_id	varchar	32	0	0		用户主键	0			0
2	role_id	varchar	32	0	0		角色主键	0			0

角色跟功能关系表：
1	permission_id	varchar	32	0	0		功能主键	0			0
2	role_id	varchar	32	0	0		角色主键	0			0
```
好了，这就是所有的表了，可以直接使用 mybatis-generator 生成 mapper entity reposity 到对应的包中(这里的代码示例已经生成，就不需要重新生成了)。  
```$xslt
├── entity
│   ├── system
│   │   ├── Permission.java
│   │   ├── PermissionRole.java
│   │   ├── Role.java
│   │   ├── User.java
│   │   └── UserRole.java
├── repository
│   ├── system
│   │   ├── PermissionMapper.java
│   │   ├── PermissionRoleMapper.java
│   │   ├── RoleMapper.java
│   │   └── UserMapper.java
├── rest
│   ├── system
│   │   ├── LoginController.java
│   │   ├── PermissionController.java
│   │   ├── RoleController.java
│   │   └── UserController.java
└── service
    ├── system
    │   ├── LoginService.java
    │   ├── PermissionService.java
    │   ├── RoleService.java
    │   └── UserService.java
```
这个是它们的代码结构，后续如果有其它的业务代码的话，直接按照这个结构添加就可以了。具体的代码写法也比较简单，就不一一展示了，直接看就行。
```$xslt
└── templates
    ├── layout
    │   ├── adminlayout.html
    ├── login.html
    └── system
        ├── demo.html
        ├── index.html
        ├── permission.html
        ├── role.html
        └── user.html
```
这个是视图层代码结构，也比较简单。一个布局页面，其它的是业务页面。
![image](https://github.com/liangyt/github-resource/blob/master/springbootbase-resource/login.png)
![image](https://github.com/liangyt/github-resource/blob/master/springbootbase-resource/user.png)
![image](https://github.com/liangyt/github-resource/blob/master/springbootbase-resource/role.png)
![image](https://github.com/liangyt/github-resource/blob/master/springbootbase-resource/permission.png)

在类 <code>com.liangyt.config.view.WebMvcViewController</code>设置了过滤：
```$xslt
/**
 * 设置拦截器
 * @param registry
 */
@Override
public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(testControllerInterceptor).addPathPatterns("/api/**");
    // 登录页和登录接口不拦截
    registry.addInterceptor(testControllerInterceptor).excludePathPatterns("/login").excludePathPatterns("/api/login");
    super.addInterceptors(registry);
}
```
在拦截器类 <code>com.liangyt.common.view.TestControllerInterceptor</code>处理拦截：
```$xslt
/**
 * 进入controller 之前的处理
 * @param request
 * @param response
 * @param handler
 * @return 返回 false 不进入controller 返回 true 进入controller
 * @throws Exception
 */
@Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    logger.info(request.getRequestURI());
    // 用户未登录，则跳转登录页先进行登录
    if (request.getSession().getAttribute("loginuser") == null) {
        response.sendRedirect("/login");
        return false;
    }
    return true;
}
```
好了，第三个分支内容就这么多了，直接下载安装依赖，数据库连接配置好，基本就可以跑起来看一下了。下一个分支，估计就是配置shiro了。

 #### learn.04
 隔了这么长的时间，由于项目比较忙，所以写得断断续续的。  
 这个分支那就把shiro集成进来了.  
 还是老规则，先添加依赖
 ```apple js
<!--shiro-spring  包含 shiro-core 和 shiro-web -->
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-spring</artifactId>
    <version>${shiro.version}</version>
</dependency>
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-ehcache</artifactId>
    <version>${shiro.version}</version>
</dependency>
```
依赖不多就两个，添加依赖之后那就开始配置本次的shiro。由于代码基本都添加了注释，所以跟代码相关的就不多说了吧，直接更新看代码就行。  
主要的配置类<code>com.liangyt.config.shiro.ShirlConfig</code>就是这个，springboot启动的时候会去配置相关的设置，配置的先后顺序可以看日志就知道了，每个方法都添加了执行日志。
这里也启用了shiro缓存，使用的是比较简单的ehcache本地缓存.本来是想使用redis缓存的，感觉redis可以作为一个比较大的处理方式，后面再集成进来吧。
配置shiroFilter的时候，参数有一个<code>ShiroService</code>,这个服务是用来读取跟shiro相关数据的类。
比如启动过程中就需要配置过滤的权限角色等数据，就是通过这个服务读取的。这样的话在这个服务中引用这个配置类<code>ShirlConfig</code>中某些Bean的时候就需要注意一下，别出来循环引用了。
<code>ShiroService</code>还可以处理一些缓存相关的操作，比如更新用户角色，更新角色限权时，就需要删除缓存，甚至更新filterShiro的过滤链（<code>com.liangyt.common.service.DynamicShiroService</code>类）。
* 这种做法有一个问题，只能针对单服务应用有效，集群的时候需要另外考虑方式方法了.

配置了shiro之后，原来的拦截器可以注释掉了。使用shiro的验证就可以了，未登录用户直接跳转登录页.

重新写了一个角色验证器<code>com.liangyt.config.shiro.filter.AnyRolesAuthorizationFilter</code>，因为每个用户可以配置多个角色，每个功能权限可能被多个角色选用,所以用户只需要满足其中的一个角色就可以通过验证。

_这部分有参考网上的一个网友的例子,忘记了出处_