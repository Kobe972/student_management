package com.weichen.stumanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
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
        .antMatchers("/listeningWordTest").hasAnyRole("member","faculty","root")
        .antMatchers("/uploadResources").hasAnyRole("root","faculty")
        .antMatchers("/upload/resources").hasAnyRole("root","faculty")
        .antMatchers("/uploadCourseInfo").hasRole("root")
        .antMatchers("/upload/courseInfo").hasRole("root")
        .antMatchers("/delete/courseInfo").hasRole("root")
        .antMatchers("/courseList").permitAll()
        .antMatchers("/image/courseCover").permitAll()
        .antMatchers("/backstage").hasAnyRole("root","faculty")
        .antMatchers("/getRole").permitAll()
        .antMatchers("/getUsername").permitAll()
        .antMatchers("/resourceList").permitAll()
        .antMatchers("/delete/resource").hasAnyRole("faculty","root")
        .antMatchers("/getUploadedResources").hasAnyRole("faculty","member","root")
        .antMatchers("/resources").permitAll()
        .antMatchers("/registerFaculty").hasRole("root")
        .antMatchers("/facultyRegister").hasRole("root")
        .antMatchers("/").permitAll()
        .anyRequest().permitAll()
        .and()
        .rememberMe()
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