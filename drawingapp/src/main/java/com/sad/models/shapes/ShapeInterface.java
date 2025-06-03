package com.sad.models.shapes;

import com.sad.models.ShapeFactory;
import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * Interface for drawable shapes in the Drawing Application.
 * Any shape implementing this interface must provide methods for rendering as a JavaFX Node,
 * as well as methods for manipulating shape properties such as position, color, size, and rotation.
 */
public interface ShapeInterface {

    /**
     * Draws the shape as a JavaFX Node.
     * @return The JavaFX Node representing this shape.
     */
    Node draw();

    /**
     * Gets the x-coordinate of the shape.
     * @return The x-coordinate.
     */
    double getX();

    /**
     * Sets the x-coordinate of the shape.
     * @param x The new x-coordinate.
     */
    void setX(double x);

    /**
     * Gets the y-coordinate of the shape.
     * @return The y-coordinate.
     */
    double getY();

    /**
     * Sets the y-coordinate of the shape.
     * @param y The new y-coordinate.
     */
    void setY(double y);

    /**
     * Gets the JavaFX Node associated with this shape.
     * @return The JavaFX Node.
     */
    Node getNode();

    /**
     * Sets the JavaFX Node for this shape.
     * @param node The node to associate.
     */
    void setNode(Node node);

    /**
     * Gets the border color of the shape.
     * @return The border color.
     */
    Color getBorderColor();

    /**
     * Gets the fill color of the shape.
     * @return The fill color.
     */
    Color getFillColor();

    /**
     * Sets the border color of the shape.
     * @param color The new border color.
     */
    void setBorderColor(Color color);

    /**
     * Sets the fill color of the shape.
     * @param color The new fill color.
     */
    void setFillColor(Color color);

    /**
     * Gets the width of the shape.
     * @return The width.
     */
    double getWidth();

    /**
     * Gets the height of the shape.
     * @return The height.
     */
    double getHeight();

    /**
     * Sets the width of the shape.
     * @param width The new width.
     */
    void setWidth(double width);

    /**
     * Sets the height of the shape.
     * @param height The new height.
     */
    void setHeight(double height);

    /**
     * Creates and returns a copy of this shape.
     * @return A new ShapeInterface instance with the same properties.
     */
    ShapeInterface clone();

    /**
     * Gets the factory that can create shapes of this type.
     * @return The ShapeFactory instance.
     */
    ShapeFactory getShapeFactory();

    /**
     * Moves the shape to a new position.
     * Updates both the x and y coordinates.
     * @param x The new x-coordinate.
     * @param y The new y-coordinate.
     */
    void moveTo(double x, double y);

    /**
     * Gets the rotation angle of the shape.
     * @return The rotation angle in degrees.
     */
    double getAngle();

    /**
     * Sets the rotation angle of the shape.
     * @param newAngle The new rotation angle in degrees.
     */
    void setAngle(double newAngle);

    /**
     * Rotates the shape by a specified angle increment.
     * @param deltaAngle The angle increment in degrees.
     */
    void rotateBy(double deltaAngle);
    void mirrorX();
    void mirrorY();

}