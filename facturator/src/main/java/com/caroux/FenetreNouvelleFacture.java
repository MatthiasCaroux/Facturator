package com.caroux;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;

public class FenetreNouvelleFacture extends BorderPane {

    private VBox lesProduits;
    private List<Produit> produits;
    private Label totalLabel;

    public FenetreNouvelleFacture() {
        this.produits = new ArrayList<>();
        this.lesProduits = new VBox();
        this.totalLabel = new Label("Total: 0.00€");
        this.totalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        this.setStyle("-fx-background-color: #5da6e8;");
        this.setTop(this.getHBox());
        this.setCenter(this.getVbox());
    }

    public HBox getHBox() {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(20));
        hBox.setAlignment(Pos.CENTER);

        Button button = new Button("+");
        button.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 20px; -fx-background-radius: 15px;");

        TextField textField = new TextField();
        textField.setPromptText("Ajouter un produit");
        textField.setStyle("-fx-background-color: white; -fx-background-radius: 15px; -fx-font-size: 16px;");
        textField.setPrefWidth(300);

        TextField textField2 = new TextField();
        textField2.setPromptText("€");
        textField2.setStyle("-fx-background-color: white; -fx-background-radius: 15px; -fx-font-size: 16px;");
        textField2.setPrefWidth(100);

        button.setOnAction(new ControleurAjouterProduit(this, textField, textField2));

        hBox.getChildren().addAll(textField, textField2, button);
        return hBox;
    }

    public VBox getVbox() {
        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane(this.lesProduits);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-radius: 15px;");
        scrollPane.setPrefHeight(400);

        Button ajouterFacture = new Button("Enregistrer la facture");
        ajouterFacture.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 20px; -fx-background-radius: 15px;");
        ajouterFacture.setOnAction(new ControleurEnregistrerFature(this));

        vbox.getChildren().addAll(scrollPane, totalLabel, ajouterFacture);
        return vbox;
    }

    public void ajouteProduit(Produit produit) {
        this.produits.add(produit);
        updateTotal();
    }

    public void constructionProduit() {
        this.lesProduits.getChildren().clear();
        for (Produit produit : produits) {
            HBox hBox = new HBox();
            hBox.setSpacing(10);
            hBox.setAlignment(Pos.CENTER);
            String nom = produit.getNom();
            double prix = produit.getPrix();
            String prixString = String.format("%.2f€", prix);

            Label nomLabel = new Label(nom);
            nomLabel.setStyle("-fx-font-size: 16px;");
            Label prixLabel = new Label(prixString);
            prixLabel.setStyle("-fx-font-size: 16px;");

            HBox.setMargin(nomLabel, new Insets(0, 20, 0, 0));  // Add space between name and price

            hBox.getChildren().addAll(nomLabel, prixLabel);
            lesProduits.getChildren().add(hBox);
        }
    }

    private void updateTotal() {
        double total = produits.stream().mapToDouble(Produit::getPrix).sum();
        totalLabel.setText(String.format("Total: %.2f€", total));
    }

    public List<Produit> getProduits() {
        return produits;
    }
}
