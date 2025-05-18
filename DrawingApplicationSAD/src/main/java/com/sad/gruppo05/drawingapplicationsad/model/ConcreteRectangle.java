package com.sad.gruppo05.drawingapplicationsad.model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ConcreteRectangle implements Shape {
    private final double x, y, width, height;
    private final Color borderColor;
    private final Color fillColor;

    public ConcreteRectangle(double x, double y, double width, double height, Color borderColor, Color fillColor) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.borderColor = borderColor;
        this.fillColor = fillColor;
    }

    @Override
    public Node createNode() {
        Rectangle rect = new Rectangle(x, y, width, height);
        rect.setStroke(borderColor);
        rect.setFill(fillColor);
        return rect;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public Color getBorderColor() { return borderColor; }
    public Color getFillColor() { return fillColor; }
}