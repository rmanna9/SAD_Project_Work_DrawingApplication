package com.sad.gruppo05.drawingapplicationsad;

import com.sad.gruppo05.drawingapplicationsad.model.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private ColorPicker borderColorPicker;
    @FXML
    private ColorPicker fillColorPicker;
    @FXML
    private Pane root;

    private ShapeFactory currentFactory;
    private Color borderColor = Color.BLACK;
    private Color fillColor = Color.TRANSPARENT;

    private final List<Shape> shapes = new ArrayList<>();

    @FXML
    public void initialize() {
        setupBorderColorPicker();
        setupFillColorPicker();
        root.addEventHandler(MouseEvent.MOUSE_CLICKED, this::drawShape);
    }

    @FXML
    private void onSelectLine() {
        currentFactory = new LineFactory();
        System.out.println("Linea selezionata");
    }

    @FXML
    private void onSelectRectangle() {
        currentFactory = new RectangleFactory();
        System.out.println("Rettangolo selezionato");
    }

    @FXML
    private void onSelectEllipse() {
        currentFactory = new EllipseFactory();
        System.out.println("Ellisse selezionata");
    }

    private void setupBorderColorPicker() {
        borderColorPicker.setValue(borderColor);
        borderColorPicker.setOnAction(event -> {
            borderColor = borderColorPicker.getValue();
            System.out.println("Colore bordo selezionato: " + borderColor);
        });
    }

    private void setupFillColorPicker() {
        fillColorPicker.setValue(fillColor);
        fillColorPicker.setOnAction(event -> {
            fillColor = fillColorPicker.getValue();
            System.out.println("Colore riempimento selezionato: " + fillColor);
        });
    }

    private void drawShape(MouseEvent event) {
        if (currentFactory == null) {
            System.out.println("Seleziona prima una forma dal menu!");
            return;
        }
        double x = event.getX();
        double y = event.getY();

        Shape shape = currentFactory.createShape(x, y, 0, 0, borderColor, fillColor);
        Node node = shape.createNode();

        // Aggiungo shape alla lista e al pane
        shapes.add(shape);
        root.getChildren().add(node);
    }

    @FXML
    private void onSaveButtonClick() {
        Command saveCommand = new SavePaintCommand(shapes, root);
        saveCommand.execute();
    }

    @FXML
    private void onLoadButtonClick() {
        Command loadCommand = new LoadPaintCommand(shapes, root);
        loadCommand.execute();
    }
}
