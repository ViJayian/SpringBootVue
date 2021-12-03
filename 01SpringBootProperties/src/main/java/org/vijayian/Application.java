package org.vijayian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * root.
 *
 * @author ViJay
 */
@SpringBootApplication
@RestController //is ok
//@Component //not ok
public class Application {
    public static void main(String[] args) {
        //>> TODO 设置启动配置文件.
        //>> TODO 变量不存在使用默认配置文件.
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
        builder.application().setAdditionalProfiles("dev");
        builder.run(args);
        ConfigurableApplicationContext context = builder.context();
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        System.out.println(beanDefinitionNames);
//        SpringApplication.run(Application.class, args);
    }

    @RequestMapping("/req")
//    @ResponseBody
    public String req() {
        return "ok";
    }
}
