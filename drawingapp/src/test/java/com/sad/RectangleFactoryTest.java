package com.sad;

import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.sad.models.*;

/**
 * Unit test class for RectangleFactory.
 * Verifies that the factory correctly creates ConcreteRectangle objects
 * with the expected properties and that the draw method returns a valid JavaFX Rectangle node.
 */
public class RectangleFactoryTest {

    private RectangleFactory factory;

    /**
     * Sets up a new RectangleFactory instance before each test.
     */
    @BeforeEach
    public void setup() {
        factory = new RectangleFactory();
    }

    /**
     * Tests the creation of a ConcreteRectangle using the factory.
     * Asserts that the created shape is not null, is a Rectangle,
     * and that its properties match the expected default and provided values.
     */
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

