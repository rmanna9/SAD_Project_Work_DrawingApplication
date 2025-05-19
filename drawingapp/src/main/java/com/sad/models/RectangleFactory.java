package com.sad.models;
import javafx.scene.paint.Color;


public class RectangleFactory extends ShapeFactory {
    private final double defaultWidth = 100;
    private final double defaultHeight = 50; 

    @Override
    public ShapeInterface createShape(double x, double y, double width, double height, Color borderColor, Color fillColor) {                                   
        return new ConcreteRectangle(x, y, defaultWidth, defaultHeight, borderColor, fillColor);
    }

}
