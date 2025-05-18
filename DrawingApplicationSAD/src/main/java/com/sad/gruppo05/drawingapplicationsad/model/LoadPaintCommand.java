package com.sad.gruppo05.drawingapplicationsad.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;

public class LoadPaintCommand implements Command {
    private final Canvas canvas;

    public LoadPaintCommand(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void execute() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Carica immagine");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files", "*.png"));
        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
        }
    }
}