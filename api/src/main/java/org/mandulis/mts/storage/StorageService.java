package org.mandulis.mts.storage;

import org.mandulis.mts.dto.AttachmentDto;

import java.nio.file.Path;

public interface StorageService {

    Path store(AttachmentDto attachmentDto);
}
