package com.sad;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("guiApp.fxml"));
        Parent root = fxmlLoader.load();

        Controller controller = fxmlLoader.getController(); // 🔥 ottieni il controller

        Scene scene = new Scene(root);

        // 🔥 Aggiungi l'ascoltatore per il tasto DELETE alla scena
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.DELETE) {
                controller.clearPane(); // chiama il metodo nel controller
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}