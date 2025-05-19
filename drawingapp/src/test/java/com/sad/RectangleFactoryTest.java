package com.sad;

import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.sad.models.*;

public class RectangleFactoryTest {

    private RectangleFactory factory;

    @BeforeEach
    public void setup() {
        factory = new RectangleFactory();
    }

    @Test
    public void testRectangleCreation() {
        ShapeInterface shape = factory.createShape(30, 40, 0, 0, Color.RED, Color.BLUE);
        assertNotNull(shape);
        Node node = shape.draw();
        assertTrue(node instanceof Rectangle);

        Rectangle rect = (Rectangle) node;
        assertEquals(30.0, rect.getX());
        assertEquals(40.0, rect.getY());
        assertEquals(100.0, rect.getWidth()); // default width = 100
        assertEquals(50, rect.getHeight());  // default height = 50
        assertEquals(Color.RED, rect.getStroke());
        assertEquals(Color.BLUE, rect.getFill());
    }
}

