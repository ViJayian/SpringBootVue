package org.vijayian.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * jpaconfig
 *
 * @author ViJay
 * @date 2021-01-23
 */
@Configuration
@EnableTransactionManagement
public class JpaConfig {

    /**
     * 启动时 EntityManagerFactoryBuilder bean没有注入，在这个类上alt+F7看到初始化这个类是在HibernateJpaConfiguration类
     *
     * @Configuration
     * @ConditionalOnSingleCandidate(DataSource.class)
     * class HibernateJpaConfiguration extends JpaBaseConfiguration
     *
     * @ConditionalOnSingleCandidate 意思是当前DataSource在容器中只有一个，或者说在容器中有多个，但是有首选的一个
     * 因此配置DataSource多数据源时要添加@Primary
     *
     */
    @Configuration
    @EnableJpaRepositories(basePackages = "org.vijayian.repository1",entityManagerFactoryRef = "entityManagerFactoryBeanOne",transactionManagerRef = "platformTransactionManagerOne")
    static class JpaConfigOne {
        @Resource(name = "dataSourceOne")
        DataSource dataSourceOne;

        @Autowired
        JpaProperties jpaProperties;

        @Bean
        @Primary
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBeanOne(EntityManagerFactoryBuilder builder) {
            return builder.dataSource(dataSourceOne)
                    .properties(jpaProperties.getProperties())
                    .packages("org.vijayian.entity")
                    .persistenceUnit("pu1")
                    .build();
        }

        @Bean
        PlatformTransactionManager platformTransactionManagerOne(EntityManagerFactoryBuilder builder){
            LocalContainerEntityManagerFactoryBean bean = entityManagerFactoryBeanOne(builder);
            return new JpaTransactionManager(bean.getObject());
        }
    }

    @Configuration
    @EnableJpaRepositories(basePackages = "org.vijayian.repository2",entityManagerFactoryRef = "entityManagerFactoryBeanTwo",transactionManagerRef = "platformTransactionManagerTwo")
    static class JpaConfigTwo {
        @Resource(name = "dataSourceTwo")
        DataSource dataSourceTwo;

        @Autowired
        JpaProperties jpaProperties;

        @Bean
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBeanTwo(EntityManagerFactoryBuilder builder) {
            return builder.dataSource(dataSourceTwo)
                    .properties(jpaProperties.getProperties())
                    .packages("org.vijayian.entity")
                    .persistenceUnit("pu2")
                    .build();
        }

        @Bean
        PlatformTransactionManager platformTransactionManagerTwo(EntityManagerFactoryBuilder builder){
            LocalContainerEntityManagerFactoryBean bean = entityManagerFactoryBeanTwo(builder);
            return new JpaTransactionManager(bean.getObject());
        }
    }
}
