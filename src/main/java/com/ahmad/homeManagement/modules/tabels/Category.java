package com.ahmad.homeManagement.modules.tabels;

import jakarta.persistence.*;

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
}
