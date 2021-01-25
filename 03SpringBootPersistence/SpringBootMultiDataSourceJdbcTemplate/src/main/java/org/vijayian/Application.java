package org.vijayian;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

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
    JdbcTemplate jdbcTemplateOne;

    @Autowired
    JdbcTemplate jdbcTemplateTwo;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            jdbcTemplateOne.update("insert into t_user(username,dbsource) values (?,?)", "one", "one");
            jdbcTemplateTwo.update("insert into t_user(username,dbsource) values (?,?)", "two", "two");
        };
    }
}
