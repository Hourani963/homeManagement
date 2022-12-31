package com.ahmad.homeManagement.services;

import com.ahmad.homeManagement.modules.CategoryRepository;
import com.ahmad.homeManagement.modules.tabels.Article;
import com.ahmad.homeManagement.modules.tabels.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public ResponseEntity<String> addCategory(Category category) {
        try {
            if(isCatExist(category))
                categoryRepository.save(category);
            else
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Category already existe");

        }catch (Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("errore while creating the category");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Created new category");
    }

    private boolean isCatExist(Category category){
        return categoryRepository.findCatByName(category.getNomCat()).isEmpty();
    }

    public ResponseEntity<String> ChangeCatByName(String nomCatToChange, Category category) {
        try {
            List<Category> _article = categoryRepository.findCatByName(nomCatToChange);
            if(_article.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category dont le nom = " + nomCatToChange + " n'existe pas");
            categoryRepository.changeName(nomCatToChange,category.getNomCat());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("le nom category a été changé") ;
        }catch (Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("errore while changing the cat name");
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("le nom de Category n'a pas été modifié");
    }

    public ResponseEntity<String> ChangeCatById(Long idCat, Category category) {
        try {
            Optional<Category> _article = categoryRepository.findById(idCat);
            if(_article.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category dont l'id = " + idCat + " n'existe pas");
            categoryRepository.changeName(idCat,category.getNomCat());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("le nom category a été changé") ;
        }catch (Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("errore while changing the cat name");
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("le nom de Category n'a pas été modifié");
    }
}
