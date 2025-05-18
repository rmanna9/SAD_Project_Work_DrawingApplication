package com.sad.models;
import javafx.scene.paint.Color;


public abstract class ShapeFactory {

    public abstract ShapeInterface createShape(double x, double y, double width, double height, Color border, Color fill);

}
