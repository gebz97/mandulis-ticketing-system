package org.mandulis.mts.service;

import io.minio.*;
import org.mandulis.mts.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Path;

@Service
public class MinioStorageService implements StorageService {

    private final String endpoint;
    private final String username;
    private final String password;
    private final String bucketName;

    MinioStorageService(
            @Value("${minio.endpoint}") String endpoint,
            @Value("${minio.username}") String username,
            @Value("${minio.password}") String password,
            @Value("${minio.bucketName}") String bucketName
    ) {
        this.endpoint = endpoint;
        this.username = username;
        this.password = password;
        this.bucketName = bucketName;
    }

    MinioClient create() {
        return MinioClient
                .builder()
                .endpoint(endpoint)
                .credentials(username, password)
                .build();
    }

    @Override
    public Path store(String filename, InputStream file) {

        try (MinioClient minioClient = create()) {

            if (!bucketExists(minioClient)) {
                throw new StorageException(String.format("Bucket[name=%s] does not exist.", this.bucketName));
            }

            try {
                ObjectWriteResponse response = minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(this.bucketName)
                                .object(filename)
                                .stream(file, -1, 10485760)
                                .contentType("application/pdf")
                                .build());

                StatObjectResponse r = minioClient.statObject(StatObjectArgs.builder()
                                .bucket(this.bucketName)
                                .object(filename)
                        .build());

                System.out.println();
            } catch (Exception e) {
                throw new StorageException("Could not upload file.", e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Path.of(this.bucketName + "/" + filename);
    }

    private boolean bucketExists(MinioClient minioClient) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(this.bucketName).build());
        } catch (Exception e) {
            throw new StorageException("IO exception, check if bucket exists.", e);
        }
    }

}
