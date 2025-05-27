package com.sad;

import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sad.models.*;

import java.util.concurrent.CountDownLatch;

public class ModelTest {

    private Pane pane;
    private Model model;

    private static boolean jfxInitialized = false;

    @BeforeAll
    public static void initJavaFX() throws InterruptedException {
        if (!jfxInitialized) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(() -> {
                // JavaFX is initialized
                latch.countDown();
            });
            latch.await();
            jfxInitialized = true;
        }
    }

    @BeforeEach
    public void setup() {
        pane = new Pane();
        model = new Model(pane);
    }

    @Test
    public void testCreateShape() {
        model.setCurrentFactory(new RectangleFactory());
        ShapeInterface shape = model.createShape(10, 20, 100, 50, Color.BLACK, Color.RED);
        assertNotNull(shape);
        assertEquals(1, pane.getChildren().size());
    }

    @Test
    public void testMoveShape() {
        ShapeInterface shape = new ConcreteRectangle(10, 20, 100, 50, Color.BLACK, Color.RED);
        shape.draw();
        model.moveShape(shape, new double[]{50, 70});
        assertEquals(50, shape.getX());
        assertEquals(70, shape.getY());
    }

    @Test
    public void testCopyCutPasteShape() {
        ShapeInterface original = new ConcreteRectangle(0, 0, 100, 50, Color.BLUE, Color.GREEN);
        original.draw();
        model.copyShape(original);
        ShapeInterface clipboardCopy = model.getClipBoardShape();
        assertNotNull(clipboardCopy);
        assertEquals(0, clipboardCopy.getX());

        model.cutShape(original);
        assertEquals(0, original.getX()); // rimane 0 ma rimosso dal pane
        assertEquals(0, pane.getChildren().size());

        ShapeInterface pasted = model.pasteShape(200, 200);
        assertEquals(200, pasted.getX());
        assertEquals(1, pane.getChildren().size());
    }

    @Test
    public void testDeleteShape() {
        ShapeInterface shape = new ConcreteRectangle(10, 10, 100, 100, Color.BLACK, Color.YELLOW);
        shape.draw();
        model.addShape(shape); // aggiunge al pane
        model.deleteShape(shape);
        assertFalse(pane.getChildren().contains(shape.getNode()));
    }

    @Test
    public void testResizeShape() {
        ShapeInterface shape = new ConcreteRectangle(0, 0, 50, 50, Color.BLACK, Color.RED);
        shape.draw();
        model.resizeShape(shape, 150, 100);
        assertEquals(150, shape.getWidth());
        assertEquals(100, shape.getHeight());
    }

    @Test
    public void testChangeColor() {
        ShapeInterface shape = new ConcreteRectangle(0, 0, 50, 50, Color.BLACK, Color.RED);
        shape.draw();
        model.changeShapeColor(shape, Color.GREEN, Color.BLUE);
        assertEquals(Color.GREEN, shape.getBorderColor());
        assertEquals(Color.BLUE, shape.getFillColor());
    }

    @Test
    public void testBringToFrontAndSendToBack() {
        ShapeInterface shape1 = new ConcreteRectangle(0, 0, 100, 100, Color.BLACK, Color.RED);
        ShapeInterface shape2 = new ConcreteRectangle(0, 0, 100, 100, Color.BLACK, Color.BLUE);

        shape1.draw();
        shape2.draw();

        model.addShape(shape1);
        model.addShape(shape2);

        model.bringShapeToFront(shape1);
        assertEquals(pane.getChildren().get(pane.getChildren().size() - 1), shape1.getNode());

        model.sendShapeToBack(shape2);
        assertEquals(pane.getChildren().get(0), shape2.getNode());
    }

    @Test
    public void testAddShape() {
        ShapeInterface shape = new ConcreteRectangle(10, 10, 100, 100, Color.BLACK, Color.RED);
        shape.draw();
        model.addShape(shape);
        assertTrue(pane.getChildren().contains(shape.getNode()));
    }
}
