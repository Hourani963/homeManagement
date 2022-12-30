package com.ahmad.homeManagement.modules.tabels;


import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;


@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_art", nullable = false)
    private Long idArt;

    private String nom;
    @Positive
    private int quantity;
    private String description;
    private String photo_link;

    public Long getIdArt() {
        return idArt;
    }

    public void setIdArt(Long idArt) {
        this.idArt = idArt;
    }



    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto_link() {
        return photo_link;
    }

    public void setPhoto_link(String photo_link) {
        this.photo_link = photo_link;
    }
}
