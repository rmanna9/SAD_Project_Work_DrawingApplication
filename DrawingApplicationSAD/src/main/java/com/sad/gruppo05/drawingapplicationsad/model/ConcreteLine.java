package com.sad.gruppo05.drawingapplicationsad.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ConcreteLine implements Shape {

    private double x, y, x1, y1;
    private Color border, fill;

    public ConcreteLine(double x, double y, double x1, double y1, Color border) {
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
        this.border = border;
        this.fill = null;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(this.border);
        gc.strokeLine(x, y, x1, y1);
    }
}

