package com.ahmad.homeManagement.controlers;

import com.ahmad.homeManagement.modules.CategoryRepository;
import com.ahmad.homeManagement.modules.tabels.Article;
import com.ahmad.homeManagement.modules.tabels.Category;
import com.ahmad.homeManagement.services.CategoryService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  

}
