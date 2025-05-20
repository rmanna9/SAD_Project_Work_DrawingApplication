package com.sad.models;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Concrete implementation of ShapeInterface representing a line.
 * Stores line properties and provides drawing logic.
 */
public class ConcreteLine implements ShapeInterface {
    private Color borderColor;
    private double x1, y1, x2, y2;

    /**
     * Constructs a ConcreteLine with the specified parameters.
     * @param x1 The starting x-coordinate of the line.
     * @param y1 The starting y-coordinate of the line.
     * @param x2 The ending x-coordinate of the line.
     * @param y2 The ending y-coordinate of the line.
     * @param borderColor The color of the line.
     */
    public ConcreteLine(double x1, double y1, double x2, double y2, Color borderColor) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.borderColor = borderColor;
    }

    /**
     * Draws the line as a JavaFX Node.
     * @return The JavaFX Line node representing this shape.
     */
    @Override
    public Node draw() {
        Line line = new Line(x1, y1, x2, y2);
        line.setStroke(borderColor);
        line.setStrokeWidth(3);
        return line;
    }

    /**
     * Gets the border color of the line.
     * @return The border color.
     */
    public Color getBorderColor() { return borderColor; }

    /**
     * Sets the border color of the line.
     * @param borderColor The new border color.
     */
    public void setBorderColor(Color borderColor) { this.borderColor = borderColor; }

    /**
     * Gets the starting x-coordinate of the line.
     * @return The starting x-coordinate.
     */
    public double getX1() { return x1; }

    /**
     * Sets the starting x-coordinate of the line.
     * @param x1 The new starting x-coordinate.
     */
    public void setX1(double x1) { this.x1 = x1; }

    /**
     * Gets the starting y-coordinate of the line.
     * @return The starting y-coordinate.
     */
    public double getY1() { return y1; }

    /**
     * Sets the starting y-coordinate of the line.
     * @param y1 The new starting y-coordinate.
     */
    public void setY1(double y1) { this.y1 = y1; }

    /**
     * Gets the ending x-coordinate of the line.
     * @return The ending x-coordinate.
     */
    public double getX2() { return x2; }

    /**
     * Sets the ending x-coordinate of the line.
     * @param x2 The new ending x-coordinate.
     */
    public void setX2(double x2) { this.x2 = x2; }

    /**
     * Gets the ending y-coordinate of the line.
     * @return The ending y-coordinate.
     */
    public double getY2() { return y2; }

    /**
     * Sets the ending y-coordinate of the line.
     * @param y2 The new ending y-coordinate.
     */
    public void setY2(double y2) { this.y2 = y2; }

}
