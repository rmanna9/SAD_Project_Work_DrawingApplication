package com.sad.models;
import javafx.scene.paint.Color;

public class LineFactory extends ShapeFactory{

    @Override
    public ShapeInterface createShape(double x, double y, double width, double height, Color border, Color fill) {
        double defaultWidth = 100;
        return new ConcreteLine(x, y, x+defaultWidth, y, border);
    }

}
