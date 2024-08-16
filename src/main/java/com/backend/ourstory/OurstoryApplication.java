package com.backend.ourstory;

import com.backend.ourstory.common.config.FileUploadConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.backend.ourstory")
@EnableJpaAuditing
@EnableConfigurationProperties(FileUploadConfig.class)
public class OurstoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(OurstoryApplication.class, args);
    }

}
