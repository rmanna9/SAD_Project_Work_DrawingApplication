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
        setupColorPickers(); 
        root.addEventHandler(MouseEvent.MOUSE_CLICKED, this::drawShape); // Add mouse click event handler
        model = new Model(); // Initialize the model

        // Set up the clip for the root pane
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(root.widthProperty());
        clip.heightProperty().bind(root.heightProperty());
        root.setClip(clip);
    }

    // Event handlers for Line selection
    @FXML
    private void onSelectLine() {
        model.setCurrentFactory(new LineFactory());
        highlightSelected(lineImageView);
        System.out.println("Line selected");
    }

    // Event handlers for Rectangle selection
    @FXML
    private void onSelectRectangle() {
        model.setCurrentFactory(new RectangleFactory());
        highlightSelected(rectangleImageView);
        System.out.println("Rectangle selected");
    }

    // Event handlers for Ellipse selection
    @FXML
    private void onSelectEllipse() {
        model.setCurrentFactory(new EllipseFactory());
        highlightSelected(ellipseImageView);
        System.out.println("Ellipse selected");
    }

    // Manging the highlight of the selected shape 
    private void highlightSelected(ImageView selected) {
        lineImageView.setStyle("");
        rectangleImageView.setStyle("");
        ellipseImageView.setStyle("");

        selected.setStyle("-fx-effect: dropshadow(three-pass-box, #00bfff, 10, 0, 0, 0);");
    }

    // Draw the shape on mouse click, using the factory pattern
    private void drawShape(MouseEvent event){
        double x = event.getX();
        double y = event.getY();

        ShapeInterface shape = model.createShape(x, y, 0, 0, borderColor, fillColor);
        Node node = shape.draw();
        root.getChildren().add(node);
    }
    
    // Set up the color pickers for border and fill colors
    private void setupColorPickers() {
    borderColorPicker.setValue(borderColor);
    fillColorPicker.setValue(fillColor);
    
    borderColorPicker.setOnAction(event -> {
        borderColor = borderColorPicker.getValue();
    });

    fillColorPicker.setOnAction(event -> {
        fillColor = fillColorPicker.getValue();
    });
}

    // Event handlers for Save and Load buttons
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
    
    public void clearPane() {
    root.getChildren().clear();
    }

}
