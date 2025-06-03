package com.sad.models.shapes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sad.models.ShapeFactory;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Concrete implementation of ShapeInterface representing a polygon.
 * Stores polygon properties and provides drawing logic.
 * Implements Serializable to support saving/loading functionality.
 */
public class ConcretePolygon implements ShapeInterface, Serializable {

    /** The JavaFX Polygon node representing this shape. */
    private transient Polygon polygonNode;
    /** The list of relative points (x, y coordinates) defining the polygon vertices. */
    private List<Double> points;
    /** The border and fill colors of the polygon. */
    private transient Color borderColor, fillColor;
    /** The x-coordinate of the polygon's top-left corner. */
    private double x;
    /** The y-coordinate of the polygon's top-left corner. */
    private double y;
    /** The rotation angle of the polygon in degrees. */
    private double angle = 0;

    private transient javafx.scene.transform.Scale mirrorScale = new javafx.scene.transform.Scale(1, 1, 0, 0);
    private double mirrorScaleX = 1;
    private double mirrorScaleY = 1;


    /**
     * Constructs a ConcretePolygon with the specified parameters.
     * Calculates the relative points based on the minimum x and y values.
     * @param points The list of absolute coordinates defining the polygon vertices.
     * @param borderColor The color of the polygon border.
     * @param fillColor The fill color of the polygon.
     */
    public ConcretePolygon(List<Double> points, Color borderColor, Color fillColor) {
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        for (int i = 0; i < points.size(); i += 2) {
            double px = points.get(i);
            double py = points.get(i + 1);
            if (px < minX) minX = px;
            if (py < minY) minY = py;
        }
        this.x = minX;
        this.y = minY;

        this.points = new ArrayList<>();
        for (int i = 0; i < points.size(); i += 2) {
            this.points.add(points.get(i) - minX);
            this.points.add(points.get(i + 1) - minY);
        }

        this.borderColor = borderColor;
        this.fillColor = fillColor;
    }

    /**
     * Sets the rotation angle of the polygon.
     * Updates the visual representation if the node exists.
     * @param angle The new rotation angle in degrees.
     */
    @Override
    public void setAngle(double angle) {
        this.angle = angle;
        if (polygonNode != null) {
            polygonNode.setRotate(angle);
        }
    }

    /**
     * Gets the rotation angle of the polygon.
     * @return The rotation angle in degrees.
     */
    @Override
    public double getAngle() {
        return this.angle;
    }

    /**
     * Rotates the polygon by a specified angle increment.
     * @param deltaAngle The angle increment in degrees.
     */
    @Override
    public void rotateBy(double deltaAngle) {
        setAngle(this.angle + deltaAngle);
    }

    private double getCenterY() {
        double sumY = 0;
        int count = points.size() / 2;
        for (int i = 1; i < points.size(); i += 2) {
            sumY += points.get(i);
        }
        return sumY / count;
    }
    private double getCenterX() {
        double sumX = 0;
        int count = points.size() / 2;
        for (int i = 0; i < points.size(); i += 2) {
            sumX += points.get(i);
        }
        return sumX / count;
    }

    @Override
    public void mirrorX(){
        mirrorScaleY *= -1;
        if (polygonNode != null) {
            mirrorScale.setY(mirrorScale.getY() * -1);
        }
    }

    @Override
    public void mirrorY(){
        mirrorScaleX *= -1;
        if (polygonNode != null) {
            mirrorScale.setX(mirrorScale.getX() * -1);
        }
    }
        /**
     * Draws the polygon as a JavaFX Node.
     * Creates a new Polygon node if none exists, or returns the existing one.
     * @return The JavaFX Polygon node representing this shape.
     */
    @Override
    public Node draw() {
        if (polygonNode == null) {
            polygonNode = new Polygon();
            polygonNode.getPoints().addAll(points);
            polygonNode.setStroke(borderColor);
            polygonNode.setFill(fillColor);
            polygonNode.setStrokeWidth(3);
            setAngle(angle);
            polygonNode.setUserData(this);
            polygonNode.setLayoutX(x);
            polygonNode.setLayoutY(y);

            double centerX = getCenterX();
            double centerY = getCenterY();
            mirrorScale = new javafx.scene.transform.Scale(mirrorScaleX, mirrorScaleY, centerX, centerY);
            polygonNode.getTransforms().add(mirrorScale);
        }
        return polygonNode;
    }

    /**
     * Sets the x-coordinate of the polygon's top-left corner.
     * Updates the visual representation if the node exists.
     * @param x The new x-coordinate.
     */
    @Override
    public void setX(double x) {
        this.x = x;
        if (polygonNode != null) polygonNode.setLayoutX(x);
    }

    /**
     * Sets the y-coordinate of the polygon's top-left corner.
     * Updates the visual representation if the node exists.
     * @param y The new y-coordinate.
     */
    @Override
    public void setY(double y) {
        this.y = y;
        if (polygonNode != null) polygonNode.setLayoutY(y);
    }

    /**
     * Gets the x-coordinate of the polygon's top-left corner.
     * @return The x-coordinate.
     */
    @Override
    public double getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the polygon's top-left corner.
     * @return The y-coordinate.
     */
    @Override
    public double getY() {
        return y;
    }

    /**
     * Returns the JavaFX Node associated with this polygon.
     * @return The Polygon node.
     */
    @Override
    public Node getNode() {
        return polygonNode;
    }

    /**
     * Sets the JavaFX Node for this polygon.
     * Throws IllegalArgumentException if the node is not a Polygon.
     * @param node The node to associate.
     * @throws IllegalArgumentException if the node is not a Polygon.
     */
    @Override
    public void setNode(Node node) {
        if (node instanceof Polygon) {
            this.polygonNode = (Polygon) node;
        } else {
            throw new IllegalArgumentException("Node must be an instance of Polygon");
        }
    }

    /**
     * Creates and returns a copy of this polygon.
     * @return A new ConcretePolygon with the same properties.
     */
    @Override
    public ShapeInterface clone() {
        ConcretePolygon copy = new ConcretePolygon(getAbsolutePoints(), borderColor, fillColor);
        copy.setX(x);
        copy.setY(y);
        copy.setAngle(angle);
        copy.mirrorScaleX = this.mirrorScaleX;
        copy.mirrorScaleY = this.mirrorScaleY;
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

        polygonNode = new Polygon();
        polygonNode.getPoints().addAll(points);
        polygonNode.setStroke(borderColor);
        polygonNode.setFill(fillColor);
        polygonNode.setStrokeWidth(3);
        setAngle(angle);
        polygonNode.setUserData(this);
        polygonNode.setLayoutX(x);
        polygonNode.setLayoutY(y);

        double centerX = getCenterX();
        double centerY = getCenterX();
        mirrorScale = new javafx.scene.transform.Scale(mirrorScaleX, mirrorScaleY, centerX, centerY);
        polygonNode.getTransforms().add(mirrorScale);
    }

    /**
     * Moves the polygon to a new position.
     * Updates both the internal coordinates and the visual representation.
     * @param newX The new x-coordinate.
     * @param newY The new y-coordinate.
     */
    @Override
    public void moveTo(double newX, double newY) {
        this.x = newX;
        this.y = newY;

        if (polygonNode != null) {
            polygonNode.setLayoutX(newX);
            polygonNode.setLayoutY(newY);
        }
    }

    /**
     * Gets the absolute points of the polygon (relative points + top-left corner).
     * @return A list of absolute coordinates defining the polygon vertices.
     */
    private List<Double> getAbsolutePoints() {
        List<Double> absPoints = new ArrayList<>();
        for (int i = 0; i < points.size(); i += 2) {
            absPoints.add(points.get(i) + x);
            absPoints.add(points.get(i + 1) + y);
        }
        return absPoints;
    }

    /**
     * Gets the border color of the polygon.
     * @return The border color.
     */
    public Color getBorderColor() {
        return borderColor;
    }

    /**
     * Sets the border color of the polygon.
     * Updates the visual representation if the node exists.
     * @param borderColor The new border color.
     */
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        if (polygonNode != null) {
            polygonNode.setStroke(borderColor);
        }
    }

    /**
     * Gets the fill color of the polygon.
     * @return The fill color.
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * Sets the fill color of the polygon.
     * Updates the visual representation if the node exists.
     * @param color The new fill color.
     */
    @Override
    public void setFillColor(Color color) {
        this.fillColor = color;
        if (polygonNode != null) {
            polygonNode.setFill(fillColor);
        }
    }

    /**
     * Gets the width of the polygon (bounding box width).
     * @return The width.
     */
    @Override
    public double getWidth() {
        return polygonNode.getBoundsInParent().getWidth();
    }

    /**
     * Sets the width of the polygon by scaling its points horizontally.
     * @param width The new width.
     */
    @Override
    public void setWidth(double width) {
        scale(width / getWidth(), 1);
    }

    /**
     * Gets the height of the polygon (bounding box height).
     * @return The height.
     */
    @Override
    public double getHeight() {
        return polygonNode.getBoundsInParent().getHeight();
    }

    /**
     * Sets the height of the polygon by scaling its points vertically.
     * @param height The new height.
     */
    @Override
    public void setHeight(double height) {
        scale(1, height / getHeight());
    }

    /**
     * Scales the polygon's points by the specified factors.
     * @param scaleX The scaling factor for the X dimension.
     * @param scaleY The scaling factor for the Y dimension.
     */
    private void scale(double scaleX, double scaleY) {
        double sumX = 0;
        double sumY = 0;
        int count = points.size() / 2;

        for (int i = 0; i < points.size(); i += 2) {
            sumX += points.get(i);
            sumY += points.get(i + 1);
        }

        double centerX = sumX / count;
        double centerY = sumY / count;

        List<Double> newPoints = new ArrayList<>();
        for (int i = 0; i < points.size(); i += 2) {
            double x = points.get(i);
            double y = points.get(i + 1);

            double newX = centerX + (x - centerX) * scaleX;
            double newY = centerY + (y - centerY) * scaleY;

            newPoints.add(newX);
            newPoints.add(newY);
        }

        points.clear();
        points.addAll(newPoints);
        if (polygonNode != null) {
            polygonNode.getPoints().setAll(points);
        }
    }

    /**
     * Returns the factory for creating polygons.
     * @return The ShapeFactory instance (currently null).
     */
    @Override
    public ShapeFactory getShapeFactory() {
        return null;
    }
}
