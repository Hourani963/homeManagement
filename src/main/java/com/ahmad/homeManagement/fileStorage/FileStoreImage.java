package com.ahmad.homeManagement.fileStorage;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;


public class FileStoreImage extends FileStoreLocal {

    private MultipartFile image;
    private Map<String ,String> imageMetadata;

    public FileStoreImage(String projectName) throws Exception {
        super(projectName);
        folderName = "images";
        creatFolder(folderName);
        System.out.println(this.pathAbsolutToResources+"\\"+folderName);
    }


    public void setImage(MultipartFile image) throws IOException {
        isImage(image);
        isFileEmpty(image);
        this.imageMetadata = extractMetaData(image);
        this.image = image;

        save(folderName,image.getOriginalFilename(), Optional.ofNullable(imageMetadata),image.getInputStream());
    }
    public String setImage(MultipartFile image, String folderName) throws IOException {
        isImage(image);
        isFileEmpty(image);
        this.imageMetadata = extractMetaData(image);
        this.image = image;
        //this.folderName += "\\"+folderName;

        save(this.folderName+"\\"+ folderName, image.getOriginalFilename(), Optional.ofNullable(imageMetadata),image.getInputStream());

        return folderName +"\\"+ image.getOriginalFilename();
    }

    @Override
    public String setVideo(MultipartFile image, String folderName) {

        return folderName;
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

    @Override
    public void deleteFile(String nomPerformer, String nomImage) throws CantDeleteFileException, IOException {
        System.err.println(pathAbsolutToResources+"\\images\\"+ nomPerformer + "\\"+ nomImage);
        //File image = new File(pathAbsolutToResources+"\\images\\"+ nomPerformer + "\\"+ nomImage);

        try {
            Files.delete(Path.of(pathAbsolutToResources + "\\images\\" + nomPerformer + "\\" + nomImage));
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", pathAbsolutToResources+"\\images\\"+ nomPerformer + "\\"+ nomImage);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", pathAbsolutToResources+"\\images\\"+ nomPerformer + "\\"+ nomImage);
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
            throw new CantDeleteFileException("cant delete image");
        }

    }
}
