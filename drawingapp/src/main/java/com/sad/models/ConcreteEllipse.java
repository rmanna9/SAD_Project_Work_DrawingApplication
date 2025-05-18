package com.sad.models;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;


public class ConcreteEllipse implements ShapeInterface{
    private Color borderColor, fillColor;
    private double x, y, width, height;

    public ConcreteEllipse(double x, double y, double width, double height, Color borderColor, Color fillColor) {
        this.borderColor = borderColor;
        this.fillColor = fillColor;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    @Override
    public Node draw() {
        Ellipse ellipse = new Ellipse(x, y, width, height);
        ellipse.setStroke(borderColor);
        ellipse.setFill(fillColor);
        ellipse.setStrokeWidth(3);
        return ellipse;
    }


    public Color getBorderColor() { return borderColor; }
    public void setBorderColor(Color borderColor) { this.borderColor = borderColor; }

    public Color getFillColor() { return fillColor; }
    public void setFillColor(Color fillColor) { this.fillColor = fillColor; }

    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

}
