package com.ahmad.homeManagement.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IFileStorage {



    /**
     * using this methode to get all files in the folder so we can send files absolute links to the frontEnd so they can use the link in the download method to get the file
     * @param pathAbsolutToFolder
     * @return
     */
    List<String> listFilesForFolder(String pathAbsolutToFolder, String ImageOrVideo);
    byte[] downloadByLink(String absoluteLink);
    void setImage(MultipartFile multipartFile) throws IOException;
    String setImage(MultipartFile image, String folderName) throws IOException;
    void setFolderName(String folderName);
    String getFolderName();
    String getPathAbsolutToResources();

    String setVideo(MultipartFile image, String folderName) throws IOException;
}
