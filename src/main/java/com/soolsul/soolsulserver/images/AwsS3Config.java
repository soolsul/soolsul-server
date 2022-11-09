package com.soolsul.soolsulserver.images;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Config {

    @Value("${aws.s3.credentials.access.key}")
    private String accessKey;

    @Value("${aws.s3.credentials.secret.key}")
    private String secretKey;

    @Value("${aws.s3.images.region.name}")
    private String regionName;

    @Bean
    public AmazonS3 s3Client() {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials()))
                .withRegion(regionName)
                .build();
    }

    private AWSCredentials awsCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }
}
