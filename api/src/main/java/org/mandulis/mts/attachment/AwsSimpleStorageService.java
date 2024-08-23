package org.mandulis.mts.attachment;

import java.net.URI;

public class AwsSimpleStorageService implements StorageService {
    @Override
    public URI store(AttachmentFile attachmentFile) {
        return null;
    }

    @Override
    public AttachmentFile retrieve(URI uri) {
        return null;
    }

    @Override
    public boolean delete(URI uri) {
        return false;
    }
}
