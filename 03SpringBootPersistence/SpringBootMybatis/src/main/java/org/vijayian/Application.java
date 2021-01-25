package org.vijayian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.vijayian.entity.User;
import org.vijayian.mapper.UserMapper;

import java.util.List;

/**
 * root
 *
 * @author ViJay
 * @date 2021-01-23
 */
@SpringBootApplication
//>> TODO 方式二：使用@MapperScan扫描指定mapper的包，这样就不用在每个类上加@Mapper了.
//@MapperScan(basePackages = "org.vijayian.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    UserMapper userMapper;

    @Bean
    ApplicationRunner applicationRunner(){
        return args -> {
            User user = new User();
            user.setUsername("jdbcTemplate test");
            user.setDbsource("system");
            userMapper.addUser(user);

            user.setDbsource("update");
            userMapper.updateUser(user);

            userMapper.addUser(user);

            User userById = userMapper.findUserById(6);
            System.out.println(userById);

            List<User> users = userMapper.findAll();
            System.out.println(users);

            userMapper.deleteUserById(13);
        };
    }
}
