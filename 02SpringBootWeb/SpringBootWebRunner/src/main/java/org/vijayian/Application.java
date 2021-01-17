package org.vijayian;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

}
