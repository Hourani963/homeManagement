package com.ahmad.homeManagement.modules.tabels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonManagedReference
    @JsonIgnore
    private Collection<Article> article;

    public void setArticle(Collection<Article> article) {
        this.article = article;
    }

    public Collection<Article> getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article.add(article);
    }

    public void removePerformer(Long idPerformer) {
        Article _performer = this.article.stream().filter(t -> t.getIdArt() == idPerformer).findFirst().orElse(null);
        if (_performer != null) {
            this.article.remove(_performer);
            _performer.getCategory().remove(this);
        }
       /* this.performer.remove(performer);
        performer.getCategory().remove(this);*/
    }
}
