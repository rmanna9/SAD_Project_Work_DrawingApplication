package com.sad.models;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 * Concrete implementation of ShapeInterface representing an ellipse.
 * Stores ellipse properties and provides drawing logic.
 */
public class ConcreteEllipse implements ShapeInterface {
    private Color borderColor, fillColor;
    private double x, y, width, height;

    /**
     * Constructs a ConcreteEllipse with the specified parameters.
     * @param x The x-coordinate of the ellipse center.
     * @param y The y-coordinate of the ellipse center.
     * @param width The horizontal radius of the ellipse.
     * @param height The vertical radius of the ellipse.
     * @param borderColor The color of the ellipse border.
     * @param fillColor The fill color of the ellipse.
     */
    public ConcreteEllipse(double x, double y, double width, double height, Color borderColor, Color fillColor) {
        this.borderColor = borderColor;
        this.fillColor = fillColor;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    /**
     * Draws the ellipse as a JavaFX Node.
     * @return The JavaFX Ellipse node representing this shape.
     */
    @Override
    public Node draw() {
        Ellipse ellipse = new Ellipse(x, y, width, height);
        ellipse.setStroke(borderColor);
        ellipse.setFill(fillColor);
        ellipse.setStrokeWidth(3);
        return ellipse;
    }

    /**
     * Gets the border color of the ellipse.
     * @return The border color.
     */
    public Color getBorderColor() { return borderColor; }

    /**
     * Sets the border color of the ellipse.
     * @param borderColor The new border color.
     */
    public void setBorderColor(Color borderColor) { this.borderColor = borderColor; }

    /**
     * Gets the fill color of the ellipse.
     * @return The fill color.
     */
    public Color getFillColor() { return fillColor; }

    /**
     * Sets the fill color of the ellipse.
     * @param fillColor The new fill color.
     */
    public void setFillColor(Color fillColor) { this.fillColor = fillColor; }

    /**
     * Gets the horizontal radius (width) of the ellipse.
     * @return The width.
     */
    public double getWidth() { return width; }

    /**
     * Sets the horizontal radius (width) of the ellipse.
     * @param width The new width.
     */
    public void setWidth(double width) { this.width = width; }

    /**
     * Gets the vertical radius (height) of the ellipse.
     * @return The height.
     */
    public double getHeight() { return height; }

    /**
     * Sets the vertical radius (height) of the ellipse.
     * @param height The new height.
     */
    public void setHeight(double height) { this.height = height; }

    /**
     * Gets the x-coordinate of the ellipse center.
     * @return The x-coordinate.
     */
    public double getX() { return x; }

    /**
     * Sets the x-coordinate of the ellipse center.
     * @param x The new x-coordinate.
     */
    public void setX(double x) { this.x = x; }
    
    /**
     * Gets the y-coordinate of the ellipse center.
     * @return The y-coordinate.
     */
    public double getY() { return y; }

    /**
     * Sets the y-coordinate of the ellipse center.
     * @param y The new y-coordinate.
     */
    public void setY(double y) { this.y = y; }

}
