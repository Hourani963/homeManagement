package com.ahmad.homeManagement.controlers;


import com.ahmad.homeManagement.modules.tabels.Article;
import com.ahmad.homeManagement.services.ArticleService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public void uploadPerformerProfileImage(@PathVariable("id") Long idArt,
                                            @RequestParam("file") MultipartFile file){
        articleService.uploadArticleImageById(idArt,file);
    }

}
