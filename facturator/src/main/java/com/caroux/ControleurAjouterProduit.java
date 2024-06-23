package com.caroux;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ControleurAjouterProduit implements EventHandler<ActionEvent> {

    private FenetreNouvelleFacture fenetreNouvelleFacture;
    private TextField textField;
    private TextField textField2;

    public ControleurAjouterProduit(FenetreNouvelleFacture fenetreNouvelleFacture, TextField textField, TextField textField2) {
        this.fenetreNouvelleFacture = fenetreNouvelleFacture;
        this.textField = textField;
        this.textField2 = textField2;
    }

    @Override
    public void handle(ActionEvent event) {
        System.out.println("Ajout produit");
        if (textField.getText().isEmpty() || textField2.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir les champs obligatoires");
            alert.showAndWait();
        } else {
            try {
                double prix = Double.parseDouble(textField2.getText());
                Produit produit = new Produit(textField.getText(), prix);
                fenetreNouvelleFacture.ajouteProduit(produit);
                fenetreNouvelleFacture.constructionProduit();
                textField.clear();
                textField2.clear();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez entrer un prix valide");
                alert.showAndWait();
            }
        }
    }
    
}