package com.captainyun7.ch503sessionbasedspringsecuritylogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class Ch503SessionBasedSpringSecurityLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ch503SessionBasedSpringSecurityLoginApplication.class, args);
    }

}
