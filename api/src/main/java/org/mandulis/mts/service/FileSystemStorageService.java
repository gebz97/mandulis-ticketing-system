package org.mandulis.mts.service;

import org.mandulis.mts.exception.StorageException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    public FileSystemStorageService(StorageProperties properties) {

        if (properties.getLocation().trim().isEmpty()) {
            throw new StorageException("File upload location can not be Empty.");
        }

        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public Path store(String filename, InputStream file) {
        createStorageFileTreeIfNeeded();

        try {
            Path referencePath = this.rootLocation.resolve(
                            Paths.get(filename))
                    .normalize();
            Path destinationFile = referencePath.toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            Files.copy(file, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING);
            return referencePath;
        } catch (IOException e) {
            throw new StorageException("Failed to store file.");
        }
    }

    private void createStorageFileTreeIfNeeded() {
        try {
            Files.createDirectories(rootLocation.toAbsolutePath());
        } catch (Exception e) {
            throw new StorageException("Failed to create storage file tree.");
        }
    }
}
