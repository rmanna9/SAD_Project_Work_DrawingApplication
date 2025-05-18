package com.sad.models.command;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import com.sad.models.ConcreteEllipse;
import com.sad.models.ConcreteLine;
import com.sad.models.ConcreteRectangle;
import com.sad.models.ShapeInterface;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;



public class SaveCommand implements CommandInterface {
    private final List<ShapeInterface> shapes;
    private final Pane pane;

    public SaveCommand(List<ShapeInterface> shapes, Pane pane) {
        this.shapes = shapes;
        this.pane = pane;
    }


    @Override
    public void execute() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Shapes");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
        File file = fileChooser.showSaveDialog(pane.getScene().getWindow());

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (ShapeInterface shape : shapes) {
                    String line = serializeShape(shape);
                    if (line != null) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String serializeShape(ShapeInterface shape) {
        if (shape instanceof ConcreteRectangle) {
            ConcreteRectangle rectangle = (ConcreteRectangle) shape;
            return String.format("Rectangle %f %f %f %f %s %s",
                    rectangle.getX(), rectangle.getY(),
                    rectangle.getWidth(), rectangle.getHeight(),
                    colorToString(rectangle.getBorderColor()),
                    colorToString(rectangle.getFillColor()));
        } else if (shape instanceof ConcreteEllipse) {
            ConcreteEllipse ellipse = (ConcreteEllipse) shape;
            return String.format("Ellipse %f %f %f %f %s %s",
                    ellipse.getX(), ellipse.getY(),
                    ellipse.getWidth(), ellipse.getHeight(),
                    colorToString(ellipse.getBorderColor()),
                    colorToString(ellipse.getFillColor()));
        } else if (shape instanceof ConcreteLine){
            ConcreteLine line = (ConcreteLine) shape;
            return String.format("Line %f %f %f %f %s",
                    line.getX1(), line.getY1(),
                    line.getX2(), line.getY2(),
                    colorToString(line.getBorderColor()));
        } else {
            return null;
        }
      
    }

    private String colorToString(Color color) {
    if (color.getOpacity() == 0.0) {
        return "transparent";
    }
    return String.format("#%02X%02X%02X",
            (int) (color.getRed() * 255),
            (int) (color.getGreen() * 255),
            (int) (color.getBlue() * 255));
}


}
