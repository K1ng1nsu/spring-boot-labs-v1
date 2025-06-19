package com.captainyun7.ch4examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.captainyun7.ch4examples.v4")
@EnableJpaRepositories(basePackages = "com.captainyun7.ch4examples.v4")
@EntityScan(basePackages = "com.captainyun7.ch4examples.v4")
public class Ch4ExamplesApplication {
    public static void main(String[] args) {
        SpringApplication.run(Ch4ExamplesApplication.class, args);
    }
}
