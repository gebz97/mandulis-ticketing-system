package org.mandulis.mts.attachment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Value("${storage.type}")
    private String type;

    @Value("${minio.host}")
    private String minioHost;

    @Value("${minio.port}")
    private int minioPort;

    @Value("${minio.bucketName}")
    private String minioBucketName;

    @Value("${minio.access-key}")
    private String minioAccessKey;

    @Value("${minio.secret-key}")
    private String minioSecretKey;

    @Bean
    public StorageService storageService() {
        return switch (type) {
            case "minio" -> new MinioStorageService(
                    minioHost,
                    minioPort,
                    minioBucketName,
                    minioAccessKey,
                    minioSecretKey
            );
            case "s3" -> new AwsSimpleStorageService();
            default -> throw new IllegalArgumentException("Invalid storage type");
        };
    }
}
