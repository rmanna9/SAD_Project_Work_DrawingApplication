package com.sad.models.shapes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.sad.models.RectangleFactory;
import com.sad.models.ShapeFactory;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Concrete implementation of ShapeInterface representing a rectangle.
 * Stores rectangle properties and provides drawing logic.
 * Implements Serializable to support saving/loading functionality.
 */
public class ConcreteRectangle implements ShapeInterface, Serializable {
    /** The border color of the rectangle. */
    private transient Color borderColor, fillColor;
    /** The x-coordinate of the rectangle's upper-left corner. */
    private double x;
    /** The y-coordinate of the rectangle's upper-left corner. */
    private double y;
    /** The width of the rectangle. */
    private double width;
    /** The height of the rectangle. */
    private double height;
    /** The JavaFX Rectangle node representing this shape. */
    private transient Rectangle rectNode;
    /** The rotation angle of the rectangle in degrees. */
    private double angle = 0;


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
     * Creates a new Rectangle node if none exists, or returns the existing one.
     * @return The JavaFX Rectangle node representing this shape.
     */
    @Override
    public Node draw() {
        if (rectNode == null) {
            rectNode = new Rectangle(0, 0, width, height);
            rectNode.setLayoutX(x);
            rectNode.setLayoutY(y);
            rectNode.setStroke(borderColor);
            rectNode.setFill(fillColor);
            rectNode.setStrokeWidth(3);
            setAngle(angle);
            rectNode.setUserData(this);
        }
        return rectNode;
    }

    /**
     * Sets the rotation angle of the rectangle.
     * Updates the visual representation if the node exists.
     * @param angle The new rotation angle in degrees.
     */
    @Override
    public void setAngle(double angle) {
        this.angle = angle;
        if (rectNode != null) {
            rectNode.setRotate(angle);
        }
    }

    /**
     * Gets the rotation angle of the rectangle.
     * @return The rotation angle in degrees.
     */
    @Override
    public double getAngle() {
        return this.angle;
    }

    /**
     * Rotates the rectangle by a specified angle increment.
     * @param deltaAngle The angle increment in degrees.
     */
    @Override
    public void rotateBy(double deltaAngle) {
        setAngle(this.angle + deltaAngle);
    }

    @Override
    public void mirrorX() {
        return;
    }
    
    @Override
    public void mirrorY(){
        return;
    }

    /**
     * Returns the JavaFX Node associated with this rectangle.
     * @return The Rectangle node.
     */
    @Override
    public Node getNode() {
        return this.rectNode;
    }

    /**
     * Sets the JavaFX Node for this rectangle.
     * Throws IllegalArgumentException if the node is not a Rectangle.
     * @param node The node to associate.
     * @throws IllegalArgumentException if the node is not a Rectangle.
     */
    @Override
    public void setNode(Node node) {
        if (node instanceof Rectangle) {
            this.rectNode = (Rectangle) node;
        } else {
            throw new IllegalArgumentException("Node must be an instance of Rectangle");
        }
    }

    /**
     * Creates and returns a copy of this rectangle.
     * @return A new ConcreteRectangle with the same properties.
     */
    @Override
    public ShapeInterface clone() {
        ConcreteRectangle copy = new ConcreteRectangle(x, y, width, height, borderColor, fillColor);
        copy.setAngle(angle);
        return copy;
    }

    /**
     * Converts a Color object to its string representation.
     * Used for serialization.
     * @param color The color to convert.
     * @return A string representing the color's RGBA values, or null if the color is null.
     */
    private static String colorToString(Color color) {
        if (color == null) return null;
        return color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "," + color.getOpacity();
    }

    /**
     * Converts a string representation back to a Color object.
     * Used for deserialization.
     * @param str The string to convert.
     * @return A Color object, or null if the input string is null.
     */
    private static Color stringToColor(String str) {
        if (str == null) return null;
        String[] parts = str.split(",");
        return new Color(
            Double.parseDouble(parts[0]),
            Double.parseDouble(parts[1]),
            Double.parseDouble(parts[2]),
            Double.parseDouble(parts[3])
        );
    }

    /**
     * Custom serialization method.
     * Converts colors to their string representations for storage.
     * @param out The ObjectOutputStream to write to.
     * @throws IOException If an I/O error occurs.
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(colorToString(borderColor));
        out.writeObject(colorToString(fillColor));
    }

    /**
     * Custom deserialization method.
     * Recreates the transient JavaFX node and restores colors from their string representations.
     * @param in The ObjectInputStream to read from.
     * @throws IOException If an I/O error occurs.
     * @throws ClassNotFoundException If the class of a serialized object cannot be found.
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        borderColor = stringToColor((String) in.readObject());
        fillColor = stringToColor((String) in.readObject());

        this.rectNode = new Rectangle(0, 0, width, height);
        this.rectNode.setLayoutX(x);
        this.rectNode.setLayoutY(y);
        this.rectNode.setStroke(borderColor);
        this.rectNode.setFill(fillColor);
        this.rectNode.setStrokeWidth(3);
        setAngle(angle);
        this.rectNode.setUserData(this);
    }

    /**
     * Moves the rectangle to a new position.
     * Updates both the internal coordinates and the visual representation.
     * @param x The new x-coordinate.
     * @param y The new y-coordinate.
     */
    @Override
    public void moveTo(double x, double y) {
        this.x = x;
        this.y = y;

        if (rectNode != null) {
            rectNode.setLayoutX(x);
            rectNode.setLayoutY(y);
        }
    }

    /**
     * Returns the factory for creating rectangles.
     * @return The RectangleFactory instance.
     */
    @Override
    public ShapeFactory getShapeFactory() {
        return new RectangleFactory();
    }

    /**
     * Gets the border color of the rectangle.
     * @return The border color.
     */
    @Override
    public Color getBorderColor() {
        return borderColor;
    }

    /**
     * Sets the border color of the rectangle.
     * Updates the visual representation if the node exists.
     * @param borderColor The new border color.
     */
    @Override
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        if (rectNode != null) {
            rectNode.setStroke(borderColor);
        }
    }

    /**
     * Gets the fill color of the rectangle.
     * @return The fill color.
     */
    @Override
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * Sets the fill color of the rectangle.
     * Updates the visual representation if the node exists.
     * @param fillColor The new fill color.
     */
    @Override
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        if (rectNode != null) {
            rectNode.setFill(fillColor);
        }
    }

    /**
     * Gets the width of the rectangle.
     * @return The width.
     */
    @Override
    public double getWidth() {
        return width;
    }

    /**
     * Sets the width of the rectangle.
     * Updates the visual representation if the node exists.
     * @param width The new width.
     */
    @Override
    public void setWidth(double width) {
        this.width = width;
        if (rectNode != null) {
            rectNode.setWidth(width);
        }
    }

    /**
     * Gets the height of the rectangle.
     * @return The height.
     */
    @Override
    public double getHeight() {
        return height;
    }

    /**
     * Sets the height of the rectangle.
     * Updates the visual representation if the node exists.
     * @param height The new height.
     */
    @Override
    public void setHeight(double height) {
        this.height = height;
        if (rectNode != null) {
            rectNode.setHeight(height);
        }
    }

    /**
     * Gets the x-coordinate of the rectangle's upper-left corner.
     * @return The x-coordinate.
     */
    @Override
    public double getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the rectangle's upper-left corner.
     * Updates the visual representation if the node exists.
     * @param x The new x-coordinate.
     */
    @Override
    public void setX(double x) {
        this.x = x;
        if (rectNode != null) {
            rectNode.setLayoutX(x);
        }
    }

    /**
     * Gets the y-coordinate of the rectangle's upper-left corner.
     * @return The y-coordinate.
     */
    @Override
    public double getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the rectangle's upper-left corner.
     * Updates the visual representation if the node exists.
     * @param y The new y-coordinate.
     */
    @Override
    public void setY(double y) {
        this.y = y;
        if (rectNode != null) {
            rectNode.setLayoutY(y);
        }
    }
}
