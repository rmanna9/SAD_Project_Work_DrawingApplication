package com.sad.models;
import javafx.scene.paint.Color;


public class EllipseFactory extends ShapeFactory {
    private final double defaultWidth = 100;
    private final double defaultHeight = 50;

    @Override
    public ShapeInterface createShape(double x, double y, double width, double height, Color border, Color fill) {                            
        return new ConcreteEllipse(x, y, defaultWidth, defaultHeight, border, fill);
    }

}
