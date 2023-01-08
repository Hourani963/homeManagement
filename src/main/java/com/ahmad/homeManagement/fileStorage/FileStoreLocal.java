package com.ahmad.homeManagement.fileStorage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import com.amazonaws.util.IOUtils;
import com.amazonaws.services.s3.model.ObjectMetadata;

import com.ahmad.homeManagement.services.IFileStorage;
import org.springframework.web.multipart.MultipartFile;

public abstract class FileStoreLocal implements IFileStorage {
    protected String pathAbsolutToResources;
    protected String folderName;
    public FileStoreLocal(String projectName) throws Exception {
        this.findPathAbsolutToResources(projectName);
    }

    public String findPathAbsolutToResources() {
        return pathAbsolutToResources;
    }

    public void save(String folderName, String fileName,
                     Optional<Map<String, String>> optionalMetadata,
                     InputStream inputStream) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();

        optionalMetadata.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(metadata::addUserMetadata);
            }
        });

        this.put(folderName, fileName, inputStream, metadata);
    }

    public void creatFolder(String folderName) throws IOException {

        if (!Files.exists(Path.of(pathAbsolutToResources+"\\"+ folderName))) {
            try {
                Files.createDirectories(Path.of(pathAbsolutToResources+"\\"+ folderName));
                System.out.println("Directory created in :" + pathAbsolutToResources+"\\"+ folderName);
            }catch (IOException e){
                System.err.println("****"+e);
            }
        }
    }


    private void put(String folderName, String fileName,
                     InputStream inputStream,
                     ObjectMetadata metadata) throws IOException {

        creatFolder(folderName);
        try{
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            File targetFile = new File(pathAbsolutToResources+"\\"+folderName+ "\\"+fileName);
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);
            outStream.close();

        } catch (IOException e) {
            System.err.println("errore in méthode (put) "+e);
        }
    }
    public byte[] downloadByLink(String absoluteLink) {

        InputStream targetStream = null;
        try {
            File file = new File(pathAbsolutToResources + "\\" + absoluteLink);
            targetStream = new FileInputStream(file);

            return IOUtils.toByteArray(targetStream);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to download file From Local", e);
        } finally {
            IOUtils.closeQuietly(targetStream,null);
        }

    }

    protected void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()){
            throw new IllegalStateException("Cannot upload an empty file.");
        }
    }
    protected Map<String, String> extractMetaData(MultipartFile file) {
        Map<String, String>  metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }


    /**
     * get the absolute path of the resources folder
     * @param projectName we need project name
     */
    private void findPathAbsolutToResources(String projectName) throws Exception {
        String pathTargetRessources = this.getClass().getResource("").toString();
        String postFix = projectName + "\\src\\main\\resources";
        String[] seperated = pathTargetRessources.split("/");
        int i = 1;
        String s = "";
        while (!seperated[i].equals(projectName)){
            s += seperated[i] + "\\";
            i++;
            if(i == seperated.length){
                throw new Exception("Ne peut pas oubtenir le chemin absolue -> Vérifier le nom de projet");
            }
        }
        s+= postFix;
        this.pathAbsolutToResources = s;
    }

    public List<String> listFilesForFolder(String articleNom, String ImageOrVideo) {
        File folder = new File(pathAbsolutToResources+"\\"+ImageOrVideo+"\\"+articleNom);
        List<String> listFiles = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                //listFilesForFolder(fileEntry);
            } else {
                listFiles.add(fileEntry.getName());
                System.out.println(fileEntry.getName());
            }
        }
        return listFiles;
    }

    public String getPathAbsolutToResources() {
        return pathAbsolutToResources;
    }


    public void setPathAbsolutToResources(String pathAbsolutToResources) {
        this.pathAbsolutToResources = pathAbsolutToResources;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
