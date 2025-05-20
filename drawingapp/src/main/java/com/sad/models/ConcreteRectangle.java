package com.sad.models;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Concrete implementation of ShapeInterface representing a rectangle.
 * Stores rectangle properties and provides drawing logic.
 */
public class ConcreteRectangle implements ShapeInterface {
    private Color borderColor, fillColor;
    private double x, y, width, height;

    /**
     * Constructs a ConcreteRectangle with the specified parameters.
     * @param x The x-coordinate of the rectangle's upper-left corner.
     * @param y The y-coordinate of the rectangle's upper-left corner.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @param borderColor The color of the rectangle border.
     * @param fillColor The fill color of the rectangle.
     */
    public ConcreteRectangle(double x, double y, double width, double height, Color borderColor, Color fillColor) {
        this.borderColor = borderColor;
        this.fillColor = fillColor;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    /**
     * Draws the rectangle as a JavaFX Node.
     * @return The JavaFX Rectangle node representing this shape.
     */
    @Override
    public Node draw() {
        Rectangle rectangle = new Rectangle(x, y, width, height);
        rectangle.setStroke(borderColor);
        rectangle.setFill(fillColor);
        rectangle.setStrokeWidth(3);
        return rectangle;
    }

    /**
     * Gets the border color of the rectangle.
     * @return The border color.
     */
    public Color getBorderColor() { return borderColor; }

    /**
     * Sets the border color of the rectangle.
     * @param borderColor The new border color.
     */
    public void setBorderColor(Color borderColor) { this.borderColor = borderColor; }

    /**
     * Gets the fill color of the rectangle.
     * @return The fill color.
     */
    public Color getFillColor() { return fillColor; }

    /**
     * Sets the fill color of the rectangle.
     * @param fillColor The new fill color.
     */
    public void setFillColor(Color fillColor) { this.fillColor = fillColor; }

    /**
     * Gets the width of the rectangle.
     * @return The width.
     */
    public double getWidth() { return width; }

    /**
     * Sets the width of the rectangle.
     * @param width The new width.
     */
    public void setWidth(double width) { this.width = width; }

    /**
     * Gets the height of the rectangle.
     * @return The height.
     */
    public double getHeight() { return height; }

    /**
     * Sets the height of the rectangle.
     * @param height The new height.
     */
    public void setHeight(double height) { this.height = height; }

    /**
     * Gets the x-coordinate of the rectangle's upper-left corner.
     * @return The x-coordinate.
     */
    public double getX() { return x; }

    /**
     * Sets the x-coordinate of the rectangle's upper-left corner.
     * @param x The new x-coordinate.
     */
    public void setX(double x) { this.x = x; }

    /**
     * Gets the y-coordinate of the rectangle's upper-left corner.
     * @return The y-coordinate.
     */
    public double getY() { return y; }

    /**
     * Sets the y-coordinate of the rectangle's upper-left corner.
     * @param y The new y-coordinate.
     */
    public void setY(double y) { this.y = y; }
}
