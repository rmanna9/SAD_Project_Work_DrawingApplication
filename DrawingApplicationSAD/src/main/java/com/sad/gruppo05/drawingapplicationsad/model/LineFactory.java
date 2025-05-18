package com.sad.gruppo05.drawingapplicationsad.model;

import javafx.scene.paint.Color;

public class LineFactory extends ShapeFactory{
    @Override
    public Shape createShape(double x, double y, double width, double height, Color border, Color fill) {
        double length = 100;
        return new ConcreteLine(x, y, x+ length, y, border);
    }
}
