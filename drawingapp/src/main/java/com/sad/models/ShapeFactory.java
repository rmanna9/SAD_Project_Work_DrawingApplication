package com.sad.models;
import javafx.scene.paint.Color;

/**
 * Abstract factory class for creating shape objects.
 * Subclasses must implement the method to create specific shapes.
 */
public abstract class ShapeFactory {

    /**
     * Creates a shape with the specified parameters.
     *
     * @param x The x-coordinate for the shape.
     * @param y The y-coordinate for the shape.
     * @param width The width of the shape.
     * @param height The height of the shape.
     * @param border The border color of the shape.
     * @param fill The fill color of the shape.
     * @return A new instance of a class implementing ShapeInterface.
     */
    public abstract ShapeInterface createShape(double x, double y, double width, double height, Color border, Color fill);

}
