package com.weichen.stumanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class DBSecurityConfig
{
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        // @formatter:off
        http.authorizeHttpRequests()
        .antMatchers("/register").permitAll()
        .antMatchers("/").hasRole("member")
        .anyRequest().permitAll()
        .and()
        .formLogin()
        .and()
        .sessionManagement()
        .invalidSessionUrl("/login")
        .and()
        .csrf().disable();
        // @formatter:on
        return http.build();
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}