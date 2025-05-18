package com.sad.models;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class ConcreteLine implements ShapeInterface{
    private Color borderColor;
    private double x1, y1, x2, y2;
    public ConcreteLine(double x1, double y1, double x2, double y2, Color borderColor) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.borderColor = borderColor;
    }
    @Override
    public Node draw() {
        Line line = new Line(x1, y1, x2, y2);
        line.setStroke(borderColor);
        line.setStrokeWidth(3);
        return line;
    }

    public Color getBorderColor() { return borderColor; }
    public void setBorderColor(Color borderColor) { this.borderColor = borderColor; }

    public double getX1() { return x1; }
    public void setX1(double x1) { this.x1 = x1; }
    public double getY1() { return y1; }
    public void setY1(double y1) { this.y1 = y1; }
    public double getX2() { return x2; }
    public void setX2(double x2) { this.x2 = x2; }
    public double getY2() { return y2; }
    public void setY2(double y2) { this.y2 = y2; }

    

}
