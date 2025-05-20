package com.sad.models.command;

import com.sad.models.*;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public class LoadCommand implements CommandInterface {
    private final List<ShapeInterface> shapes;
    private final Pane pane;

    public LoadCommand(List<ShapeInterface> shapes, Pane pane) {
        this.shapes = shapes;
        this.pane = pane;
    }

    @Override
    public void execute() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Shapes");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());

        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                shapes.clear(); 
                pane.getChildren().clear();

                while ((line = reader.readLine()) != null) {
                    ShapeInterface shape = deserializeShape(line);
                    if (shape != null) {
                        shapes.add(shape);
                        Node node = shape.draw();
                        pane.getChildren().add(node);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ShapeInterface deserializeShape(String line) {
        String[] parts = line.split(" ");
        String type = parts[0];

        try {
            switch (type) {
                case "Rectangle":
                    double xR = Double.parseDouble(parts[1].replace(",", "."));
                    double yR = Double.parseDouble(parts[2].replace(",", "."));
                    double wR = Double.parseDouble(parts[3].replace(",", "."));
                    double hR = Double.parseDouble(parts[4].replace(",", "."));
                    Color borderR = stringToColor(parts[5]);
                    Color fillR = stringToColor(parts[6]);
                    return new ConcreteRectangle(xR, yR, wR, hR, borderR, fillR);

                case "Ellipse":
                    double xE = Double.parseDouble(parts[1].replace(",", "."));
                    double yE = Double.parseDouble(parts[2].replace(",", "."));
                    double wE = Double.parseDouble(parts[3].replace(",", "."));
                    double hE = Double.parseDouble(parts[4].replace(",", "."));
                    Color borderE = stringToColor(parts[5]);
                    Color fillE = stringToColor(parts[6]);
                    return new ConcreteEllipse(xE, yE, wE, hE, borderE, fillE);

                case "Line":
                    double x1 = Double.parseDouble(parts[1].replace(",", "."));
                    double y1 = Double.parseDouble(parts[2].replace(",", "."));
                    double x2 = Double.parseDouble(parts[3].replace(",", "."));
                    double y2 = Double.parseDouble(parts[4].replace(",", "."));
                    Color stroke = stringToColor(parts[5]);
                    return new ConcreteLine(x1, y1, x2, y2, stroke);

                default:
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Color stringToColor(String str) {
        if (str.equalsIgnoreCase("transparent")) {
            return Color.TRANSPARENT;
        }
        return Color.web(str);
    }
}
