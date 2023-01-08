package com.ahmad.homeManagement.services;

import com.ahmad.homeManagement.modules.ArticleRepo;
import com.ahmad.homeManagement.modules.CategoryRepository;
import com.ahmad.homeManagement.modules.tabels.Article;
import com.ahmad.homeManagement.modules.tabels.Category;
import com.amazonaws.services.amplify.model.ResourceNotFoundException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ArticleRepo articleRepo;

    public CategoryService(CategoryRepository categoryRepository, ArticleRepo articleRepo) {
        this.categoryRepository = categoryRepository;
        this.articleRepo = articleRepo;
    }

    public List<Category> findAll() {
        return categoryRepository.findAllCat();
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

    public ResponseEntity<String> addCatToArticle(Long idCat, Long idArt) {
        try {
            Optional<Object> _cat = categoryRepository.findById(idCat).map(category -> {
                Article _article = articleRepo.findById(idArt)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + idArt));
                category.setArticle(_article);
                categoryRepository.save(category);
                return _article;

            });

            return ResponseEntity.status(HttpStatus.CREATED).body("Le categorie a été ajouté à l'article");


        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vérifier que les ids existe dans la bd");
        }
    }

    public Collection<Article> getAllArticleforCat(Long idCat) {
        return categoryRepository.getAllArticleForCat(idCat);

    }

    public ResponseEntity<String> removeCatToArticle(Long idCat, Long idPerformer) {
        try {
            Category category = categoryRepository.findById(idCat)
                    .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + idCat));

            category.removePerformer(idPerformer);
            categoryRepository.save(category);

            return ResponseEntity.status(HttpStatus.CREATED).body("Le categorie a été suprimmé à l'article");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vérifier que les ids existe dans la bd");
        }
    }
}
