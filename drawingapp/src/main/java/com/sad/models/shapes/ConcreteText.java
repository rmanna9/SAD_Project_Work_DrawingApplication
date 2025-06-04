package com.sad.models.shapes;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.sad.models.ShapeFactory;

/**
 * Concrete implementation of ShapeInterface representing a text shape.
 * Stores text properties and provides drawing logic.
 * Implements Serializable to support saving/loading functionality.
 */
public class ConcreteText implements ShapeInterface, Serializable {

    /** The JavaFX Text node representing this shape. */
    private transient Text textNode;
    /** The content of the text. */
    private String content;
    /** The x-coordinate of the text's position. */
    private double x;
    /** The y-coordinate of the text's position. */
    private double y;
    /** The border color of the text. */
    private transient Color borderColor;
    /** The fill color of the text. */
    private transient Color fillColor;
    /** The font size of the text. */
    private double fontSize = 20;
    /** The rotation angle of the text in degrees. */
    private double angle = 0;

    private double width, height;

    private transient javafx.scene.transform.Scale mirrorScale = new javafx.scene.transform.Scale(1, 1, 0, 0);
    private double mirrorScaleX = 1;
    private double mirrorScaleY = 1;
    private double scaleX = 1;
    private double scaleY = 1;

    /**
     * Constructs a ConcreteText with the specified parameters.
     * @param content The content of the text.
     * @param x The x-coordinate of the text's position.
     * @param y The y-coordinate of the text's position.
     * @param borderColor The border color of the text.
     * @param fillColor The fill color of the text.
     * @param fontSize The font size of the text.
     */    
    public ConcreteText(String content, double x, double y, Color borderColor, Color fillColor, double fontSize) {
        this.content = content;
        this.x = x;
        this.y = y;
        this.borderColor = borderColor;
        this.fillColor = fillColor;
        this.fontSize = fontSize;
    }

    /**
     * Draws the text as a JavaFX Node.
     * Creates a new Text node if none exists, or returns the existing one.
     * @return The JavaFX Text node representing this shape.
     */
    @Override
    public Node draw() {
        if (textNode == null) {
            textNode = new Text(content);
            textNode.setX(x);
            textNode.setY(y);
            textNode.setStroke(borderColor);
            textNode.setFill(fillColor);
            textNode.setFont(Font.font(fontSize));
            setAngle(angle);
            textNode.setUserData(this);
            this.width = textNode.getBoundsInParent().getWidth();
            this.height = textNode.getBoundsInParent().getHeight();

            textNode.applyCss();
            textNode.snapshot(null, null);

            //double centerX = textNode.getBoundsInParent().getMinX() + textNode.getBoundsInParent().getWidth() / 2;
            //double centerY = textNode.getBoundsInParent().getMinY() + textNode.getBoundsInParent().getHeight() / 2;

            updateTransform();
            
        }
        return textNode;
    }

    /**
     * Sets the rotation angle of the text.
     * Updates the visual representation if the node exists.
     * @param angle The new rotation angle in degrees.
     */
    @Override
    public void setAngle(double angle) {
        this.angle = angle;
        if (textNode != null) {
            textNode.setRotate(angle);
        }
    }

    /**
     * Gets the rotation angle of the text.
     * @return The rotation angle in degrees.
     */
    @Override
    public double getAngle() {
        return angle;
    }

    /**
     * Rotates the text by a specified angle increment.
     * @param deltaAngle The angle increment in degrees.
     */
    @Override
    public void rotateBy(double deltaAngle) {
        setAngle(this.angle + deltaAngle);
    }

    @Override
    public void mirrorX(){
        mirrorScaleY *= -1;
        if (textNode != null) {
            textNode.getTransforms().remove(mirrorScale);

            textNode.applyCss();
            textNode.snapshot(null, null);

            double centerX = textNode.getBoundsInParent().getMinX() + textNode.getBoundsInParent().getWidth() / 2;
            double centerY = textNode.getBoundsInParent().getMinY() + textNode.getBoundsInParent().getHeight() / 2;

            mirrorScale = new Scale(mirrorScaleX, mirrorScaleY, centerX, centerY);
            textNode.getTransforms().add(mirrorScale);
        }    
    }

    @Override
    public void mirrorY(){
        mirrorScaleX *= -1;
        if (textNode != null) {
            textNode.getTransforms().remove(mirrorScale);

            textNode.applyCss();
            textNode.snapshot(null, null);

            double centerX = textNode.getBoundsInParent().getMinX() + textNode.getBoundsInParent().getWidth() / 2;
            double centerY = textNode.getBoundsInParent().getMinY() + textNode.getBoundsInParent().getHeight() / 2;

            mirrorScale = new Scale(mirrorScaleX, mirrorScaleY, centerX, centerY);
            textNode.getTransforms().add(mirrorScale);
        }
    }

    public double getFontSize() {
        return fontSize;
    }

    /**
     * Sets the font size of the text.
     * Updates the visual representation if the node exists.
     * @param fontSize The new font size.
     */
    public void setFontSize(double fontSize) {
        this.fontSize = fontSize;
        if (textNode != null) {
            textNode.setFont(Font.font(fontSize));
        }
    }

    /**
     * Sets the x-coordinate of the text's position.
     * Updates the visual representation if the node exists.
     * @param x The new x-coordinate.
     */
    @Override
    public void setX(double x) {
        this.x = x;
        if (textNode != null) {
            textNode.setX(x);
        }
    }

    /**
     * Sets the y-coordinate of the text's position.
     * Updates the visual representation if the node exists.
     * @param y The new y-coordinate.
     */
    @Override
    public void setY(double y) {
        this.y = y;
        if (textNode != null) {
            textNode.setY(y);
        }
    }

    /**
     * Gets the x-coordinate of the text's position.
     * @return The x-coordinate.
     */
    @Override
    public double getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the text's position.
     * @return The y-coordinate.
     */
    @Override
    public double getY() {
        return y;
    }

    /**
     * Returns the JavaFX Node associated with this text.
     * @return The Text node.
     */
    @Override
    public Node getNode() {
        return textNode;
    }

    /**
     * Sets the JavaFX Node for this text.
     * Throws IllegalArgumentException if the node is not a Text.
     * @param node The node to associate.
     * @throws IllegalArgumentException if the node is not a Text.
     */
    @Override
    public void setNode(Node node) {
        if (node instanceof Text) {
            this.textNode = (Text) node;
        } else {
            throw new IllegalArgumentException("Node must be a Text");
        }
    }

    /**
     * Creates and returns a copy of this text.
     * @return A new ConcreteText with the same properties.
     */
    @Override
    public ShapeInterface clone() {
        ConcreteText copy = new ConcreteText(content, x, y, borderColor, fillColor, fontSize);
        copy.setX(x);
        copy.setY(y);
        copy.setAngle(angle);
        copy.mirrorScaleX = this.mirrorScaleX;
        copy.mirrorScaleY = this.mirrorScaleY;
        copy.scaleX = this.scaleX;
        copy.scaleY = this.scaleY;
        copy.setWidth(width);
        copy.setHeight(height);
        return copy;
    }

    /**
     * Gets the content of the text.
     * @return The content of the text.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the text.
     * Updates the visual representation if the node exists.
     * @param content The new content of the text.
     */
    public void setContent(String content) {
        this.content = content;
        if (textNode != null) {
            textNode.setText(content);
        }
    }

    /**
     * Gets the border color of the text.
     * @return The border color.
     */
    public Color getBorderColor() {
        return this.borderColor;
    }

    /**
     * Sets the border color of the text.
     * Updates the visual representation if the node exists.
     * @param color The new border color.
     */
    @Override
    public void setBorderColor(Color color) {
        this.borderColor = color;
        if (textNode != null) {
            textNode.setStroke(color);
        }
    }

    /**
     * Gets the fill color of the text.
     * @return The fill color.
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * Sets the fill color of the text.
     * Updates the visual representation if the node exists.
     * @param color The new fill color.
     */
    @Override
    public void setFillColor(Color color) {
        this.fillColor = color;
        if (textNode != null) {
            textNode.setFill(color);
        }
    }

    /**
     * Gets the width of the text (bounding box width).
     * @return The width.
     */
    @Override
    public double getWidth() {
        return this.width;
    }

    /**
     * Gets the height of the text (bounding box height).
     * @return The height.
     */
    @Override
    public double getHeight() {
        return this.height;
    }

    /**
     * Sets the width of the text.
     * This method is not implemented as resizing text is typically done by changing the font size.
     * @param width The new width (ignored).
     */
    @Override
    public void setWidth(double width) {
        this.width = width;
        if (textNode != null && textNode.getLayoutBounds().getWidth() != 0) {
            scaleX = width / textNode.getLayoutBounds().getWidth();
            updateTransform();
        }
    }

    /**
     * Sets the height of the text.
     * This method is not implemented as resizing text is typically done by changing the font size.
     * @param height The new height (ignored).
     */
    @Override
    public void setHeight(double height) {
        this.height = height;
        if (textNode != null && textNode.getLayoutBounds().getHeight() != 0) {
            scaleY = height / textNode.getLayoutBounds().getHeight();
            updateTransform();
        }
    }

    private void updateTransform() {
        if (textNode != null) {
            textNode.getTransforms().remove(mirrorScale);

            textNode.applyCss();
            textNode.snapshot(null, null);

            double centerX = textNode.getBoundsInParent().getMinX() + textNode.getBoundsInParent().getWidth() / 2;
            double centerY = textNode.getBoundsInParent().getMinY() + textNode.getBoundsInParent().getHeight() / 2;

            mirrorScale = new Scale(mirrorScaleX * scaleX, mirrorScaleY * scaleY, centerX, centerY);
            textNode.getTransforms().add(mirrorScale);
        }
    }


    /**
     * Moves the text to a new position.
     * Updates both the internal coordinates and the visual representation.
     * @param newX The new x-coordinate.
     * @param newY The new y-coordinate.
     */
    @Override
    public void moveTo(double newX, double newY) {
        // Salva lo stato delle trasformazioni
        final double currentAngle = this.angle;
        final double scaleX = mirrorScale.getX();
        final double scaleY = mirrorScale.getY();

        // Rimuove temporaneamente le trasformazioni
        if (textNode != null) {
            textNode.setRotate(0);
            textNode.getTransforms().remove(mirrorScale);
        }

        // Applica lo spostamento
        setX(newX);
        setY(newY);

        // Ripristina le trasformazioni
        if (textNode != null) {
            // Calcola il nuovo centro
            textNode.applyCss();
            textNode.snapshot(null, null);
            double newCenterX = textNode.getBoundsInParent().getMinX() + textNode.getBoundsInParent().getWidth() / 2;
            double newCenterY = textNode.getBoundsInParent().getMinY() + textNode.getBoundsInParent().getHeight() / 2;

            mirrorScale = new Scale(scaleX, scaleY, newCenterX, newCenterY);
            textNode.getTransforms().add(mirrorScale);
            textNode.setRotate(currentAngle);
        }
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
        out.writeDouble(fontSize);
        out.writeDouble(mirrorScale.getX()); 
        out.writeDouble(mirrorScale.getY()); 
        out.writeDouble(scaleX);
        out.writeDouble(scaleY);
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
        fontSize = in.readDouble();
        this.scaleX = in.readDouble();
        this.scaleY = in.readDouble();
        double scaleX = in.readDouble();
        double scaleY = in.readDouble();
        

        draw();

        mirrorScale.setX(scaleX);
        mirrorScale.setY(scaleY);
    }

    /**
     * Returns the factory for creating text shapes.
     * @return The ShapeFactory instance (currently null).
     */
    @Override
    public ShapeFactory getShapeFactory() {
        return null;
    }
}
