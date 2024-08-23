package org.mandulis.mts.storage;

import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.mandulis.mts.dto.AttachmentDto;
import org.mandulis.mts.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class MinioStorageService implements StorageService {

    private final MinioClientBuilder minioClientBuilder;
    private final String bucketName;

    MinioStorageService(
            MinioClientBuilder minioClientBuilder,
            @Value("${minio.bucketName}") String bucketName
    ) {
        this.minioClientBuilder = minioClientBuilder;
        this.bucketName = bucketName;
    }

    @Override
    public Path store(AttachmentDto attachmentDto) {
        this.checkIfBucketExists();
        this.uploadFile(attachmentDto);
        return Path.of(this.bucketName + "/" + attachmentDto.fileName);
    }

    private void uploadFile(AttachmentDto attachmentDto) {
        try (MinioClient minioClient = minioClientBuilder.build()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(this.bucketName)
                            .object(attachmentDto.fileName)
                            .stream(attachmentDto.inputStream, -1, 10485760)
                            .contentType(attachmentDto.contentType)
                            .build());
        } catch (Exception e) {
            throw new StorageException("Could not upload file.", e);
        }
    }

    private void checkIfBucketExists() {
        boolean exists;
        try (MinioClient minioClient = minioClientBuilder.build()) {
            exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(this.bucketName).build());
        } catch (Exception e) {
            throw new StorageException("IO exception, check if bucket exists.", e);
        }
        if (!exists) {
            throw new StorageException(String.format("Bucket[name=%s] does not exist.", this.bucketName));
        }
    }

}
