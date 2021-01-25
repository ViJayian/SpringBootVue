package org.vijayian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.vijayian.entity.User;
import org.vijayian.mapper1.UserMapper;
import org.vijayian.mapper2.UserMapper2;

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
    UserMapper userMapper;

    @Autowired
    UserMapper2 userMapper2;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            User user = new User();
            user.setUsername("mybatis master");
            userMapper.addUser(user);

            User user2 = new User();
            user2.setUsername("mybatis slave");
            userMapper2.addUser(user2);
        };
    }
}
