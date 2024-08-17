package org.mandulis.mts.service;

import java.io.InputStream;
import java.nio.file.Path;

public interface StorageService {

    Path store(String filename, InputStream file);
}
