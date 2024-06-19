package com.monitor.master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class MonitorRunApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorRunApplication.class, args);
    }
}
