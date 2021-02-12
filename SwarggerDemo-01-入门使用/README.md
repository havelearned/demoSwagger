# Swagger介绍

为了解决前后端的及时协调，及时更改项目需求Api调用
而产生的一个工具

# 使用步骤

创建项目
导入依赖
````xml
 <!--swagger2-->
    <!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger2 -->
    <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger2</artifactId>
      <version>2.9.2</version>
    </dependency>

    <!--swagger-ui-->
    <!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui -->
    <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger-ui</artifactId>
      <version>2.9.2</version>
    </dependency>
````

# 创建一个HellWorld工程
```java
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @ResponseBody
    @RequestMapping(value = "/hello")
    public String doHello(){
        return "helloWorld";
    }
}
```

#配置Swagger
因为Swagger不是spring的jar需要配置
```java
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
}
```

- @Configuration 
    等价于 @Component 在ioc容器中添加bean
    
- @EnableSwagger2 开启Swagger2功能

启动项目访问：http://localhost:xxxx/swagger-ui.html

- Api Documentation:Swagger信息

- basic-error-controller 和  Basic Error Controller  是接口信息

- Models 是实体类 信息


## 使用Swagger
- 更换默认配置
```java

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {


    Contact contact=new Contact("姓名","http://jilijili.fun","邮箱");


    @Bean
    public Docket Docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo());

    }

    private ApiInfo apiInfo(){
       return  new ApiInfo(
               "Api 标题",
               "Api 描述",
               "1.0 版本",
               "urn:tos 组织",
               contact,
               "Apache 2.0 开源版本号",
               "http://www.apache.org/licenses/LICENSE-2.0",
               new ArrayList());


    }

```

Contact 类是封装了个人信息，方便联系 <br>
Docket 对象封装 整个项目api的详情


- 使用Swagger

```java
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    Contact contact=new Contact("姓名","http://jilijili.fun","邮箱");


    @Bean
    public Docket Docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.luojiaju.swargger.controller"))
                .build()
                ;

    }

    private ApiInfo apiInfo(){
       return  new ApiInfo(
               "Api 标题",
               "Api 描述",
               "1.0 版本",
               "urn:tos 组织",
               contact,
               "Apache 2.0 开源版本号",
               "http://www.apache.org/licenses/LICENSE-2.0",
               new ArrayList());


    }

```
- apis() <br>
RequestHandlerSelectors.配置扫描接口的方法 <br>
.basePackage() 指定扫描某一个包内的接口 <br>
.any() 扫描全部 <br>
.none() 全都不扫描 <br>
.withClassAnnotation() 扫描类上的注解 ，注解的条件是 该类必须是接口 <br>
.withMethodAnnotation() 扫描方法上的注解 <br>
但是 常用的是  RequestHandlerSelectors.basePackage(package) <br>


- .paths()<br>
 过滤什么路径<br>
 PathSelectors .<br>
.ant(final String antPattern) 过滤掉 /controller/xxx.java<br>
.any() 过滤掉全部<br>
.none() 全都不过来<br>
.regex(final String pathRegex) 使用正则表达是过滤<br>


需求：
    如果 是 开发环境下使用Swagger,而发布环境下禁止使用Swagger 怎么做到？
    
    
  - 获取当前的开发环境，判断是否处于对应的环境下
  ```java


        @Bean
         public Docket Docket( Environment environment){
     
     
             Profiles of = Profiles.of("test", "dev");
     
             boolean flag = environment.acceptsProfiles(of);
     
             return new Docket(DocumentationType.SWAGGER_2)
                     .apiInfo(apiInfo())
                     .enable(flag)
                     .select()
                     .apis(RequestHandlerSelectors.basePackage("com.luojiaju.swargger.controller"))
                     .build()
                     ;
     
         }

  ```

Environment 获取当前的生产环境类<br>

Profiles.of("test", "dev") 项目中的配置环境配置<br>

boolean flag = environment.acceptsProfiles(of); 获取当前的生产环境匹配返回布尔值

.enable(flag) 方法作用是 是否使用Swagger功能， 默认是true 使用

（有个小坑，boolean 数据类型，默认是false ，以及 项目的端口号要注意）；



## Swagger 的分组

每一开发人员都有自己编写的api 这时就需要更好的管控了



```java
  @Bean
    public Docket Docket3() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("开发人C")
                ;

    }
    @Bean
    public Docket Docket2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("开发人B")
                ;

    }

```



启动项目

**Select a spec** 会有下拉选择



### 生成Model

Swagger中还提供了，实体类的基本信息和注解信息

- 在接口中返回实体类信息

  ```java
  
      @PostMapping(value = "/user")
      public UserModel doUserQuery(){
          return new UserModel();
      }
  ```

  运行程序就可以看到 Swagger对Model的使用了

  注意的是，private 的属性 需要加上set get方法

  

- 在接口和Model中添加注释

```java



	@ApiModel("这是实体类")
	public class UserModel {
    	@ApiModelProperty("这是实体类的属性注释")
    	private String username;
    	@ApiModelProperty("这是实体类的属性注释")
    	private String password;
    }

    @ApiOperation("这是接口的方法描述：获取用户名称")
    @PostMapping(value = "/user2")
    public UserModel doUserQuery2(@ApiParam("用户名称") String name){
        return new UserModel();
    }
```

做好注解之后Swagger会生成相关信息；



# 最后

**Swagger最厉害的是接口可以在线测试 ，这个最简单自行测试吧！**

如果有错误会有错误信息，这样前后端人员对接协调及时，提高合作关系！！









 

