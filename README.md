# SpringBootVue
## 1.SpringBoot基础配置

### 1.不使用spring-boot-starter-parent 

pom.xml

```
<properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    </properties>

    <!--不使用spring-boot-starter-parent-->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>2.0.4.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.1</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
```

### 2.注解@SpringBootApplication组成

- @SpringBootConfiguration

  就是一个@Configuration ，@SpringBootConfiguration表明是一个配置类，类似applicationContext.xml

- @EnableAutoConfiguration

  表示开启自动化配置

- @ComponentScan

  开启包扫描，扫描的类位于当前类扫描的包下



- 代码: **[01SpringBootProperties](./01SpringBootProperties)**

## 2.SpringBoot web

### 1.自定义转换器 Gson,google的开源解析json框架

- maven排除jackson的包，引入gson的包
- 默认提供了GsonHttpMessageConverter，如果要自定义时间格式或者字段序列化方式，提供一个GsonHttpMessageConverter，默认的会根据@ConditionalOnMissingBean失效

```
<!--引入web包后 默认加入jackson-databind作为json处理器-->
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <!--使用Gson先排掉jackson的包-->
            <exclusions>
                <exclusion>
                    <artifactId>jackson-databind</artifactId>
                    <groupId>com.fasterxml.jackson.core</groupId>
                </exclusion>
            </exclusions>
        </dependency>
        <!--引入gson包-->
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
        </dependency>
        <!--引入fastjson包-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.75</version>
        </dependency>
    </dependencies>
```

```
@Bean
    public GsonHttpMessageConverter converter(){
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        GsonBuilder builder = new GsonBuilder();
        //>> TODO gson日期格式化.
        builder.setDateFormat("yyyy-MM-dd");
        //>> TODO protected类型的不被序列化.
        builder.excludeFieldsWithModifiers(Modifier.PROTECTED);
        Gson gson = builder.create();
        converter.setGson(gson);
        return converter;
    }
```

### 2.fastjson

- 排除jackson的包，引入fastjson的包
- 提供FastJsonHttpMessageConverter 自定义converter Bean
- 添加编码配置

```java
@Bean
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        //>> TODO 创建FastJsonHttpMessageConverter.
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        //>> TODO 创建配置FastJsonConfig.
        FastJsonConfig config = new FastJsonConfig();
        config.setDateFormat("yyyy-MM-dd");
        config.setCharset(StandardCharsets.UTF_8);
        config.setSerializerFeatures(
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty);
        //>> TODO 设置FastJsonConfig.
        converter.setFastJsonConfig(config);
        return converter;
    }
```

```yaml
spring:
  http:
    encoding:
      force-response: true
```

### 3.fastjson第二种配置方式

使用WebMvcConfigurer，将fastjsonconverter添加到converters中

```java
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //>> TODO 创建FastJsonHttpMessageConverter.
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        //>> TODO 创建配置FastJsonConfig.
        FastJsonConfig config = new FastJsonConfig();
        config.setDateFormat("yyyy-MM-dd");
        config.setCharset(StandardCharsets.UTF_8);
        config.setSerializerFeatures(
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty);
        //>> TODO 设置FastJsonConfig.
        converter.setFastJsonConfig(config);
        converters.add(converter);
    }
}
```

### 4.SpringBoot中使用xml配置

```java
@Configuration
//>> TODO 导入xml bean的配置.
@ImportResource("classpath:beans.xml")
public class HelloConfiguartion {

}

@Autowired
    private Hello hello;

    @Bean
    public ApplicationRunner runner(){
        return args -> {
            System.out.println(hello.getName());
        };
    }
```

### 5.SpringBoot中使用拦截器

- 实现HandlerInterceptor接口
  - preHandle 返回true后面方法才执行
  - postHandle
  - afterCompletion

- 实现WebMvcConfigurer的addInterceptors添加拦截器

```java
public class MyInteceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(">>prehandler");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println(">>postHandler");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println(">>afterCompletion");
    }
}

@Configuration
public class WebMvnConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInteceptor())
                .addPathPatterns("/**");
    }
}
```

### 6.SpringBoot 中的Runner

```java
@Bean
    CommandLineRunner lineRunner(){
        //>> TODO String... args.
        return args -> {
            System.out.println("lineRunner");
        };
    }

    @Bean
    ApplicationRunner applicationRunner(){
        //>> TODO ApplicationArguments args.
        return args -> {
            System.out.println("applicationRunner");
        };
    }
```

### 7.整合Servlet，Filter和Listener

- 注解@WebServlet, @WebFilter, @WebListener
- 启动类添加注解@ServletComponentScan

```java
public class Web3Support {
    //>> TODO 这种方式配置必须是静态内部类.
    @WebServlet("/my")
    static class MyServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            System.out.println(req.getParameter("name"));
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            doGet(req, resp);
        }
    }

    @WebFilter("/*")
    static class MyFilter implements Filter {

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            System.out.println("init");
        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            System.out.println("doFilter");
            filterChain.doFilter(servletRequest, servletResponse);
        }

        @Override
        public void destroy() {
            System.out.println("destory");
        }
    }

    @WebListener
    static class MyListener implements ServletRequestListener{

        @Override
        public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
            System.out.println("destroy");
        }

        @Override
        public void requestInitialized(ServletRequestEvent servletRequestEvent) {
            System.out.println("event");
        }
    }
}
```

### 8.aop

- 引用aop依赖

- 配置Aspect,添加@Aspect注解和@Component注解

```xml
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>
```

```java
@Aspect
@Component
public class LogAspect {
    //>> TODO 三个*的意思 任意返回值，任意类，任意方法 ..代表任意参数 .
    @Pointcut("execution(* org.vijayian.service.*.*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before(JoinPoint jp) {
        System.out.println("before 执行...");
    }

    @After("pointcut()")
    public void after(JoinPoint jp) {
        System.out.println("after 执行...");
    }

    @AfterReturning(value = "pointcut()", returning = "result")
    public void afterReturn(JoinPoint jp, Object result) {
        System.out.println("afterReturn 执行...");
        System.out.println("返回值为：" + result);
    }

    @AfterThrowing(value = "pointcut()", throwing = "e")
    public void afterThrow(JoinPoint jp, Exception e) {
        System.out.println("afterThrow 执行...");
        System.out.println(e.getMessage());
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("around 执行...");
        return jp.proceed();
    }
}
```

- 代码：**[02SpringBootWeb](./02SpringBootWeb)**

## 3.SpringBoot整合持久化技术

### 1.SpringBoot整合JdbcTemplate

1. 引入pom

```xml
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.9</version>
        </dependency>
    </dependencies>
```

2. JdbcTemplate自动配置

```
org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration
```

3. yml配置

```yaml
spring:
  datasource:
    username: root
    type: com.alibaba.druid.pool.DruidDataSource
    password: root
    url: jdbc:mysql:///tb_master
```

4. JdbcTemplate使用

```java
/**
     * 增删改 主要使用：update和batchUpdate
     * 查询：query和queryForObject
     * execute 执行任意的sql
     *
     * 执行查询时：需要将列和实体进行对应，如果一致，可以直接使用BeanPropertyRowMapper，
     `不同需要自己实现RowMapper接口
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void addUser(User user) {
        jdbcTemplate.update("insert into t_user(username,dbsource) values (?,?)", user.getUsername(), user.getDbsource());
    }

    public void updateUser(User user) {
        jdbcTemplate.update("update t_user set username = ?,dbsource = ? where id = ?",
                user.getUsername(), user.getDbsource(), user.getId());
    }

    public User getUserById(Integer id) {
        return jdbcTemplate.queryForObject("select * from t_user where id = ?", new BeanPropertyRowMapper<>(User.class), id);

    }

    public void deleteUserById(Integer id) {
        jdbcTemplate.update("delete from t_user where id = ?", id);
    }

    public List<User> allUserLists() {
        return jdbcTemplate.query("select * from t_user", new BeanPropertyRowMapper<>(User.class));
    }
```

### 2.SpringBoot整合mybatis

1. pom依赖

```
<dependencies>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.3.2</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.9</version>
        </dependency>
    </dependencies>

```

2. yml配置文件

- 配置实体类别名，可以简化mapper.xml的配置，实体可以直接写实体名称，不用包含全路径名

```yaml
spring:
  datasource:
    username: root
    type: com.alibaba.druid.pool.DruidDataSource
    password: root
    url: jdbc:mysql:///tb_master

mybatis:
  #mapper xml配置文件
  mapper-locations: classpath:mapper/*Mapper.xml
  #实体类别名
  type-aliases-package: org.vijayian.entity
```

3. xml配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="org.vijayian.mapper.UserMapper">

    <!--int addUser(User user);

    int deleteUserById(Integer id);

    int updateUser(User user);

    User findUserById(Integer id);

    List<User> findAll();-->

    <insert id="addUser" parameterType="org.vijayian.entity.User">
        insert into t_user(username,dbsource) values (#{username},#{dbsource})
    </insert>

    <delete id="deleteUserById" parameterType="int">
        delete from t_user where id = #{id}
    </delete>

    <update id="updateUser" parameterType="org.vijayian.entity.User">
        update t_user set username = #{username},dbsource = #{dbsource} where id = #{id}
    </update>

    <select id="findUserById" resultType="User">
        select * from t_user where id = #{id}
    </select>

    <select id="findAll" resultType="org.vijayian.entity.User">
        select * from t_user
    </select>

</mapper>
```

4. mapper接口

```java
//>> TODO 方式1：在mapper接口上使用@Mapper指定接口是一个mybatis的mapper.
@Mapper
public interface UserMapper {
    int addUser(User user);

    int deleteUserById(Integer id);

    int updateUser(User user);

    User findUserById(Integer id);

    List<User> findAll();
}
```

5. 扫描mapper接口

- 在mapper接口使用@Mapper
- 在启动类配置mapper包扫描@MapperScan

```java
@SpringBootApplication
//>> TODO 方式二：使用@MapperScan扫描指定mapper的包，这样就不用在每个类上加@Mapper了.
//@MapperScan(basePackages = "org.vijayian.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
```

### 3.SpringBoot整合SpringData JPA

1. pom

```xml
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.9</version>
        </dependency>
    </dependencies>

```

2. yml

```yaml
spring:
  datasource:
    username: root
    type: com.alibaba.druid.pool.DruidDataSource
    password: root
    url: jdbc:mysql:///tb_master
  jpa:
    show-sql: true
    database: mysql
    hibernate:
      ddl-auto: none
    #数据库方言
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL57Dialect

```

3. repository

```java
public interface UserRepository extends JpaRepository<User, Integer> {

}
```

4. 操作

```
	@Autowired
    UserRepository userRepository;

    @Bean
    ApplicationRunner applicationRunner(){
        return args -> {
            User user = new User();
            user.setUsername("jpa");
            user.setDbsource("system");
            userRepository.save(user);

            userRepository.deleteById(1);

            User u = userRepository.findById(2).orElseThrow(NullPointerException::new);
            u.setUsername("jpa update");
            userRepository.save(u);
            System.out.println(u);

            List<User> all = userRepository.findAll();
            System.out.println(all);
        };
    }
```

### 4.多数据源配置

#### 1.jdbcTemplate多数据源配置

1. pom

```xml
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.10</version>
        </dependency>
    </dependencies>
```

2. 配置多数据源

```java
@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.one")
    DataSource dataSourceOne(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.two")
    DataSource dataSourceTwo(){
        return DruidDataSourceBuilder.create().build();
    }
}
```

3. 配置多jdbcTemplate

```java
@Configuration
public class JdbcTemplateConfig {
    @Bean
    JdbcTemplate jdbcTemplateOne(DataSource dataSourceOne) {
        return new JdbcTemplate(dataSourceOne);
    }

    @Bean
    JdbcTemplate jdbcTemplateTwo(DataSource dataSourceTwo) {
        return new JdbcTemplate(dataSourceTwo);
    }

}
```

4. use

```java
@Autowired
    JdbcTemplate jdbcTemplateOne;

    @Autowired
    JdbcTemplate jdbcTemplateTwo;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            jdbcTemplateOne.update("insert into t_user(username,dbsource) values (?,?)", "one", "one");
            jdbcTemplateTwo.update("insert into t_user(username,dbsource) values (?,?)", "two", "two");
        };
    }
```

#### 2.mybatis多数据源

1. pom文件

```xml
<dependencies>
        <!--mybatis多数据源-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.3.2</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.10</version>
        </dependency>
    </dependencies>
```

2. mybatis config

需要手工配置xml的路径，自定义了sqlsessionFactory，yml中的配置是不生效的

```java
@Configuration
public class MybatisConfig {

    @Configuration
    @MapperScan(value = "org.vijayian.mapper1", sqlSessionFactoryRef = "sqlSessionFactoryBean1",
            sqlSessionTemplateRef = "sqlSessionTemplate1")
    static class MybatisConfigOne {
        @Autowired
        DataSource dataSourceOne;

        @Bean
        SqlSessionFactory sqlSessionFactoryBean1() throws Exception {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(dataSourceOne);
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper1/*Mapper*.xml"));
            return bean.getObject();
        }

        //>> TODO 线程安全类，用来管理Mybatis的SqlSession操作.
        @Bean
        SqlSessionTemplate sqlSessionTemplate1() throws Exception {
            return new SqlSessionTemplate(sqlSessionFactoryBean1());
        }
    }

    @Configuration
    @MapperScan(value = "org.vijayian.mapper2", sqlSessionFactoryRef = "sqlSessionFactoryBean2",
            sqlSessionTemplateRef = "sqlSessionTemplate2")
    static class MybatisConfigTwo {
        @Autowired
        DataSource dataSourceTwo;

        @Bean
        SqlSessionFactory sqlSessionFactoryBean2() throws Exception {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(dataSourceTwo);
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper2/*Mapper*.xml"));
            return bean.getObject();
        }

        @Bean
        SqlSessionTemplate sqlSessionTemplate2() throws Exception {
            return new SqlSessionTemplate(sqlSessionFactoryBean2());
        }
    }
}

```

3. yml

```yaml
spring:
  datasource:
    one:
      username: root
      type: com.alibaba.druid.pool.DruidDataSource
      password: root
      url: jdbc:mysql:///tb_master
    two:
      username: root
      type: com.alibaba.druid.pool.DruidDataSource
      password: root
      url: jdbc:mysql:///tb_slave

mybatis:
  #mapper xml配置文件
  mapper-locations: classpath:mapper1/*Mapper*.xml,classpath:mapper2/*Mapper*.xml
  #实体类别名
  type-aliases-package: org.vijayian.entity
```

#### 3.jpa多数据源配置

1. pom文件

```xml
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.10</version>
        </dependency>
    </dependencies>
```

2. yml文件

```yaml
spring:
  datasource:
    one:
      username: root
      type: com.alibaba.druid.pool.DruidDataSource
      password: root
      url: jdbc:mysql:///tb_master
    two:
      username: root
      type: com.alibaba.druid.pool.DruidDataSource
      password: root
      url: jdbc:mysql:///tb_slave

  jpa:
    #数据库方言
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL57Dialect
        ddl-auto: none
      database: mysql
      show-sql: true
```

3. 配置文件，多数据源要使用@Primary，否则hibernate自动配置会失效（因为@ConditionalOnSingleCandidate注解）

```java
@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.one")
    DataSource dataSourceOne(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.two")
    DataSource dataSourceTwo(){
        return DruidDataSourceBuilder.create().build();
    }
}
```



```java
@Configuration
@EnableTransactionManagement
public class JpaConfig {

    /**
     * 启动时 EntityManagerFactoryBuilder bean没有注入，在这个类上alt+F7看到初始化这个类是在HibernateJpaConfiguration类
     *
     * @Configuration
     * @ConditionalOnSingleCandidate(DataSource.class)
     * class HibernateJpaConfiguration extends JpaBaseConfiguration
     *
     * @ConditionalOnSingleCandidate 意思是当前DataSource在容器中只有一个，或者说在容器中有多个，但是有首选的一个
     * 因此配置DataSource多数据源时要添加@Primary
     *
     */
    @Configuration
    @EnableJpaRepositories(basePackages = "org.vijayian.repository1",entityManagerFactoryRef = "entityManagerFactoryBeanOne",transactionManagerRef = "platformTransactionManagerOne")
    static class JpaConfigOne {
        @Resource(name = "dataSourceOne")
        DataSource dataSourceOne;

        @Autowired
        JpaProperties jpaProperties;

        @Bean
        @Primary
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBeanOne(EntityManagerFactoryBuilder builder) {
            return builder.dataSource(dataSourceOne)
                    .properties(jpaProperties.getProperties())
                    .packages("org.vijayian.entity")
                    .persistenceUnit("pu1")
                    .build();
        }

        @Bean
        PlatformTransactionManager platformTransactionManagerOne(EntityManagerFactoryBuilder builder){
            LocalContainerEntityManagerFactoryBean bean = entityManagerFactoryBeanOne(builder);
            return new JpaTransactionManager(bean.getObject());
        }
    }

    @Configuration
    @EnableJpaRepositories(basePackages = "org.vijayian.repository2",entityManagerFactoryRef = "entityManagerFactoryBeanTwo",transactionManagerRef = "platformTransactionManagerTwo")
    static class JpaConfigTwo {
        @Resource(name = "dataSourceTwo")
        DataSource dataSourceTwo;

        @Autowired
        JpaProperties jpaProperties;

        @Bean
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBeanTwo(EntityManagerFactoryBuilder builder) {
            return builder.dataSource(dataSourceTwo)
                    .properties(jpaProperties.getProperties())
                    .packages("org.vijayian.entity")
                    .persistenceUnit("pu2")
                    .build();
        }

        @Bean
        PlatformTransactionManager platformTransactionManagerTwo(EntityManagerFactoryBuilder builder){
            LocalContainerEntityManagerFactoryBean bean = entityManagerFactoryBeanTwo(builder);
            return new JpaTransactionManager(bean.getObject());
        }
    }
}

```









