package com.sad.gruppo05.drawingapplicationsad.model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class ConcreteLine implements Shape {

    private final double startX, startY, endX, endY;
    private final Color borderColor;

    public ConcreteLine(double startX, double startY, double endX, double endY, Color borderColor) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.borderColor = borderColor;
    }

    @Override
    public Node createNode() {
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(borderColor);
        return line;
    }

    public double getStartX() { return startX; }
    public double getStartY() { return startY; }
    public double getEndX() { return endX; }
    public double getEndY() { return endY; }
    public Color getBorderColor() { return borderColor; }


}