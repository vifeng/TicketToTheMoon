package com.vf.tickettothemoon.administrators.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Prof {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profId;
    private String nom, prenom;

    public Prof() {
    }

    public Prof(Long profId, String nom, String prenom) {
        this.profId = profId;
        this.nom = nom;
        this.prenom = prenom;
    }


    public Prof( String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public Long getProfId() {
        return this.profId;
    }

    public void setProfId(Long profId) {
        this.profId = profId;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Prof profId(Long profId) {
        this.profId = profId;
        return this;
    }

    public Prof nom(String nom) {
        this.nom = nom;
        return this;
    }

    public Prof prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " profId='" + getProfId() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            "}";
    }


}