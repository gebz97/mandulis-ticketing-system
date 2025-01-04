package org.mandulis.mts.attachment;

import io.minio.*;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import okio.Timeout;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MinioStorageService implements StorageService {

    private final MinioClient minioClient;
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
        this.bucketName = minioBucketName;
    }

    @Override
    public String store(MultipartFile file) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(file.getOriginalFilename())
                            .stream(file.getInputStream(), file.getSize(), 10485760)
                            .contentType(file.getContentType())
                            .build()
            );
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(bucketName)
                            .object(file.getOriginalFilename())
                            .build()
            );
        } catch (MinioException | IOException e) {
            log.error("Error storing file: {}", e.getMessage());
            throw new RuntimeException("Error storing file", e);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String retrieve(String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(1, TimeUnit.HOURS)
                            .build()
            );

        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | XmlParserException |
                 ServerException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean delete(String objectName) {
        try {
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
