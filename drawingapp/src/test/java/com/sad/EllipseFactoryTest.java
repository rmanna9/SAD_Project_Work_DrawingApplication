package com.sad;

import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.sad.models.*;
import com.sad.models.shapes.ShapeInterface;

/**
 * Unit test class for EllipseFactory.
 * Verifies that the factory correctly creates ConcreteEllipse objects
 * with the expected properties and that the draw method returns a valid JavaFX Ellipse node.
 */
public class EllipseFactoryTest {

    private EllipseFactory factory;

    /**
     * Sets up a new EllipseFactory instance before each test.
     */
    @BeforeEach
    public void setup() {
        factory = new EllipseFactory();
    }

    /**
     * Tests the creation of a ConcreteEllipse using the factory.
     * Asserts that the created shape is not null, is an Ellipse,
     * and that its properties match the expected default and provided values.
     */
    @Test
    public void testEllipseCreation() {
        ShapeInterface shape = factory.createShape(50, 60, 0, 0, Color.GREEN, Color.YELLOW);
        assertNotNull(shape);
        Node node = shape.draw();
        assertTrue(node instanceof Ellipse);

        Ellipse ellipse = (Ellipse) node;
        assertEquals(50.0, ellipse.getCenterX());
        assertEquals(60.0, ellipse.getCenterY());
        assertEquals(100.0, ellipse.getRadiusX()); // default width = 100
        assertEquals(50.0, ellipse.getRadiusY());  // default height = 50
        assertEquals(Color.GREEN, ellipse.getStroke());
        assertEquals(Color.YELLOW, ellipse.getFill());
    }
}

