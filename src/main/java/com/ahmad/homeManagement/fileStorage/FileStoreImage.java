package com.ahmad.homeManagement.fileStorage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;


public class FileStoreImage extends FileStoreLocal {

    private MultipartFile image;
    private Map<String ,String> imageMetadata;

    public FileStoreImage(String projectName) throws Exception {
        super(projectName);
        folderName = "images";
        creatFolder();
        System.out.println(this.pathAbsolutToResources+"\\"+folderName);
    }


    public void setImage(MultipartFile image) throws IOException {
        isImage(image);
        isFileEmpty(image);
        this.imageMetadata = extractMetaData(image);
        this.image = image;

        save(image.getOriginalFilename(), Optional.ofNullable(imageMetadata),image.getInputStream());
    }
    public void setImage(MultipartFile image, String folderName) throws IOException {
        isImage(image);
        isFileEmpty(image);
        this.imageMetadata = extractMetaData(image);
        this.image = image;
        this.folderName += "\\"+folderName;

        save(image.getOriginalFilename(), Optional.ofNullable(imageMetadata),image.getInputStream());
    }

    public void setImageMetadata(Map<String, String> imageMetadata) {
        this.imageMetadata = imageMetadata;
    }

    private void isImage(MultipartFile file) {
        System.err.println(file.getContentType());
        if (!Arrays.asList(
                ("image/jpeg"),
                ("image/png"),
                ("image/gif")).contains(file.getContentType()))
            throw new IllegalStateException("File must be an image [" + file.getContentType() + "]");
    }
}
