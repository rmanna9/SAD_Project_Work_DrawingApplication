package com.sad;


import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

import com.sad.models.*;
import com.sad.models.command.*;

public class Controller {

    private ShapeFactory currentFactory;

    @FXML 
    private Pane root;
    @FXML
    private ColorPicker borderColorPicker;
    @FXML
    private ColorPicker fillColorPicker;
    private Color borderColor = Color.BLACK;
    private Color fillColor = Color.TRANSPARENT;
    private final List<ShapeInterface> shapes = new ArrayList<>();


    public void initialize() {
        setupBorderColorPicker();
        setupFillColorPicker();
        root.addEventHandler(MouseEvent.MOUSE_CLICKED, this::drawShape);
    }

    @FXML
    private void onSelectLine(){
        currentFactory = new LineFactory();
        System.out.println("Line selected");
    }

    @FXML
    private void onSelectRectangle(){
        currentFactory = new RectangleFactory();
        System.out.println("Rectangle selected");
    }

    @FXML
    private void onSelectEllipse(){
        currentFactory = new EllipseFactory();
        System.out.println("Ellipse selected");
    }

    private void drawShape(MouseEvent event){
        if(currentFactory != null) {
            double x = event.getX();
            double y = event.getY();

            ShapeInterface shape = currentFactory.createShape(x, y, 0, 0, borderColor, fillColor);
            Node node = shape.draw();
            root.getChildren().add(node);
            shapes.add(shape);
        }
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

    @FXML
    private void onSaveButtonClick() {
        CommandInterface saveCommand = new SaveCommand(shapes, root);
        saveCommand.execute();
    }
    @FXML
    private void onLoadButtonClick() {
        CommandInterface loadCommand = new LoadCommand(shapes, root);
        loadCommand.execute();
    }
    
}
