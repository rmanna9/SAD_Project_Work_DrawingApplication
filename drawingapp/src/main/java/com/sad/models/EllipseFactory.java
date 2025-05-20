package com.sad.models;
import javafx.scene.paint.Color;

/**
 * Factory class for creating ConcreteEllipse objects.
 * Provides a method to instantiate ellipses with default width and height.
 */
public class EllipseFactory extends ShapeFactory {
    private final double defaultWidth = 100;
    private final double defaultHeight = 50;

    /**
     * Creates a ConcreteEllipse with the specified position and colors,
     * using default width and height.
     *
     * @param x The x-coordinate of the ellipse center.
     * @param y The y-coordinate of the ellipse center.
     * @param width Ignored (default width is used).
     * @param height Ignored (default height is used).
     * @param border The border color of the ellipse.
     * @param fill The fill color of the ellipse.
     * @return A new ConcreteEllipse instance.
     */
    @Override
    public ShapeInterface createShape(double x, double y, double width, double height, Color border, Color fill) {                            
        return new ConcreteEllipse(x, y, defaultWidth, defaultHeight, border, fill);
    }

}
