package org.vijayian.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * configuration
 *
 * @author ViJay
 * @date 2021-01-17
 */
@Configuration
//>> TODO 导入xml bean的配置.
@ImportResource("classpath:beans.xml")
public class HelloConfiguartion {

}
