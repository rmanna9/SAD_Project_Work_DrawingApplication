package com.sad.gruppo05.drawingapplicationsad;

import com.sad.gruppo05.drawingapplicationsad.model.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Controller {

    @FXML
    private Canvas canvas;
    @FXML
    private ColorPicker borderColorPicker;
    @FXML
    private ColorPicker fillColorPicker;
    private ShapeFactory currentFactory;
    private Color borderColor = Color.BLACK;
    private Color fillColor = Color.TRANSPARENT;

    @FXML
    public void initialize() {
        setupBorderColorPicker();
        setupFillColorPicker();
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, this::drawShape);
    }

    @FXML
    private void onSelectLine() {
        currentFactory = new LineFactory();
        System.out.println("Linea selezionata");
    }
    @FXML
    private void onSelectRectangle() {
        currentFactory = new RectangleFactory();
        System.out.println("Rect selezionata");
    }
    @FXML
    private void onSelectEllipse() {
        currentFactory = new EllipseFactory();
        System.out.println("ellipse selezionata");
    }
    private void setupBorderColorPicker(){
        borderColorPicker.setValue(borderColor);

        borderColorPicker.setOnAction(event -> {
            borderColor = borderColorPicker.getValue();
            System.out.println("Border color selezionato: " + borderColor);
        });
    }
    private void setupFillColorPicker(){
        fillColorPicker.setValue(fillColor);

        fillColorPicker.setOnAction(event -> {
            fillColor = fillColorPicker.getValue();
            System.out.println("Border color selezionato: " + fillColor);
        });
    }

    private void drawShape(MouseEvent event) {
        if (currentFactory == null) {
            System.out.println("Seleziona prima una forma dal menu!");
            return; // esci senza disegnare
        }
        double x = event.getX();
        double y = event.getY();

        Shape shape = currentFactory.createShape(x, y, 0, 0, borderColor, fillColor);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        shape.draw(gc);
    }

    @FXML
    private void onSaveButtonClick(){
        Command saveCommand = new SavePaintCommand(canvas);
        saveCommand.execute();
    }

}