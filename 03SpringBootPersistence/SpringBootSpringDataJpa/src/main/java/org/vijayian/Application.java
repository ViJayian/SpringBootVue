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
 * @date 2021-01-23
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
            user.setUsername("jpa");
            user.setDbsource("system");
            userRepository.save(user);

            userRepository.deleteById(1);

            User u = userRepository.findById(2).orElseThrow(NullPointerException::new);
            u.setUsername("jpa update");
            userRepository.save(u);
            System.out.println(u);

            List<User> all = userRepository.findAll();
            System.out.println(all);
        };
    }
}
