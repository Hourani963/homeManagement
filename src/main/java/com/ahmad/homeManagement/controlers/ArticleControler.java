package com.ahmad.homeManagement.controlers;


import com.ahmad.homeManagement.modules.tabels.Article;
import com.ahmad.homeManagement.modules.tabels.Category;
import com.ahmad.homeManagement.services.ArticleService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("article")
@CrossOrigin(origins = "*")
public class ArticleControler {

    private final ArticleService articleService;

    @Autowired
    public ArticleControler(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<Article> getAllArticles(){
        return articleService.findAll();
    }

    @PostMapping("addArticle")
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addArticle(@RequestBody Article article){
        System.err.println(article.getNom());
        return articleService.save(article);
    }
    @PostMapping("addQuantityArticle/{idArt}")
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addQuantity(@PathVariable("idArt") Long idArt, @RequestBody Article article){
        return articleService.AddQuantity(idArt, article.getQuantity());
    }
    @PostMapping("removeQuantityArticle/{idArt}")
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeQuantity(@PathVariable("idArt") Long idArt, @RequestBody Article article){
        return articleService.removeQuantity(idArt, article.getQuantity());
    }


    @PostMapping(
            path = "{id}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String uploadArticleImage(@PathVariable("id") Long idArt,
                                            @RequestParam("file") MultipartFile file) throws Exception {
        return articleService.uploadArticleImageById(idArt,file);
    }

    @GetMapping(
            path = "{id}/image/download"
    )
    public ResponseEntity<byte[]> downloadArticleImage(@PathVariable("id") Long idArt) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(articleService.downloaddArticleImageById(idArt), headers, HttpStatus.OK);

    }

    @GetMapping(
            path = "{id}/image/downloadAll",
            produces = (MediaType.APPLICATION_JSON_VALUE)
    )
    public List<String> downloadArticleImages(@PathVariable("id") Long idArt) throws Exception {
        return articleService.downloaddArticleImages(idArt);
    }


    @GetMapping("{idArt}/getAllCat")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Collection<Category> getAllArticleForCat(@PathVariable("idArt") Long idArt){
        return articleService.getAllCatForArt(idArt);
    }

}
