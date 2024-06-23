package com.caroux;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppliCreationFacture extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("AppliCreationFacture");
        stage.setScene(new Scene(new FenetreNouvelleFacture(), 1000, 650));
        stage.show();
        
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}
