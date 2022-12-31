package com.ahmad.homeManagement.services;

import com.ahmad.homeManagement.fileStorage.FileStoreImage;
import com.ahmad.homeManagement.modules.ArticleRepo;
import com.ahmad.homeManagement.modules.tabels.Article;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@Service
public class ArticleService {


    private final ArticleRepo articleRepo;
    private IFileStorage fileStorage ;


    public ArticleService(ArticleRepo articleRepo) throws Exception {
        this.articleRepo = articleRepo;
        this.fileStorage = new FileStoreImage("homeManagement");
    }


    public List<Article> findAll() {
        return articleRepo.findAll();
    }

    public ResponseEntity<String> save(Article article) {
        try {
            if(isArticleExist(article))
                articleRepo.save(article);
            else
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Article already existe");

        }catch (Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("errore while creating the article");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Created new article");
    }



    private boolean isArticleExist(Article article){
        return articleRepo.findArticleByName(article.getNom()).isEmpty();
    }

    public ResponseEntity<String> AddQuantity(Long idArt, int newQuantity) {

        try {
            Optional<Article> _article = articleRepo.findById(idArt);
            if(_article.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("L'article dont l'id = " + idArt + " n'existe pas");
            articleRepo.addQuantity(idArt, newQuantity);
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
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Quantity removed old Quantity = "+ _article.get().getQuantity() + " new Quantity = " + (_article.get().getQuantity()-quantity) );
        }catch (Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("errore while adding quantity to article id : " + idArt);
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("La valeur n'a pas été modifié");
    }

    public String uploadArticleImageById(Long idArt, MultipartFile file) throws Exception {
        System.err.println(file.getOriginalFilename());
        Optional<Article> article = articleRepo.findById(idArt);
        String photoLink = fileStorage.setImage(file, article.get().getNom());
        articleRepo.addPhotoLink(idArt,photoLink);
        return photoLink;
    }

    public byte[] downloaddArticleImageById(Long idArt) {
        Optional<Article> article = articleRepo.findById(idArt);

        return fileStorage.downloadByLink(article.get().getPhoto_link());
    }

    public List<String> downloaddArticleImages(Long idArt) {
        Optional<Article> article = articleRepo.findById(idArt);

        return fileStorage.listFilesForFolder(fileStorage.getPathAbsolutToResources()+"\\images\\"+article.get().getNom());
    }
}
