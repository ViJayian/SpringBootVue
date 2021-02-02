package org.vijayian;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;

/**
 * application
 *
 * @author ViJay
 * @date 2021-01-30
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Bean
    ApplicationRunner runner() {
        return args -> {
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            ops.set("name", "整合redis");
            String name = ops.get("name");
            System.out.println(name);

            ValueOperations ops2 = redisTemplate.opsForValue();
            Book book = new Book();
            book.setName("金字塔");
            book.setAuthor("kk");
            ops2.set("abook", book);

            Book abook = (Book) ops2.get("abook");
            System.out.println(abook);
        };
    }

    //>> TODO 必须实现序列化接口 ，否则会报错.
    @Data
    static class Book implements Serializable {
        private String name;
        private String author;
    }
}
