package com.sad.models;
import javafx.scene.paint.Color;

/**
 * Factory class for creating ConcreteRectangle objects.
 * Provides a method to instantiate rectangles with default width and height.
 */
public class RectangleFactory extends ShapeFactory {
    /** The default width for created rectangles. */
    private final double defaultWidth = 100;
    /** The default height for created rectangles. */
    private final double defaultHeight = 50; 

    /**
     * Creates a ConcreteRectangle with the specified position and colors,
     * using default width and height.
     *
     * @param x The x-coordinate of the rectangle's upper-left corner.
     * @param y The y-coordinate of the rectangle's upper-left corner.
     * @param width Ignored (default width is used).
     * @param height Ignored (default height is used).
     * @param borderColor The border color of the rectangle.
     * @param fillColor The fill color of the rectangle.
     * @return A new ConcreteRectangle instance.
     */
    @Override
    public ShapeInterface createShape(double x, double y, double width, double height, Color borderColor, Color fillColor) {                                   
        return new ConcreteRectangle(x, y, defaultWidth, defaultHeight, borderColor, fillColor);
    }

}
