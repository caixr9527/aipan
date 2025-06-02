package com.aipan.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author caixr
 * @date 2025/6/2 16:05
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    private String endPoint;

    private String accessKey;

    private String accessSecret;

    private String bucketName;

    private Long PRE_SING_URL_EXPIRE_TIME = 60 * 10 * 1000L;

}
