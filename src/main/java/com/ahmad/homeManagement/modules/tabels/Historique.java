package com.ahmad.homeManagement.modules.tabels;


import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Historique {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_histo", nullable = false)
    private Long idHisto;

    private Long idArticle;
    private int oldQuantity;
    private int newQuantity;
    private Date dateModif;

    public Historique(Long idArticle, int oldQuantity, int newQuantity, Date dateModif) {
        this.idArticle = idArticle;
        this.oldQuantity = oldQuantity;
        this.newQuantity = newQuantity;
        this.dateModif = dateModif;
    }

    public Historique() {
    }

    public Long getIdHisto() {
        return idHisto;
    }

    public Long getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Long idArticle) {
        this.idArticle = idArticle;
    }

    public int getOldQuantity() {
        return oldQuantity;
    }

    public void setOldQuantity(int oldQuantity) {
        this.oldQuantity = oldQuantity;
    }

    public int getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(int newQuantity) {
        this.newQuantity = newQuantity;
    }

    public Date getDateModif() {
        return dateModif;
    }

    public void setDateModif(Date dateModif) {
        this.dateModif = dateModif;
    }

}
