package com.sad;

import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.sad.models.*;

public class EllipseFactoryTest {

    private EllipseFactory factory;

    @BeforeEach
    public void setup() {
        factory = new EllipseFactory();
    }

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

