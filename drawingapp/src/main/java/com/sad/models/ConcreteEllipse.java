package com.sad.models;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 * Concrete implementation of ShapeInterface representing an ellipse.
 * Stores ellipse properties and provides drawing logic.
 */
public class ConcreteEllipse implements ShapeInterface {
    /** The border color of the ellipse. */
    private Color borderColor, fillColor;
    /** The x-coordinate of the ellipse center. */
    private double x;
    /** The y-coordinate of the ellipse center. */
    private double y;
    /** The horizontal radius (width) of the ellipse. */
    private double width;
    /** The vertical radius (height) of the ellipse. */
    private double height;
    /** The JavaFX Ellipse node representing this shape. */
    private Ellipse ellipseNode;

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
            ellipseNode = new Ellipse(x, y, width, height);
            ellipseNode.setStroke(borderColor);
            ellipseNode.setFill(fillColor);
            ellipseNode.setStrokeWidth(3);
            ellipseNode.setUserData(this);
        }
        return ellipseNode;
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
        return new ConcreteEllipse(x, y, width, height, borderColor, fillColor);
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
    public void setX(double x) { this.x = x; }
    
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
    public void setY(double y) { this.y = y; }

}
