package com.sad.gruppo05.drawingapplicationsad.model;

import javafx.scene.paint.Color;

public abstract class ShapeFactory {
    public abstract Shape createShape(double x, double y, double width, double height, Color border, Color fill);
}