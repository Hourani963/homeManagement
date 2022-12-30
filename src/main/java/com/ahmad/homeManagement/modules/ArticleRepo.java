package com.ahmad.homeManagement.modules;



import com.ahmad.homeManagement.modules.tabels.Article;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArticleRepo extends JpaRepository<Article, Long> {

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
}
