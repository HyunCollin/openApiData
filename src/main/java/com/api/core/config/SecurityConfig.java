package com.api.core.config;

import com.api.core.filter.ApiAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ApiAuthenticationFilter apiAuthenticationFilter;

    public SecurityConfig(ApiAuthenticationFilter apiAuthenticationFilter) {
        this.apiAuthenticationFilter = apiAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests()
            .requestMatchers("/**").permitAll()
            .requestMatchers("/images/**").permitAll()
            .requestMatchers("/js/**").permitAll()
            .requestMatchers("/webjars/**").permitAll();
        //  filter order add
        http.addFilterBefore(apiAuthenticationFilter, BasicAuthenticationFilter.class);
        http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        });
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/file/**");
    }


}
