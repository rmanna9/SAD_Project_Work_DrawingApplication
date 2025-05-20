package com.sad.models;
import javafx.scene.Node;

/**
 * Interface for drawable shapes in the Drawing Application.
 * Any shape implementing this interface must provide a method to return a JavaFX Node for rendering.
 */
public interface ShapeInterface {
    /**
     * Draws the shape as a JavaFX Node.
     * @return The JavaFX Node representing this shape.
     */
    public Node draw();   
}
