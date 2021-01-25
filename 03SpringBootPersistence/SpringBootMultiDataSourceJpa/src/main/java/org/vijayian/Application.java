package org.vijayian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.vijayian.entity.User;
import org.vijayian.repository1.UserRepository;
import org.vijayian.repository2.UserRepository2;

/**
 * root
 *
 * @author ViJay
 * @date 2021-01-23
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepository2 userRepository2;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            User user = new User();
            user.setUsername("jpa insert master");
            userRepository.save(user);

            User user2 = new User();
            user2.setUsername("jpa insert slave");
            userRepository2.save(user2);


        };
    }
}
