package com.ahmad.homeManagement.controlers;

import com.ahmad.homeManagement.modules.CategoryRepository;
import com.ahmad.homeManagement.modules.tabels.Article;
import com.ahmad.homeManagement.modules.tabels.Category;
import com.ahmad.homeManagement.services.CategoryService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("category")
@CrossOrigin(origins = "*")
public class CategoryControler {

    private final CategoryService categoryService;


    @Autowired
    public CategoryControler(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<Category> getAllCats(){
        return categoryService.findAll();
    }

    @PostMapping("addCat")
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }


    @PostMapping("{nomCat}/changeCatByName")
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changeCategoryByName(
            @PathVariable("nomCat") String nomCatToChange,
            @RequestBody Category category){
        return categoryService.ChangeCatByName(nomCatToChange,category);
    }
    @PostMapping("{idCat}/changeCatById")
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changeCategoryById(
            @PathVariable("idCat") Long idCat,
            @RequestBody Category category){
        return categoryService.ChangeCatById(idCat,category);
    }

    @GetMapping("{idCat}/addCatToArt/{idArt}")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addCatToArticle(
            @PathVariable("idCat") Long idCat,
            @PathVariable("idArt") Long idArt){
        return categoryService.addCatToArticle(idCat, idArt);
    }
    @GetMapping("{idCat}/getAllArticles")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Collection<Article> getAllArticleForCat(@PathVariable("idCat") Long idCat){
        return categoryService.getAllArticleforCat(idCat);
    }

    @GetMapping("{idCat}/removeCatToArt/{idArt}")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeCatToArticle(
            @PathVariable("idCat") Long idCat,
            @PathVariable("idArt") Long idArt){
        return categoryService.removeCatToArticle(idCat, idArt);
    }

}
