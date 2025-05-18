package com.sad.models;
import javafx.scene.paint.Color;


public class RectangleFactory extends ShapeFactory {
    @Override
    public ShapeInterface createShape(double x, double y, double width, double height, Color borderColor, Color fillColor) {
        double defaultWidth = 100;
        double defaultHeight = 50;                                    
        return new ConcreteRectangle(x, y, defaultWidth, defaultHeight, borderColor, fillColor);
    }

}
