package com.example.log.config;

import com.example.log.filter.LogFilter;
import com.google.common.collect.ImmutableSet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfig {
    @Bean
    public FilterRegistrationBean<LogFilter> filterRegistrationBean() {
        FilterRegistrationBean<LogFilter> filterRegistrationBean = new FilterRegistrationBean<>(logFilter());
        filterRegistrationBean.setUrlPatterns(ImmutableSet.of("/v1/private/*", "/v1/public/*", "/v1/friendly/*", "/mgmt/*"));

        //架构组的加traceId的优先级最高： TracingOncePerRequestFilter
//        filterRegistrationBean.setOrder(Integer.MIN_VALUE + 100);
        filterRegistrationBean.setOrder(Integer.MIN_VALUE);
        return filterRegistrationBean;
    }

    @Bean
    public LogFilter logFilter() {
        return new LogFilter();
    }

}
