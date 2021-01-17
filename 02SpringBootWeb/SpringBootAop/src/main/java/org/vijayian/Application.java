package org.vijayian;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.vijayian.service.HelloService;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 *
 * @author ViJay
 * @date 2021-01-17
 */
@SpringBootApplication
public class Application {
    public Application(HelloService helloService) {
        this.helloService = helloService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    private final HelloService helloService;

    @Bean
    ApplicationRunner runner() {
        return args -> {
            helloService.hello();
        };
    }
}
