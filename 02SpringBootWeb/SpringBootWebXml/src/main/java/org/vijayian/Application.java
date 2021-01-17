package org.vijayian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.vijayian.config.Hello;

/**
 *
 *
 * @author ViJay
 * @date 2021-01-17
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private Hello hello;

    @Bean
    public ApplicationRunner runner(){
        return args -> {
            System.out.println(hello.getName());
        };
    }
}
