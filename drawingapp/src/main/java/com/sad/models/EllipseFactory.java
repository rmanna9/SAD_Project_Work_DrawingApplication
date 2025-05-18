package com.sad.models;
import javafx.scene.paint.Color;


public class EllipseFactory extends ShapeFactory {
    @Override
    public ShapeInterface createShape(double x, double y, double width, double height, Color border, Color fill) {
        double defaultWidth = 80;
        double defaultHeight = 35;                                
        return new ConcreteEllipse(x, y, defaultWidth, defaultHeight, border, fill);
    }

}
