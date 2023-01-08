package com.ahmad.homeManagement.fileStorage;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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
        isVideo(video);
        isFileEmpty(video);
        this.videoMetaData = extractMetaData(video);
        save(this.folderName+"\\"+ folderName, video.getOriginalFilename(), Optional.ofNullable(videoMetaData),video.getInputStream());
        return folderName+"\\"+video.getOriginalFilename();
    }

    private void isVideo(MultipartFile file) {
        System.err.println(file.getContentType());
        if (!Arrays.asList(
                ("video/mp4"),
                ("video/mov"),
                ("video/mkv"),
                ("video/vnd.dlna.mpeg-tts")).contains(file.getContentType()))
            throw new IllegalStateException("File must be a video [" + file.getContentType() + "]");
    }
    @Override
    public void deleteFile(String nomPerformer, String nomVido) throws CantDeleteFileException {
        File video = new File(pathAbsolutToResources+"\\videos\\"+ nomPerformer + "\\"+ nomVido);


        if(video.delete()){
            File thumbnailVideo = new File(pathAbsolutToResources+"\\videos\\"+ nomPerformer + "\\thumbnail\\"+ nomVido+".jpeg");
            if(thumbnailVideo.delete());

            else
                throw new CantDeleteFileException("can't delete the thumbnail");
        }
        else
            throw new CantDeleteFileException("cant delete the video");

    }
}
