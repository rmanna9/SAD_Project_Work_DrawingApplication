package com.sad;


import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

import com.sad.models.*;
import com.sad.models.command.*;

public class Controller {


    @FXML private Pane root;
    @FXML private ColorPicker borderColorPicker;
    @FXML private ColorPicker fillColorPicker;
    @FXML private ImageView lineImageView;
    @FXML private ImageView rectangleImageView;
    @FXML private ImageView ellipseImageView;

    private Color borderColor = Color.BLACK;
    private Color fillColor = Color.TRANSPARENT;
    
    private Model model;

    public void initialize() {
        setupBorderColorPicker();
        setupFillColorPicker();
        root.addEventHandler(MouseEvent.MOUSE_CLICKED, this::drawShape);
        model = new Model();

        // Applica un clip per evitare che le forme escano dai limiti del Pane
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(root.widthProperty());
        clip.heightProperty().bind(root.heightProperty());
        root.setClip(clip);
    }

    @FXML
    private void onSelectLine() {
        model.setCurrentFactory(new LineFactory());
        highlightSelected(lineImageView);
        System.out.println("Line selected");
    }

    @FXML
    private void onSelectRectangle() {
        model.setCurrentFactory(new RectangleFactory());
        highlightSelected(rectangleImageView);
        System.out.println("Rectangle selected");
    }

    @FXML
    private void onSelectEllipse() {
        model.setCurrentFactory(new EllipseFactory());
        highlightSelected(ellipseImageView);
        System.out.println("Ellipse selected");
    }

    private void highlightSelected(ImageView selected) {
        lineImageView.setStyle("");
        rectangleImageView.setStyle("");
        ellipseImageView.setStyle("");

        selected.setStyle("-fx-effect: dropshadow(three-pass-box, #00bfff, 10, 0, 0, 0);");
    }

    private void drawShape(MouseEvent event){
        double x = event.getX();
        double y = event.getY();

        ShapeInterface shape = model.createShape(x, y, 0, 0, borderColor, fillColor);
        Node node = shape.draw();
        root.getChildren().add(node);
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
        model.setCommand(new SaveCommand(model.getShapes(), root));
        model.executeCommand();
    }
    @FXML
    private void onLoadButtonClick() {
        model.setCommand(new LoadCommand(model.getShapes(), root));
        model.executeCommand();
    }
    
}
