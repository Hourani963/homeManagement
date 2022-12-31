package com.ahmad.homeManagement.modules.tabels;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Optional;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_cat", nullable = false)
    private Long idCat;
    private String nomCat;
    public Long getIdCat() {
        return idCat;
    }



    public String getNomCat() {
        return nomCat;
    }

    public void setNomCat(String nomCat) {
        this.nomCat = nomCat;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Article> article;

    public Collection<Article> getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article.add(article);
    }
}
