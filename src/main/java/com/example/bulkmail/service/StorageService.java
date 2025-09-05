package com.example.bulkmail.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class StorageService {
    public void storeFile(MultipartFile file, String directory) throws IOException {
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File destinationFile = new File(dir, file.getOriginalFilename());
        file.transferTo(destinationFile);
    }
}
