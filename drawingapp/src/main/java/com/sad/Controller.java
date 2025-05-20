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

/**
 * Controller class for the Drawing Application.
 * Manages user interactions, shape selection, color picking, and drawing logic.
 */
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

    /**
     * Initializes the controller.
     * Sets up color pickers, mouse event handlers, and the drawing area clip.
     */
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

    /**
     * Handles the selection of the Line tool.
     * Sets the current factory to LineFactory and highlights the selected icon.
     */
    @FXML
    private void onSelectLine() {
        model.setCurrentFactory(new LineFactory());
        highlightSelected(lineImageView);
        System.out.println("Line selected");
    }

    /**
     * Handles the selection of the Rectangle tool.
     * Sets the current factory to RectangleFactory and highlights the selected icon.
     */
    @FXML
    private void onSelectRectangle() {
        model.setCurrentFactory(new RectangleFactory());
        highlightSelected(rectangleImageView);
        System.out.println("Rectangle selected");
    }

    /**
     * Handles the selection of the Ellipse tool.
     * Sets the current factory to EllipseFactory and highlights the selected icon.
     */
    @FXML
    private void onSelectEllipse() {
        model.setCurrentFactory(new EllipseFactory());
        highlightSelected(ellipseImageView);
        System.out.println("Ellipse selected");
    }

    /**
     * Highlights the selected shape icon and removes highlight from others.
     * @param selected The ImageView to highlight.
     */
    private void highlightSelected(ImageView selected) {
        lineImageView.setStyle("");
        rectangleImageView.setStyle("");
        ellipseImageView.setStyle("");

        selected.setStyle("-fx-effect: dropshadow(three-pass-box, #00bfff, 10, 0, 0, 0);");
    }

    /**
     * Draws a shape at the mouse click position using the current factory and colors.
     * @param event MouseEvent containing the click coordinates.
     */
    private void drawShape(MouseEvent event){
        double x = event.getX();
        double y = event.getY();

        ShapeInterface shape = model.createShape(x, y, 0, 0, borderColor, fillColor);
        Node node = shape.draw();
        root.getChildren().add(node);
    }
    
    /**
     * Sets up the color pickers for border and fill colors.
     * Updates the controller's color fields on user selection.
     */
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

    /**
     * Handles the Save button click event.
     * Executes the SaveCommand to persist the current shapes.
     */
    @FXML
    private void onSaveButtonClick() {
        model.setCommand(new SaveCommand(model.getShapes(), root));
        model.executeCommand();
    }

    /**
     * Handles the Load button click event.
     * Executes the LoadCommand to load shapes into the pane.
     */
    @FXML
    private void onLoadButtonClick() {
        model.setCommand(new LoadCommand(model.getShapes(), root));
        model.executeCommand();
    }
    
    /**
     * Clears all shapes from the drawing pane.
     */
    public void clearPane() {
        root.getChildren().clear();
    }

}
