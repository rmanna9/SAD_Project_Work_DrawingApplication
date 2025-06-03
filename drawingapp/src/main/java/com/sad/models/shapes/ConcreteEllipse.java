package com.sad.models.shapes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.sad.models.EllipseFactory;
import com.sad.models.ShapeFactory;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 * Concrete implementation of ShapeInterface representing an ellipse.
 * Stores ellipse properties and provides drawing logic.
 */
public class ConcreteEllipse implements ShapeInterface, Serializable {
    /** The border color of the ellipse. */
    private transient Color borderColor, fillColor;
    /** The x-coordinate of the ellipse center. */
    private double x;
    /** The y-coordinate of the ellipse center. */
    private double y;
    /** The horizontal radius (width) of the ellipse. */
    private double width;
    /** The vertical radius (height) of the ellipse. */
    private double height;
    /** The JavaFX Ellipse node representing this shape. */
    private transient Ellipse ellipseNode;
    
    private double angle = 0;



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
        if (ellipseNode == null){
            ellipseNode = new Ellipse(0, 0, width, height);
            ellipseNode.setLayoutX(x);
            ellipseNode.setLayoutY(y);
            ellipseNode.setStroke(borderColor);
            ellipseNode.setFill(fillColor);
            ellipseNode.setStrokeWidth(3);
            ellipseNode.setUserData(this);
            setAngle(angle);
            
        }
        return ellipseNode;
    }

    /**
     * Gets the current rotation angle of the ellipse.
     * @return The rotation angle in degrees.
     */
    @Override
    public double getAngle() {
        return angle;
    }

    /**
     * Sets the rotation angle of the ellipse and updates its visual representation.
     * The angle is normalized to be between 0 and 360 degrees.
     * @param angle The new rotation angle in degrees.
     */
    @Override
    public void setAngle(double angle) {
        this.angle = angle % 360;

        if (ellipseNode == null) return;

        javafx.geometry.Bounds bounds = ellipseNode.getBoundsInLocal();
        double centerX = bounds.getMinX() + bounds.getWidth() / 2;
        double centerY = bounds.getMinY() + bounds.getHeight() / 2;

        ellipseNode.getTransforms().removeIf(t -> t instanceof javafx.scene.transform.Rotate);
        javafx.scene.transform.Rotate rotate = new javafx.scene.transform.Rotate(this.angle, centerX, centerY);
        ellipseNode.getTransforms().add(rotate);
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
     * Returns the JavaFX Node associated with this ellipse.
     * @return The Ellipse node.
     */
    @Override
    public Node getNode(){
        return this.ellipseNode;
    }

    /**
     * Sets the JavaFX Node for this ellipse.
     * @param node The node to associate.
     */
    @Override
    public void setNode(Node node) {
        if (node instanceof Ellipse) {
            this.ellipseNode = (Ellipse) node;
        } else {
            throw new IllegalArgumentException("Node must be an instance of Ellipse");
        }
    }

    /**
     * Creates and returns a copy of this ellipse.
     * @return A new ConcreteEllipse with the same properties.
     */
    @Override
    public ShapeInterface clone() {
        ConcreteEllipse copy =  new ConcreteEllipse(x, y, width, height, borderColor, fillColor);
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

        ellipseNode = new Ellipse(0, 0, width, height);
        ellipseNode.setLayoutX(x);
        ellipseNode.setLayoutY(y);
        ellipseNode.setStroke(borderColor);
        ellipseNode.setFill(fillColor);
        setAngle(angle);
        ellipseNode.setStrokeWidth(3);
        ellipseNode.setUserData(this);

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
     * Moves the ellipse to a new position.
     * Updates both the internal coordinates and the visual representation.
     * @param x The new x-coordinate.
     * @param y The new y-coordinate.
     */
    @Override
    public void moveTo(double x, double y) {
        this.x = x;
        this.y = y;

        if (ellipseNode != null) {
            ellipseNode.setLayoutX(x);
            ellipseNode.setLayoutY(y);
        }
    }

    /**
     * Rotates the ellipse by a specified angle increment.
     * @param deltaAngle The angle increment in degrees.
     */
    @Override
    public void rotateBy(double deltaAngle) {
        setAngle(this.angle + deltaAngle);
    }

    /**
     * Returns the factory for creating ellipses.
     * @return The EllipseFactory instance.
     */
    @Override
    public ShapeFactory getShapeFactory() {
        return new EllipseFactory();
    }
    
    /**
     * Gets the border color of the ellipse.
     * @return The border color.
     */
    @Override
    public Color getBorderColor() { return borderColor; }

    /**
     * Sets the border color of the ellipse.
     * @param borderColor The new border color.
     */
    @Override
    public void setBorderColor(Color borderColor) { 
        this.borderColor = borderColor; 
        if(ellipseNode != null) {
            ellipseNode.setStroke(borderColor);
        }
    }

    /**
     * Gets the fill color of the ellipse.
     * @return The fill color.
     */
    @Override
    public Color getFillColor() { return fillColor; }

    /**
     * Sets the fill color of the ellipse.
     * @param fillColor The new fill color.
     */
    @Override
    public void setFillColor(Color fillColor) { 
        this.fillColor = fillColor; 
        if(ellipseNode != null) {
            ellipseNode.setFill(fillColor);
        }
    }

    /**
     * Gets the horizontal radius (width) of the ellipse.
     * @return The width.
     */
    @Override
    public double getWidth() { return width; }

    /**
     * Sets the horizontal radius (width) of the ellipse.
     * @param width The new width.
     */
    @Override
    public void setWidth(double width) { 
        this.width = width; 
        if(ellipseNode != null) {
            ellipseNode.setRadiusX(width);
        }
    }

    /**
     * Gets the vertical radius (height) of the ellipse.
     * @return The height.
     */
    @Override
    public double getHeight() { return height; }

    /**
     * Sets the vertical radius (height) of the ellipse.
     * @param height The new height.
     */
    @Override
    public void setHeight(double height) { 
        this.height = height; 
        if(ellipseNode != null) {
            ellipseNode.setRadiusY(height);
        }
    }

    /**
     * Gets the x-coordinate of the ellipse center.
     * @return The x-coordinate.
     */
    @Override
    public double getX() { return x; }

    /**
     * Sets the x-coordinate of the ellipse center.
     * @param x The new x-coordinate.
     */
    @Override
    public void setX(double x) { 
        this.x = x; 
        if(ellipseNode!=null){
            ellipseNode.setLayoutX(x);
        }
    }
    
    /**
     * Gets the y-coordinate of the ellipse center.
     * @return The y-coordinate.
     */
    @Override
    public double getY() { return y; }

    /**
     * Sets the y-coordinate of the ellipse center.
     * @param y The new y-coordinate.
     */
    @Override
    public void setY(double y) { 
        this.y = y; 
    
        if(ellipseNode!=null){
            ellipseNode.setLayoutY(y);
        }
    }
}
