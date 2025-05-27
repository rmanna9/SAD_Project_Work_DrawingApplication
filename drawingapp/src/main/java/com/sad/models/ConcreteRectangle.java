package com.sad.models;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Concrete implementation of ShapeInterface representing a rectangle.
 * Stores rectangle properties and provides drawing logic.
 */
public class ConcreteRectangle implements ShapeInterface {
    /** The border color of the rectangle. */
    private Color borderColor, fillColor;
    /** The x-coordinate of the rectangle's upper-left corner. */
    private double x;
    /** The y-coordinate of the rectangle's upper-left corner. */
    private double y;
    /** The width of the rectangle. */
    private double width;
    /** The height of the rectangle. */
    private double height;
    /** The JavaFX Rectangle node representing this shape. */
    private Rectangle rectNode;

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
        if(rectNode == null){
            rectNode = new Rectangle(x, y, width, height);
            rectNode.setStroke(borderColor);
            rectNode.setFill(fillColor);
            rectNode.setStrokeWidth(3);
            rectNode.setUserData(this);
        }
        return rectNode;
    }

    /**
     * Returns the JavaFX Node associated with this rectangle.
     * @return The Rectangle node.
     */
    @Override
    public Node getNode(){
        return this.rectNode;
    }

    /**
     * Sets the JavaFX Node for this rectangle.
     * @param node The node to associate.
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
        return new ConcreteRectangle(x, y, width, height, borderColor, fillColor);
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
    public Color getBorderColor() { return borderColor; }

    /**
     * Sets the border color of the rectangle.
     * @param borderColor The new border color.
     */
    @Override
    public void setBorderColor(Color borderColor) { 
        this.borderColor = borderColor; 
        if(rectNode != null) {
            rectNode.setStroke(borderColor);
        }
    }

    /**
     * Gets the fill color of the rectangle.
     * @return The fill color.
     */
    @Override
    public Color getFillColor() { return fillColor; }

    /**
     * Sets the fill color of the rectangle.
     * @param fillColor The new fill color.
     */
    @Override
    public void setFillColor(Color fillColor) { 
        this.fillColor = fillColor;
        if(rectNode != null) {
            rectNode.setFill(fillColor);
        }
     }

    /**
     * Gets the width of the rectangle.
     * @return The width.
     */
    @Override
    public double getWidth() { return width; }

    /**
     * Sets the width of the rectangle.
     * @param width The new width.
     */
    @Override
    public void setWidth(double width) { 
        this.width = width;
        if(rectNode != null) {
            rectNode.setWidth(width);
        }
    }

    /**
     * Gets the height of the rectangle.
     * @return The height.
     */
    @Override
    public double getHeight() { return height; }

    /**
     * Sets the height of the rectangle.
     * @param height The new height.
     */
    @Override
    public void setHeight(double height) { 
        this.height = height;
        if(rectNode != null) {
            rectNode.setHeight(height);
        }
     }

    /**
     * Gets the x-coordinate of the rectangle's upper-left corner.
     * @return The x-coordinate.
     */
    @Override
    public double getX() { return x; }

    /**
     * Sets the x-coordinate of the rectangle's upper-left corner.
     * @param x The new x-coordinate.
     */
    @Override
    public void setX(double x) { this.x = x; }

    /**
     * Gets the y-coordinate of the rectangle's upper-left corner.
     * @return The y-coordinate.
     */
    @Override
    public double getY() { return y; }

    /**
     * Sets the y-coordinate of the rectangle's upper-left corner.
     * @param y The new y-coordinate.
     */
    @Override
    public void setY(double y) { this.y = y; }
}
