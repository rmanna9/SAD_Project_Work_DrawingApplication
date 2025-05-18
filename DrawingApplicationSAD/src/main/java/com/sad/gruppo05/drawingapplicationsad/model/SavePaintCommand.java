package com.sad.gruppo05.drawingapplicationsad.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;




public class SavePaintCommand implements Command{
    private final Canvas canvas;

    public SavePaintCommand(Canvas canvas){
        this.canvas = canvas;
    }

    @Override
    public void execute(){
        WritableImage image = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(null, image);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salva immagine");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files", "*.png"));

        File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());
        if (file != null){
            try{
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
