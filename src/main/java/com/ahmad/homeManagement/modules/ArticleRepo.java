package com.ahmad.homeManagement.modules;



import com.ahmad.homeManagement.modules.tabels.Article;
import com.ahmad.homeManagement.modules.tabels.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface ArticleRepo extends JpaRepository<Article, Long> {
    @Query("SELECT a FROM Article a")
    List<Article> findAllArt();
    @Query("SELECT a FROM Article a WHERE a.nom = ?1")
    List<Article> findArticleByName(String nomArticle);

    @Transactional
    @Modifying
    @Query("UPDATE Article a SET a.quantity = a.quantity + :quantity WHERE a.idArt = :idArt")
    void addQuantity(@Param("idArt") Long idArt, @Param("quantity") int quantity);

    @Transactional
    @Modifying
    @Query("UPDATE Article a SET a.quantity = a.quantity - :quantity WHERE a.idArt = :idArt")
    void removeQuantity(Long idArt, int quantity);

    @Transactional
    @Modifying
    @Query("UPDATE Article a SET a.photo_link =  :link WHERE a.idArt = :idArt")
    void addPhotoLink(Long idArt, String link);

    @Query("SELECT c FROM Category c JOIN c.article a WHERE a.idArt = :idArt")
    Collection<Category> getAllCatForArt(Long idArt);
    @Query("SELECT a FROM Article a WHERE a.idArt = :idArt")
    Article findPerformerById(Long idArt);
}
