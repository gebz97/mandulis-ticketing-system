package org.mandulis.mts.attachment;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class MinioStorageService implements StorageService {

    private final MinioClient minioClient;
    private final String uriPrefix;
    private final String bucketName;

    MinioStorageService(
            String minioHost,
            int minioPort,
            String minioBucketName,
            String minioAccessKey,
            String minioSecretKey
    ) {
        this.minioClient = MinioClient.builder()
                .endpoint(minioHost, minioPort, true)
                .credentials(minioAccessKey, minioSecretKey).build();
        this.uriPrefix = "https://" + minioHost + ":" + minioPort + "/" + minioBucketName;
        this.bucketName = minioBucketName;
    }

    @Override
    public URI store(AttachmentFile attachmentFile) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(attachmentFile.getFileName())
                            .stream(attachmentFile.getInputStream(), -1, 10485760)
                            .contentType(attachmentFile.getContentType())
                            .build()
            );
            return URI.create(this.uriPrefix + "/" + attachmentFile.getFileName());
        } catch (MinioException | IOException e) {
            log.error("Error storing file: {}", e.getMessage());
            throw new RuntimeException("Error storing file", e);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AttachmentFile retrieve(URI uri) {
        try {
            String objectName = uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1);
            InputStream inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            return AttachmentFile.builder()
                    .fileName(objectName)
                    .inputStream(inputStream)
                    .build();
        } catch (MinioException | IOException e) {
            log.error("Error retrieving file: {}", e.getMessage());
            throw new RuntimeException("Error retrieving file", e);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(URI uri) {
        try {
            String objectName = uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1);
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            return true;
        } catch (MinioException e) {
            log.error("Error deleting file: {}", e.getMessage());
            return false;
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
