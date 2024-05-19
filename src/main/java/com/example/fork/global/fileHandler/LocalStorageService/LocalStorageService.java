package com.example.fork.global.fileHandler.LocalStorageService;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface LocalStorageService {

    void init();
    void store(MultipartFile file, String generatedFileName);
    Path load(String filename);
    Resource loadAsResource(String filename);
    void delete(String filename);
    void deleteAll();
    String getRootPath();
}
