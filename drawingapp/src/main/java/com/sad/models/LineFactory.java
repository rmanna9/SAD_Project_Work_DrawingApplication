package com.sad.models;
import javafx.scene.paint.Color;

public class LineFactory extends ShapeFactory{
    private final double defaultWidth = 100;
    
    @Override
    public ShapeInterface createShape(double x, double y, double width, double height, Color border, Color fill) {
        return new ConcreteLine(x, y, x+defaultWidth, y, border);
    }

}
