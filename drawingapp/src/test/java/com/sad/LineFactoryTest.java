package com.sad;

import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.sad.models.*;

public class LineFactoryTest {

    private LineFactory factory;

    @BeforeEach
    public void setup() {
        factory = new LineFactory();
    }

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

