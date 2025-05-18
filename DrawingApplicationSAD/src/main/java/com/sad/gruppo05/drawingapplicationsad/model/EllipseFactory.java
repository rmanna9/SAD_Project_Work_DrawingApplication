package com.sad.gruppo05.drawingapplicationsad.model;

import javafx.scene.paint.Color;

public class EllipseFactory extends ShapeFactory{
    @Override
    public Shape createShape(double x, double y, double width, double height, Color border, Color fill) {
        double width1 = 80;
        double height1 = 50;
        return new ConcreteEllipse(x, y, width1, height1, border, fill);
    }
}
