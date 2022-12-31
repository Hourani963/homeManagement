package com.ahmad.homeManagement.fileStorage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileStorageVideo extends FileStoreLocal {
    public FileStorageVideo(String projectName) throws Exception {
        super(projectName);
        folderName = "videos";
        creatFolder();
        System.out.println(this.pathAbsolutToResources+"\\"+folderName);
    }

    @Override
    public void setImage(MultipartFile multipartFile) throws IOException {

    }

    @Override
    public String setImage(MultipartFile image, String folderName) throws IOException {
        return null;
    }
}
