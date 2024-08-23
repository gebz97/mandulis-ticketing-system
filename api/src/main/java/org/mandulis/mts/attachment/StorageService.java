package org.mandulis.mts.attachment;

import java.net.URI;

public interface StorageService {

    URI store(AttachmentFile attachmentFile);
    AttachmentFile retrieve(URI uri);
    boolean delete(URI uri);
}
