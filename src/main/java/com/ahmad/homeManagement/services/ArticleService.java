package com.ahmad.homeManagement.services;

import com.ahmad.homeManagement.fileStorage.FileStorageVideo;
import com.ahmad.homeManagement.fileStorage.FileStoreImage;
import com.ahmad.homeManagement.modules.ArticleRepo;
import com.ahmad.homeManagement.modules.HistoriqueRepository;
import com.ahmad.homeManagement.modules.tabels.Article;
import com.ahmad.homeManagement.modules.tabels.Category;
import com.ahmad.homeManagement.modules.tabels.Historique;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
public class ArticleService {


    private final ArticleRepo articleRepo;
    private final HistoriqueRepository historiqueRepository;
    private IFileStorage imageFileStorage;
    private IFileStorage videoFileStorage;


    public ArticleService(ArticleRepo articleRepo, HistoriqueRepository historiqueRepository) throws Exception {
        this.articleRepo = articleRepo;
        this.historiqueRepository = historiqueRepository;
        this.imageFileStorage = new FileStoreImage("homeManagement");
        this.videoFileStorage = new FileStorageVideo("homeManagement");
    }


    public List<Article> findAll() {
        return articleRepo.findAllArt();
    }

    public Article getPerformerById(Long idArt) {
        return articleRepo.findPerformerById(idArt);
    }
    public ResponseEntity<String> save(Article article) {
        Article _article = null;
        try {
            if (isArticleExist(article)) {
                _article = articleRepo.save(article);
                historisation(_article.getIdArt(), 0, _article.getQuantity());
            } else
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Article already existe");

        } catch (Exception e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("errore while creating the article");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(_article.getIdArt().toString());
    }



    private boolean isArticleExist(Article article){
        return articleRepo.findArticleByName(article.getNom()).isEmpty();
    }

    public ResponseEntity<String> AddQuantity(Long idArt, int newQuantity) {

        try {
            Optional<Article> _article = articleRepo.findById(idArt);
            if(_article.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("L'article dont l'id = " + idArt + " n'existe pas");
            articleRepo.addQuantity(idArt, newQuantity);
            historisation(idArt,_article.get().getQuantity(),_article.get().getQuantity()+newQuantity);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Quantity added old Quantity = "+ _article.get().getQuantity() + " new Quantity = " + (_article.get().getQuantity()+newQuantity) );
        }catch (Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("errore while adding quantity to article id : " + idArt);
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("La valeur n'a pas été modifié");
    }

    public ResponseEntity<String> removeQuantity(Long idArt, int quantity) {
        try {
            Optional<Article> _article = articleRepo.findById(idArt);

            if(_article.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("L'article dont l'id = " + idArt + " n'existe pas");
            if(_article.get().getQuantity() - quantity < 0) return ResponseEntity.status(HttpStatus.CONFLICT).body("Quantity ne peut pas être négative");
            articleRepo.removeQuantity(idArt, quantity);
            historisation(idArt,_article.get().getQuantity(),_article.get().getQuantity()-quantity);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Quantity removed old Quantity = "+ _article.get().getQuantity() + " new Quantity = " + (_article.get().getQuantity()-quantity) );
        }catch (Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("errore while adding quantity to article id : " + idArt);
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("La valeur n'a pas été modifié");
    }

    public String uploadArticleImageById(Long idArt, MultipartFile file, boolean isArticleProfilePhoto) throws Exception {
        Optional<Article> article = articleRepo.findById(idArt);
        String photoLink = imageFileStorage.setImage(file, article.get().getNom());
        if(isArticleProfilePhoto)
            articleRepo.addPhotoLink(idArt,photoLink);
        return photoLink;
    }

    public byte[] downloadArticleProfileImage(Long idArt) {
        Optional<Article> article = articleRepo.findById(idArt);

        return imageFileStorage.downloadByLink("images\\"+article.get().getPhoto_link());
    }
    public byte[] downloaddArticleImage(Long idArt, String nomImage) {
        Optional<Article> article = articleRepo.findById(idArt);
        return imageFileStorage.downloadByLink("images\\"+article.get().getNom()+"\\"+nomImage);
    }

    public List<String> downloaddArticleImages(Long idArt) {
        Optional<Article> article = articleRepo.findById(idArt);

        return imageFileStorage.listFilesForFolder(article.get().getNom(),"images");
    }

    public Collection<Category> getAllCatForArt(Long idArt) {
        return articleRepo.getAllCatForArt(idArt);
    }

    private void historisation(Long idArt, int oldQuantity, int newQuantity){
        Historique historique = new Historique(idArt,oldQuantity,newQuantity, new Date());
        historiqueRepository.save(historique);
    }

    public String uploadArticleVideo(Long idArt, MultipartFile file) throws IOException, FrameGrabber.Exception {
        Optional<Article> article = articleRepo.findById(idArt);
        String videoLing = videoFileStorage.setVideo(file, article.get().getNom());
        videoFileStorage.creatFolder("videos\\"+article.get().getNom()+"\\thumbnail");
        this.getImageFromVideo(videoFileStorage.getPathAbsolutToResources()+"\\videos\\"+videoLing,
                videoFileStorage.getPathAbsolutToResources()+"\\videos\\"+article.get().getNom()+"\\thumbnail\\"+file.getOriginalFilename());

        return videoLing;
    }
    private void getImageFromVideo(String videoPath, String saveImagePath) throws FrameGrabber.Exception, IOException {
        FFmpegFrameGrabber g = new FFmpegFrameGrabber(videoPath);
        g.start();

        Java2DFrameConverter converter = new Java2DFrameConverter();
        g.setFrameNumber(g.getLengthInFrames()/2);
        g.grab();
        // (i) is the number of photos tha I need
        for (int i = 500 ; i < 501 ; i++) {

            Frame frame = g.grabImage(); // It is important to use grabImage() to get a frame that can be turned into a BufferedImage

            BufferedImage bi = converter.convert(frame);

            ImageIO.write(bi, "jpeg", new File(saveImagePath+ ".jpeg"));
        }

        g.stop();
        g = new FFmpegFrameGrabber("");
    }
    public List<String> downloaddArticleVideos(Long idArt) {
        Optional<Article> article = articleRepo.findById(idArt);
        return videoFileStorage.listFilesForFolder(article.get().getNom(),"videos");
    }

    public byte[] downloadArticleVideo(Long idArt, String videoFileName) {
        Optional<Article> article = articleRepo.findById(idArt);

        return videoFileStorage.downloadByLink("videos\\"+article.get().getNom()+"\\"+videoFileName);
    }

    public byte[] downloadPerformerVidoThumbnail(String nomPerformer, String nomVideoORImage, boolean isThumbnail) {
        if (isThumbnail)
            return imageFileStorage.downloadByLink("videos\\"+nomPerformer+"\\thumbnail\\"+nomVideoORImage+".jpeg");
        else
            return imageFileStorage.downloadByLink("images\\"+nomPerformer+"\\"+nomVideoORImage);
    }


}
