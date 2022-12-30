package com.ahmad.homeManagement.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

public interface IFileStorage {


    byte[] download(String fileName);

    void setImage(MultipartFile multipartFile) throws IOException;
    void setImage(MultipartFile image, String folderName) throws IOException;
    void setFolderName(String folderName);
    String getFolderName();
    String getPathAbsolutToResources();
}
