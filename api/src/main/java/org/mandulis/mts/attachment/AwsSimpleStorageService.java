package org.mandulis.mts.attachment;

import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

public class AwsSimpleStorageService implements StorageService {
    @Override
    public String store(MultipartFile file) {
        return null;
    }

    @Override
    public String retrieve(String objectName) {
        return null;
    }

    @Override
    public boolean delete(String objectName) {
        return false;
    }
}
