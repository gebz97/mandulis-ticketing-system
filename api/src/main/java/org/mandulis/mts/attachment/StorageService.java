package org.mandulis.mts.attachment;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String store(MultipartFile file);
    String retrieve(String name);
    boolean delete(String objectName);
}
