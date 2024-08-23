package org.mandulis.mts.controller;

import io.minio.MinioClient;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MinIOContainer;

public class BaseMinioIT {

    private static final MinIOContainer MINIO_CONTAINER = new MinIOContainer("minio/minio:RELEASE.2023-09-04T19-57-37Z");

    // todo: be careful with name: https://docs.aws.amazon.com/AmazonS3/latest/userguide/bucketnamingrules.html
    protected static final String BUCKET_NAME = "teststorage";

    protected static MinioClient MINIO_CLIENT;

    @DynamicPropertySource
    static void registerDynamicProps(DynamicPropertyRegistry registry) {
        MINIO_CONTAINER.start();
        MINIO_CLIENT = MinioClient
                .builder()
                .endpoint(MINIO_CONTAINER.getS3URL())
                .credentials(MINIO_CONTAINER.getUserName(), MINIO_CONTAINER.getPassword())
                .build();
        registry.add("jwt.secret", () -> "secret");
        registry.add("minio.endpoint", MINIO_CONTAINER::getS3URL);
        registry.add("minio.username", MINIO_CONTAINER::getUserName);
        registry.add("minio.password", MINIO_CONTAINER::getPassword);
        registry.add("minio.bucketName", () -> BUCKET_NAME);
    }
}
