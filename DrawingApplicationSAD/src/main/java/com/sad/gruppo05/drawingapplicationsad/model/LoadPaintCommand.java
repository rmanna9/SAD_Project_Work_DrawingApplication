package com.sad.gruppo05.drawingapplicationsad.model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public class LoadPaintCommand implements Command {

    private final List<Shape> shapes;
    private final Pane drawingPane;

    public LoadPaintCommand(List<Shape> shapes, Pane drawingPane) {
        this.shapes = shapes;
        this.drawingPane = drawingPane;
    }

    @Override
    public void execute() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Carica disegno");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
        File file = fileChooser.showOpenDialog(drawingPane.getScene().getWindow());

        if (file != null) {
            shapes.clear();
            drawingPane.getChildren().clear();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    Shape shape = parseShape(line);
                    if (shape != null) {
                        shapes.add(shape);
                        Node node = shape.createNode();
                        drawingPane.getChildren().add(node);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Shape parseShape(String line) {
        try {
            String[] parts = line.split(" ");
            String type = parts[0];

            java.util.function.Function<String, Color> hexToColor = hex -> {
                if (hex.equalsIgnoreCase("null")) return Color.TRANSPARENT;
                return Color.web(hex);
            };

            // Helper per parsing robusto (virgola o punto come separatore decimale)
            java.util.function.Function<String, Double> parseDoubleSafe = s ->
                    Double.parseDouble(s.replace(",", "."));

            switch (type) {
                case "LINE": {
                    double x = parseDoubleSafe.apply(parts[1]);
                    double y = parseDoubleSafe.apply(parts[2]);
                    double x1 = parseDoubleSafe.apply(parts[3]);
                    double y1 = parseDoubleSafe.apply(parts[4]);
                    Color border = hexToColor.apply(parts[5]);
                    return new ConcreteLine(x, y, x1, y1, border);
                }
                case "RECTANGLE": {
                    double x = parseDoubleSafe.apply(parts[1]);
                    double y = parseDoubleSafe.apply(parts[2]);
                    double width = parseDoubleSafe.apply(parts[3]);
                    double height = parseDoubleSafe.apply(parts[4]);
                    Color border = hexToColor.apply(parts[5]);
                    Color fill = hexToColor.apply(parts[6]);
                    return new ConcreteRectangle(x, y, width, height, border, fill);
                }
                case "ELLIPSE": {
                    double x = parseDoubleSafe.apply(parts[1]);
                    double y = parseDoubleSafe.apply(parts[2]);
                    double width = parseDoubleSafe.apply(parts[3]);
                    double height = parseDoubleSafe.apply(parts[4]);
                    Color border = hexToColor.apply(parts[5]);
                    Color fill = hexToColor.apply(parts[6]);
                    return new ConcreteEllipse(x, y, width, height, border, fill);
                }
                default:
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
