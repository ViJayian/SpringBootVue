package org.vijayian.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * jdbcTemplateConfig
 *
 * @author ViJay
 * @date 2021-01-23
 */
@Configuration
public class JdbcTemplateConfig {
    @Bean
    JdbcTemplate jdbcTemplateOne(DataSource dataSourceOne) {
        return new JdbcTemplate(dataSourceOne);
    }

    @Bean
    JdbcTemplate jdbcTemplateTwo(DataSource dataSourceTwo) {
        return new JdbcTemplate(dataSourceTwo);
    }

}
