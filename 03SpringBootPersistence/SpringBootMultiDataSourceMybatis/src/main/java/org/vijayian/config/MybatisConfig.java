package org.vijayian.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * mybatis config
 *
 * @author ViJay
 * @date 2021-01-23
 */
@Configuration
public class MybatisConfig {

    @Configuration
    @MapperScan(value = "org.vijayian.mapper1", sqlSessionFactoryRef = "sqlSessionFactoryBean1",
            sqlSessionTemplateRef = "sqlSessionTemplate1")
    static class MybatisConfigOne {
        @Autowired
        DataSource dataSourceOne;

        @Bean
        SqlSessionFactory sqlSessionFactoryBean1() throws Exception {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(dataSourceOne);
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper1/*Mapper*.xml"));
            return bean.getObject();
        }

        //>> TODO 线程安全类，用来管理Mybatis的SqlSession操作.
        @Bean
        SqlSessionTemplate sqlSessionTemplate1() throws Exception {
            return new SqlSessionTemplate(sqlSessionFactoryBean1());
        }
    }

    @Configuration
    @MapperScan(value = "org.vijayian.mapper2", sqlSessionFactoryRef = "sqlSessionFactoryBean2",
            sqlSessionTemplateRef = "sqlSessionTemplate2")
    static class MybatisConfigTwo {
        @Autowired
        DataSource dataSourceTwo;

        @Bean
        SqlSessionFactory sqlSessionFactoryBean2() throws Exception {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(dataSourceTwo);
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper2/*Mapper*.xml"));
            return bean.getObject();
        }

        @Bean
        SqlSessionTemplate sqlSessionTemplate2() throws Exception {
            return new SqlSessionTemplate(sqlSessionFactoryBean2());
        }
    }
}
