package com.sad.models;
import com.sad.models.shapes.ConcreteLine;
import com.sad.models.shapes.ShapeInterface;

import javafx.scene.paint.Color;

/**
 * Factory class for creating ConcreteLine objects.
 * Provides a method to instantiate lines with a default length.
 */
public class LineFactory extends ShapeFactory{
    /** The default length (width) for created lines. */
    private final double defaultWidth = 100;

    /**
     * Creates a ConcreteLine with the specified starting position and border color,
     * using a default length for the line. The line is horizontal.
     *
     * @param x The starting x-coordinate of the line.
     * @param y The starting y-coordinate of the line.
     * @param width Ignored (default width is used).
     * @param height Ignored.
     * @param border The border color of the line.
     * @param fill Ignored.
     * @return A new ConcreteLine instance.
     */
    @Override
    public ShapeInterface createShape(double x, double y, double width, double height, Color border, Color fill) {
        return new ConcreteLine(x, y, x+defaultWidth, y, border);
    }

}
