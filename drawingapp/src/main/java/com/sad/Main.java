package com.sad;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class for the Drawing Application.
 * Launches the JavaFX application, loads the GUI from FXML, and sets up the main stage.
 */
public class Main extends Application {
    /**
     * Starts the JavaFX application.
     * Loads the FXML layout, retrieves the controller, sets up the scene,
     * and adds a key listener for the DELETE key to clear the drawing pane.
     *
     * @param stage The primary stage for this application.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("guiApp.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        Controller controller = fxmlLoader.getController();
        controller.attachKeyHandlerToScene(scene);

        stage.setScene(scene);
        stage.setMinHeight(725);
        stage.setMinWidth(1290);
        stage.show();
    }

    /**
     * Main entry point for the application.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        launch();
    }

}