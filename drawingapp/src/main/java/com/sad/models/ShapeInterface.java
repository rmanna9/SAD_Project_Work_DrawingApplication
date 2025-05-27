package com.sad.models;
import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * Interface for drawable shapes in the Drawing Application.
 * Any shape implementing this interface must provide a method to return a JavaFX Node for rendering,
 * as well as methods for manipulating shape properties such as position, color, and size.
 */
public interface ShapeInterface {
    /**
     * Draws the shape as a JavaFX Node.
     * @return The JavaFX Node representing this shape.
     */
    public Node draw();

    /**
     * Gets the x-coordinate of the shape.
     * @return The x-coordinate.
     */
    public double getX();

    /**
     * Sets the x-coordinate of the shape.
     * @param x The new x-coordinate.
     */
    public void setX(double x);

    /**
     * Gets the y-coordinate of the shape.
     * @return The y-coordinate.
     */
    public double getY();

    /**
     * Sets the y-coordinate of the shape.
     * @param y The new y-coordinate.
     */
    public void setY(double y);

    /**
     * Gets the JavaFX Node associated with this shape.
     * @return The JavaFX Node.
     */
    public Node getNode();

    /**
     * Sets the JavaFX Node for this shape.
     * @param node The node to associate.
     */
    public void setNode(Node node);

    /**
     * Gets the border color of the shape.
     * @return The border color.
     */
    public Color getBorderColor();

    /**
     * Gets the fill color of the shape.
     * @return The fill color.
     */
    public Color getFillColor();

    /**
     * Sets the border color of the shape.
     * @param color The new border color.
     */
    public void setBorderColor(Color color);

    /**
     * Sets the fill color of the shape.
     * @param color The new fill color.
     */
    public void setFillColor(Color color);

    /**
     * Gets the width of the shape.
     * @return The width.
     */
    public double getWidth();

    /**
     * Gets the height of the shape.
     * @return The height.
     */
    public double getHeight();

    /**
     * Sets the width of the shape.
     * @param width The new width.
     */
    public void setWidth(double width);

    /**
     * Sets the height of the shape.
     * @param height The new height.
     */
    public void setHeight(double height);

    /**
     * Creates and returns a copy of this shape.
     * @return A new ShapeInterface instance with the same properties.
     */
    public ShapeInterface clone();

    /**
     * Gets the factory that can create shapes of this type.
     * @return The ShapeFactory instance.
     */
    public ShapeFactory getShapeFactory();
}