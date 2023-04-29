package com.example.springdevelop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringDevelopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDevelopApplication.class, args);
    }

}
