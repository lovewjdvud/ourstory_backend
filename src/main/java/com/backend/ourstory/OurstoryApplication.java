package com.backend.ourstory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.backend.ourstory")
@EnableJpaAuditing
public class OurstoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(OurstoryApplication.class, args);
    }

}
