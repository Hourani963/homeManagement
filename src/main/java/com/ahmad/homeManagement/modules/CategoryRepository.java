package com.ahmad.homeManagement.modules;

import com.ahmad.homeManagement.modules.tabels.Article;
import com.ahmad.homeManagement.modules.tabels.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT a FROM Category a WHERE a.nomCat = ?1")
    List<Category> findCatByName(String nomArticle);


    @Transactional
    @Modifying
    @Query("UPDATE Category c SET c.nomCat = :nomCat WHERE c.nomCat = :nomCatToChange")
    void changeName(String nomCatToChange, String nomCat);

    @Transactional
    @Modifying
    @Query("UPDATE Category c SET c.nomCat = :nomCat WHERE c.idCat = :idCat")
    void changeName(Long idCat, String nomCat);


}