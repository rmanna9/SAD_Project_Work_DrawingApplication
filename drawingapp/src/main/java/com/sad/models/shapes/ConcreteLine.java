package com.sad.models.shapes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.sad.models.LineFactory;
import com.sad.models.ShapeFactory;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Concrete implementation of ShapeInterface representing a line.
 * Stores line properties and provides drawing logic.
 * Implements Serializable to support saving/loading functionality.
 */
public class ConcreteLine implements ShapeInterface, Serializable {
    /** The border color of the line. */
    private transient Color borderColor;
    /** The starting x-coordinate of the line. */
    private double x1;
    /** The starting y-coordinate of the line. */
    private double y1;
    /** The ending x-coordinate of the line. */
    private double x2;
    /** The ending y-coordinate of the line. */
    private double y2;
    /** The JavaFX Line node representing this shape. */
    private transient Line lineNode;
    /** The rotation angle of the line in degrees. */
    private double angle = 0;

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
        if (lineNode == null) {
            lineNode = new Line(x1, y1, x2, y2);
            lineNode.setStroke(borderColor);
            lineNode.setStrokeWidth(3);
            setAngle(angle);
            lineNode.setUserData(this);
        }
        return lineNode;
    }

    /**
     * Sets the rotation angle of the line.
     * @param angle The new rotation angle in degrees.
     */
    @Override
    public void setAngle(double angle) {
        this.angle = angle;
        if (lineNode != null) {
            lineNode.setRotate(angle);
        }
    }

    /**
     * Gets the rotation angle of the line.
     * @return The rotation angle in degrees.
     */
    @Override
    public double getAngle() {
        return angle;
    }

    /**
     * Rotates the line by a specified angle increment.
     * @param deltaAngle The angle increment in degrees.
     */
    @Override
    public void rotateBy(double deltaAngle) {
        setAngle(this.angle + deltaAngle);
    }
    @Override
    public void mirrorX(){

    }
    @Override
    public void mirrorY(){
        return;
    }
    /**
     * Returns the JavaFX Node associated with this line.
     * @return The Line node.
     */
    @Override
    public Node getNode() {
        return this.lineNode;
    }

    /**
     * Sets the JavaFX Node for this line.
     * @param node The node to associate.
     * @throws IllegalArgumentException if the node is not an instance of Line.
     */
    @Override
    public void setNode(Node node) {
        if (node instanceof Line) {
            this.lineNode = (Line) node;
        } else {
            throw new IllegalArgumentException("Node must be an instance of Line");
        }
    }

    /**
     * Creates and returns a copy of this line.
     * @return A new ConcreteLine with the same properties.
     */
    @Override
    public ShapeInterface clone() {
        ConcreteLine copy = new ConcreteLine(x1, y1, x2, y2, borderColor);
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

        this.lineNode = new Line(x1, y1, x2, y2);
        this.lineNode.setStroke(borderColor);
        this.lineNode.setStrokeWidth(3);
        setAngle(angle);
        this.lineNode.setUserData(this);
    }

    /**
     * Moves the line to a new position.
     * Updates both the internal coordinates and the visual representation.
     * @param x The new starting x-coordinate.
     * @param y The new starting y-coordinate.
     */
    @Override
    public void moveTo(double x, double y) {
        double dx = x - x1;
        double dy = y - y1;

        x1 += dx;
        x2 += dx;
        y1 += dy;
        y2 += dy;

        if (lineNode != null) {
            lineNode.setStartX(x1);
            lineNode.setEndX(x2);
            lineNode.setStartY(y1);
            lineNode.setEndY(y2);
        }
    }

    /**
     * Returns the factory for creating lines.
     * @return The LineFactory instance.
     */
    @Override
    public ShapeFactory getShapeFactory() {
        return new LineFactory();
    }

    /**
     * Gets the border color of the line.
     * @return The border color.
     */
    @Override
    public Color getBorderColor() {
        return borderColor;
    }

    /**
     * Sets the border color of the line.
     * Updates the visual representation if the node exists.
     * @param borderColor The new border color.
     */
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        if (lineNode != null) {
            lineNode.setStroke(borderColor);
        }
    }

    /**
     * Gets the fill color of the line.
     * Lines do not have a fill color, so this returns null.
     * @return null
     */
    @Override
    public Color getFillColor() {
        return null;
    }

    /**
     * Sets the fill color of the line.
     * Lines do not have a fill color, so this method does nothing.
     * @param color The fill color (ignored).
     */
    @Override
    public void setFillColor(Color color) {
        // Lines do not have a fill color, so this method does nothing.
    }

    /**
     * Gets the starting x-coordinate of the line.
     * @return The starting x-coordinate.
     */
    @Override
    public double getX() {
        return x1;
    }

    /**
     * Sets the starting x-coordinate of the line.
     * Updates the ending x-coordinate accordingly.
     * @param x The new starting x-coordinate.
     */
    @Override
    public void setX(double x) {
        double dx = x - x1;
        x1 += dx;
        x2 += dx;
        if (lineNode != null) {
            lineNode.setStartX(x1);
            lineNode.setEndX(x2);
        }
    }

    /**
     * Gets the starting y-coordinate of the line.
     * @return The starting y-coordinate.
     */
    @Override
    public double getY() {
        return y1;
    }

    /**
     * Sets the starting y-coordinate of the line.
     * Updates the ending y-coordinate accordingly.
     * @param y The new starting y-coordinate.
     */
    @Override
    public void setY(double y) {
        double dy = y - y1;
        y1 += dy;
        y2 += dy;
        if (lineNode != null) {
            lineNode.setStartY(y1);
            lineNode.setEndY(y2);
        }
    }

    /**
     * Gets the ending x-coordinate of the line.
     * @return The ending x-coordinate.
     */
    public double getX2() {
        return x2;
    }

    /**
     * Gets the ending y-coordinate of the line.
     * @return The ending y-coordinate.
     */
    public double getY2() {
        return y2;
    }

    /**
     * Gets the width of the line (distance between x1 and x2).
     * @return The width.
     */
    @Override
    public double getWidth() {
        return Math.abs(x2 - x1);
    }

    /**
     * Sets the width of the line by adjusting the ending x-coordinate.
     * @param width The new width.
     */
    public void setWidth(double width) {
        this.x2 = x1 + width;
        if (lineNode != null) {
            lineNode.setEndX(x2);
        }
    }

    /**
     * Gets the height of the line (distance between y1 and y2).
     * @return The height.
     */
    @Override
    public double getHeight() {
        return Math.abs(y2 - y1);
    }

    /**
     * Sets the height of the line by adjusting the ending y-coordinate.
     * @param height The new height.
     */
    public void setHeight(double height) {
        this.y2 = y1 + height;
        if (lineNode != null) {
            lineNode.setEndY(y2);
        }
    }
}
