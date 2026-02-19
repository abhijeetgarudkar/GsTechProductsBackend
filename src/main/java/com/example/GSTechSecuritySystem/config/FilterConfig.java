package com.example.GSTechSecuritySystem.config;

import com.example.GSTechSecuritySystem.infrastructure.KeepAliveHealthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<KeepAliveHealthFilter> healthFilter() {

        FilterRegistrationBean<KeepAliveHealthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new KeepAliveHealthFilter());

        registration.addUrlPatterns("/*"); // intercept everything
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return registration;
    }
}

