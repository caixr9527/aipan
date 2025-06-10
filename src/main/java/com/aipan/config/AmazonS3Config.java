package com.aipan.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author caixr
 * @date 2025/6/10 22:01
 */
@Configuration
public class AmazonS3Config {

    @Autowired
    private MinioConfig minioConfig;

    @Bean(name = "amazonS3Client")
    public AmazonS3 getClient() {
        ClientConfiguration config = new ClientConfiguration();
        config.setProtocol(Protocol.HTTP);
        config.setConnectionTimeout(5000);
        config.setUseExpectContinue(true);

        AWSCredentials credentials = new BasicAWSCredentials(minioConfig.getAccessKey(), minioConfig.getAccessSecret());
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(minioConfig.getEndPoint(), Regions.US_EAST_1.name());

        return AmazonS3ClientBuilder.standard()
                .withClientConfiguration(config)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(endpointConfiguration)
                .withPathStyleAccessEnabled(true).build();
    }

}
