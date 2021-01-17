package org.vijayian.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * config
 *
 * @author ViJay
 * @date 2021-01-17
 */
@Configuration
public class WebMvnConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInteceptor())
                .addPathPatterns("/**");
    }
}
