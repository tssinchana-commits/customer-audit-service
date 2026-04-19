package com.project.findisc.audit_table.config;

import com.project.findisc.audit_table.security.AuthFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterRegistration(AuthFilter filter) {

        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(filter);

        return registrationBean;
    }
}