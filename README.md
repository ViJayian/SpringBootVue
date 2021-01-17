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



- 代码：**[02SpringBootWeb](./02SpringBootWeb)**





