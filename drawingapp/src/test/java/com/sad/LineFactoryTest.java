package com.sad;

import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.sad.models.*;

/**
 * Unit test class for LineFactory.
 * Verifies that the factory correctly creates ConcreteLine objects
 * with the expected properties and that the draw method returns a valid JavaFX Line node.
 */
public class LineFactoryTest {

    private LineFactory factory;

    /**
     * Sets up a new LineFactory instance before each test.
     */
    @BeforeEach
    public void setup() {
        factory = new LineFactory();
    }

    /**
     * Tests the creation of a ConcreteLine using the factory.
     * Asserts that the created shape is not null, is a Line,
     * and that its properties match the expected default and provided values.
     */
    @Test
    public void testLineCreation() {
        ShapeInterface shape = factory.createShape(10, 20, 0, 0, Color.BLACK, Color.TRANSPARENT);
        assertNotNull(shape);
        Node node = shape.draw();
        assertTrue(node instanceof Line);

        Line line = (Line) node;
        assertEquals(10.0, line.getStartX());
        assertEquals(20.0, line.getStartY());
        assertEquals(110.0, line.getEndX()); // +100 di default
        assertEquals(20.0, line.getEndY());
        assertEquals(Color.BLACK, line.getStroke());
    }
}

