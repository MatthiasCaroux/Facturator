package com.caroux;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Facture {
    private String nom;
    private Map<String, Double> produits;


    public Facture(String nom, double prix) {
        this.nom = nom;
        this.produits = new HashMap<String,Double>();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Map<String, Double> getProduits() {
        return produits;
    }

    public double getPrix() {
        double prix = 0;
        for (Double p : produits.values()) {
            prix += p;
        }
        return prix;
    }

    public Set<String> getProduitsList() {
        return produits.keySet();
    }

}