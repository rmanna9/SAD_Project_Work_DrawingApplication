package com.sad.gruppo05.drawingapplicationsad.model;

import javafx.stage.FileChooser;
import javafx.scene.layout.Pane;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Locale;

public class SavePaintCommand implements Command {

    private final List<Shape> shapes;
    private final Pane drawingPane;

    public SavePaintCommand(List<Shape> shapes, Pane drawingPane) {
        this.shapes = shapes;
        this.drawingPane = drawingPane;
    }

    @Override
    public void execute() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salva disegno");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
        File file = fileChooser.showSaveDialog(drawingPane.getScene().getWindow());

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Shape shape : shapes) {
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

    private String serializeShape(Shape shape) {
        java.util.function.Function<javafx.scene.paint.Color, String> colorToHex = c -> {
            if (c == null || c.getOpacity() == 0.0) return "null";
            return String.format("#%02X%02X%02X",
                    (int)(c.getRed() * 255),
                    (int)(c.getGreen() * 255),
                    (int)(c.getBlue() * 255));

        };

        if (shape instanceof ConcreteLine line) {
            return String.format(Locale.US, "LINE %.2f %.2f %.2f %.2f %s",
                    line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY(),
                    colorToHex.apply(line.getBorderColor()));
        } else if (shape instanceof ConcreteRectangle rect) {
            return String.format(Locale.US, "RECTANGLE %.2f %.2f %.2f %.2f %s %s",
                    rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(),
                    colorToHex.apply(rect.getBorderColor()),
                    colorToHex.apply(rect.getFillColor()));
        } else if (shape instanceof ConcreteEllipse ellipse) {
            return String.format(Locale.US, "ELLIPSE %.2f %.2f %.2f %.2f %s %s",
                    ellipse.getX(), ellipse.getY(), ellipse.getWidth(), ellipse.getHeight(),
                    colorToHex.apply(ellipse.getBorderColor()),
                    colorToHex.apply(ellipse.getFillColor()));
        }
        return null;
    }
}
