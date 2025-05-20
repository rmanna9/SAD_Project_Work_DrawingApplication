package com.sad;

import com.sad.models.ShapeInterface;
import com.sad.models.command.LoadCommand;
import com.sad.models.ConcreteRectangle;
import com.sad.models.ConcreteEllipse;
import com.sad.models.ConcreteLine;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for LoadCommand.
 * Verifies the correct deserialization of shapes from string representations.
 */
public class LoadCommandTest {

    /**
     * Tests deserialization of a rectangle from a string.
     * Asserts that the resulting object is a ConcreteRectangle and its properties match the expected values.
     */
    @Test
    public void testDeserializeRectangle() {
        String line = "Rectangle 10,000000 20,000000 100,000000 50,000000 #000000 #FF0000";
        LoadCommand cmd = new LoadCommand(new ArrayList<>(), new Pane());
        ShapeInterface shape = cmd.deserializeShape(line);

        assertTrue(shape instanceof ConcreteRectangle);
        ConcreteRectangle rect = (ConcreteRectangle) shape;
        assertEquals(10.0, rect.getX());
        assertEquals(20.0, rect.getY());
        assertEquals(100.0, rect.getWidth());
        assertEquals(50.0, rect.getHeight());
        assertEquals(Color.web("#000000"), rect.getBorderColor());
        assertEquals(Color.web("#FF0000"), rect.getFillColor());
    }

    /**
     * Tests deserialization of an ellipse from a string.
     * Asserts that the resulting object is a ConcreteEllipse and its properties match the expected values.
     */
    @Test
    public void testDeserializeEllipse() {
        String line = "Ellipse 15,000000 25,000000 60,000000 40,000000 #0000FF transparent";
        LoadCommand cmd = new LoadCommand(new ArrayList<>(), new Pane());
        ShapeInterface shape = cmd.deserializeShape(line);

        assertTrue(shape instanceof ConcreteEllipse);
        ConcreteEllipse ellipse = (ConcreteEllipse) shape;
        assertEquals(15.0, ellipse.getX());
        assertEquals(25.0, ellipse.getY());
        assertEquals(60.0, ellipse.getWidth());
        assertEquals(40.0, ellipse.getHeight());
        assertEquals(Color.web("#0000FF"), ellipse.getBorderColor());
        assertEquals(Color.TRANSPARENT, ellipse.getFillColor());
    }

    /**
     * Tests deserialization of a line from a string.
     * Asserts that the resulting object is a ConcreteLine and its properties match the expected values.
     */
    @Test
    public void testDeserializeLine() {
        String line = "Line 5,000000 5,000000 100,000000 100,000000 #008000";
        LoadCommand cmd = new LoadCommand(new ArrayList<>(), new Pane());
        ShapeInterface shape = cmd.deserializeShape(line);

        assertTrue(shape instanceof ConcreteLine);
        ConcreteLine lineShape = (ConcreteLine) shape;
        assertEquals(5.0, lineShape.getX1());
        assertEquals(5.0, lineShape.getY1());
        assertEquals(100.0, lineShape.getX2());
        assertEquals(100.0, lineShape.getY2());
        assertEquals(Color.web("#008000"), lineShape.getBorderColor());
    }
}
