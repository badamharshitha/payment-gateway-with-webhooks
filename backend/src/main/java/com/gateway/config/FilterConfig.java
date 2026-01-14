package com.gateway.config;

import com.gateway.security.ApiAuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<ApiAuthFilter> apiAuthFilter(ApiAuthFilter filter) {
        FilterRegistrationBean<ApiAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.addUrlPatterns("/api/*");
        registration.setOrder(1); // VERY IMPORTANT
        return registration;
    }
}
