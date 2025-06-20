package com.example.ch4codeyourself;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.ch4codeyourself.v5.repository")
@EntityScan(basePackages = "com.example.ch4codeyourself.v5")
public class Ch4CodeyourselfApplication {
    public static void main(String[] args) {
        SpringApplication.run(Ch4CodeyourselfApplication.class, args);
    }

}
