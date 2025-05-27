package com.sad.models;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Concrete implementation of ShapeInterface representing a line.
 * Stores line properties and provides drawing logic.
 */
public class ConcreteLine implements ShapeInterface {
    /** The border color of the line. */
    private Color borderColor;
    /** The starting x-coordinate of the line. */
    private double x1;
    /** The starting y-coordinate of the line. */
    private double y1;
    /** The ending x-coordinate of the line. */
    private double x2;
    /** The ending y-coordinate of the line. */
    private double y2;
    /** The JavaFX Line node representing this shape. */
    private Line lineNode;

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
        if(lineNode == null){
            lineNode = new Line(x1, y1, x2, y2);
            lineNode.setStroke(borderColor);
            lineNode.setStrokeWidth(3);
            lineNode.setUserData(this);
        }
        return lineNode;
    }

    /**
     * Returns the JavaFX Node associated with this line.
     * @return The Line node.
     */
    @Override
    public Node getNode(){
        return this.lineNode;
    }

    /**
     * Sets the JavaFX Node for this line.
     * @param node The node to associate.
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
        return new ConcreteLine(x1, y1, x2, y2, borderColor);
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
    public Color getBorderColor() { return borderColor; }

    /**
     * Sets the border color of the line.
     * @param borderColor The new border color.
     */
    public void setBorderColor(Color borderColor) { 
        this.borderColor = borderColor;
        if(lineNode != null) {
            lineNode.setStroke(borderColor);
        }
     }

    /**
     * Gets the fill color of the line.
     * Lines do not have a fill color, so this returns null.
     * @return null
     */
    @Override
    public Color getFillColor() { return null; }

    /**
     * Sets the fill color of the line.
     * Lines do not have a fill color, so this method does nothing.
     * @param color The fill color (ignored).
     */
    @Override
    public void setFillColor(Color color) {
        // Lines do not have a fill color, so this method does nothing.
        // It can be overridden if needed for consistency with other shapes.
     }
    
    /**
     * Gets the starting x-coordinate of the line.
     * @return The starting x-coordinate.
     */
    @Override
    public double getX() { return x1; }

    /**
     * Sets the starting x-coordinate of the line.
     * @param x The new starting x-coordinate.
     */
    @Override
    public void setX(double x) { 
        this.x1 = x;
        if (lineNode != null) {
            lineNode.setStartX(x1);
        }
    }

    /**
     * Gets the starting y-coordinate of the line.
     * @return The starting y-coordinate.
     */
    @Override
    public double getY() { return y1; }

    /**
     * Sets the starting y-coordinate of the line.
     * @param y The new starting y-coordinate.
     */
    @Override
    public void setY(double y) { 
        this.y1 = y; 
        if (lineNode != null) {
            lineNode.setStartY(y1);
        }
    }

    /**
     * Gets the ending x-coordinate of the line.
     * @return The ending x-coordinate.
     */
    public double getX2() { return x2; }

    /**
     * Gets the ending y-coordinate of the line.
     * @return The ending y-coordinate.
     */
    public double getY2() { return y2; }
    
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
