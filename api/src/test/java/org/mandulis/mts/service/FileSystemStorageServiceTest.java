package org.mandulis.mts.service;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileSystemStorageServiceTest {

    private static final StorageProperties properties = new StorageProperties();

    @AfterEach
    void afterEach() throws IOException {
        File testFileStorage = new File(properties.getLocation());
        FileUtils.deleteDirectory(testFileStorage);
    }

    @Test
    void shouldStoreFile() throws IOException {
        StorageService storageService = new FileSystemStorageService(properties);

        byte[] content = "example".getBytes(StandardCharsets.UTF_8);
        InputStream stream = new ByteArrayInputStream(content);

        Path path = storageService.store("filename.txt", stream);

        File storedFile = new File(path.toUri());
        assertTrue(storedFile.exists() && !storedFile.isDirectory());
        assertArrayEquals(content, Files.readAllBytes(storedFile.toPath()));
    }

}
