package com.windstorm.management.infrastructure.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@Configuration
public class MethodSecurityConfig {

	@Bean
	public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
		return new DefaultMethodSecurityExpressionHandler();
	}
}
