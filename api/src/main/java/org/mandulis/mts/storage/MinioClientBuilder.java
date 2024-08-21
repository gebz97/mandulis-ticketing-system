package org.mandulis.mts.storage;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MinioClientBuilder {
    private final String endpoint;
    private final String username;
    private final String password;

    public MinioClientBuilder(
            @Value("${minio.endpoint}") String endpoint,
            @Value("${minio.username}") String username,
            @Value("${minio.password}") String password
    ) {
        this.endpoint = endpoint;
        this.username = username;
        this.password = password;
    }

    public MinioClient build() {
        return MinioClient
                .builder()
                .endpoint(endpoint)
                .credentials(username, password)
                .build();
    }

}
