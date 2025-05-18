package com.sad.gruppo05.drawingapplicationsad.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
    public void draw(GraphicsContext gc) {
        gc.setFill(fillColor);
        gc.setStroke(borderColor);
        gc.fillRect(x, y, width, height);
        gc.strokeRect(x, y, width, height);
    }
}
