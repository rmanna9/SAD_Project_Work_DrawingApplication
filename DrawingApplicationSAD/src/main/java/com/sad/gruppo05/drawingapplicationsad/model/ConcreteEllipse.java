package com.sad.gruppo05.drawingapplicationsad.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ConcreteEllipse implements Shape {
    private final double x, y, width, height;
    private final Color borderColor;
    private final Color fillColor;

    public ConcreteEllipse(double x, double y, double width, double height, Color borderColor, Color fillColor) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.borderColor = borderColor;
        this.fillColor = fillColor;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(fillColor);
        gc.setStroke(borderColor);
        gc.fillOval(x, y, width, height);
        gc.strokeOval(x, y, width, height);
    }


}
