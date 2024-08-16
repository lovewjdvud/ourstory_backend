package com.backend.ourstory.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file")
public class FileUploadConfig {
    @Value("${file.rootDir}")
    private String rootDir;

    @Value("${file.uploadDir}")
    private String uploadDir;

    @Value("${file.profileImageDir}")
    private String profileImageDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public String getProfileImageDir() {
        return profileImageDir;
    }

    public String getRootDir() {
        return rootDir;
    }
}