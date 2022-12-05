package com.weichen.stumanager;

import com.weichen.stumanager.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import javax.sql.DataSource;

@SpringBootApplication
@MapperScan("com.weichen.stumanager.mapper")
@ComponentScan(basePackages = {"com.weichen.stumanager"})
public class StumanagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(StumanagerApplication.class, args);
    }

}
