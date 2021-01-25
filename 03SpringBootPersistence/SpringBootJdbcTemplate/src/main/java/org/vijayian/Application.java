package org.vijayian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.vijayian.entity.User;
import org.vijayian.repository.UserRepository;

import java.util.List;

/**
 * root
 *
 * @author ViJay
 * @date 2021-01-22
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    UserRepository userRepository;

    @Bean
    ApplicationRunner applicationRunner(){
        return args -> {
            User user = new User();
            user.setUsername("jdbcTemplate test");
            user.setDbsource("system");
            userRepository.addUser(user);

            user.setDbsource("update");
            userRepository.updateUser(user);

            userRepository.addUser(user);

            User userById = userRepository.getUserById(6);
            System.out.println(userById);

            List<User> users = userRepository.allUserLists();
            System.out.println(users);

            userRepository.deleteUserById(7);
        };
    }
}
