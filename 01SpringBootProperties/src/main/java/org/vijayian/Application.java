package org.vijayian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * root.
 *
 * @author ViJay
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        //>> TODO 设置启动配置文件.
        //>> TODO 变量不存在使用默认配置文件.
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
        builder.application().setAdditionalProfiles("dev");
        builder.run(args);
        SpringApplication.run(Application.class, args);
    }
}
