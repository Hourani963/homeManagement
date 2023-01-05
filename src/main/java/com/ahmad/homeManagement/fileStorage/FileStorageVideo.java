package com.ahmad.homeManagement.fileStorage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class FileStorageVideo extends FileStoreLocal {
    private Map<String ,String> videoMetaData;
    public FileStorageVideo(String projectName) throws Exception {
        super(projectName);
        folderName = "videos";
        creatFolder(folderName);
        System.out.println(this.pathAbsolutToResources+"\\"+folderName);
    }

    @Override
    public void setImage(MultipartFile multipartFile) throws IOException {

    }

    @Override
    public String setImage(MultipartFile image, String folderName) throws IOException {
        return null;
    }

    @Override
    public String setVideo(MultipartFile video, String folderName) throws IOException {
        // TODO
        // isVideo
        isFileEmpty(video);
        this.videoMetaData = extractMetaData(video);
        save(this.folderName+"\\"+ folderName, video.getOriginalFilename(), Optional.ofNullable(videoMetaData),video.getInputStream());
        return folderName+"\\"+video.getOriginalFilename();
    }
}
