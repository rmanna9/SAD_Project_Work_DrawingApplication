/**
 * Unit tests for the Model class.
 * Verifies the behavior of various operations such as creating, moving, copying, cutting, pasting,
 * deleting, resizing, changing colors, and managing shapes in the pane.
 */
package com.sad;

import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sad.models.*;
import com.sad.models.shapes.ConcreteRectangle;
import com.sad.models.shapes.ShapeInterface;

import java.util.concurrent.CountDownLatch;

/**
 * Test class for the Model class.
 */
public class ModelTest {

    /** Pane instance used for testing shape operations. */
    private Pane pane;
    /** Instance of the Model class being tested. */
    private Model model;

    /** Flag to ensure JavaFX is initialized only once. */
    private static boolean jfxInitialized = false;

    /**
     * Initializes JavaFX before all tests.
     * Ensures that JavaFX is properly started for testing.
     * @throws InterruptedException if the initialization is interrupted
     */
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

    /**
     * Sets up the test environment before each test.
     * Initializes the pane and the model instance.
     */
    @BeforeEach
    public void setup() {
        pane = new Pane();
        model = new Model(pane);
    }

    /**
     * Tests the creation of a shape.
     * Verifies that the shape is added to the pane and is not null.
     */
    @Test
    public void testCreateShape() {
        model.setCurrentFactory(new RectangleFactory());
        ShapeInterface shape = model.createShape(10, 20, 100, 50, Color.BLACK, Color.RED);
        assertNotNull(shape);
        assertEquals(1, pane.getChildren().size());
    }

    /**
     * Tests moving a shape.
     * Verifies that the shape's coordinates are updated correctly.
     */
    @Test
    public void testMoveShape() {
        ShapeInterface shape = new ConcreteRectangle(10, 20, 100, 50, Color.BLACK, Color.RED);
        shape.draw();
        model.moveShape(shape, new double[]{50, 70});
        assertEquals(50, shape.getX());
        assertEquals(70, shape.getY());
    }

    /**
     * Tests copying, cutting, and pasting a shape.
     * Verifies that the clipboard contains the copied shape, the cut shape is removed,
     * and the pasted shape is added to the pane.
     */
    @Test
    public void testCopyCutPasteShape() {
        ShapeInterface original = new ConcreteRectangle(0, 0, 100, 50, Color.BLUE, Color.GREEN);
        original.draw();
        model.copyShape(original);
        ShapeInterface clipboardCopy = model.getClipBoardShape();
        assertNotNull(clipboardCopy);
        assertEquals(0, clipboardCopy.getX());

        model.cutShape(original);
        assertEquals(0, original.getX()); // remains 0 but removed from the pane
        assertEquals(0, pane.getChildren().size());

        ShapeInterface pasted = model.pasteShape(200, 200);
        assertEquals(200, pasted.getX());
        assertEquals(1, pane.getChildren().size());
    }

    /**
     * Tests deleting a shape.
     * Verifies that the shape is removed from the pane.
     */
    @Test
    public void testDeleteShape() {
        ShapeInterface shape = new ConcreteRectangle(10, 10, 100, 100, Color.BLACK, Color.YELLOW);
        shape.draw();
        model.addShape(shape); // adds to the pane
        model.deleteShape(shape);
        assertFalse(pane.getChildren().contains(shape.getNode()));
    }

    /**
     * Tests resizing a shape.
     * Verifies that the shape's width and height are updated correctly.
     */
    @Test
    public void testResizeShape() {
        ShapeInterface shape = new ConcreteRectangle(0, 0, 50, 50, Color.BLACK, Color.RED);
        shape.draw();
        model.resizeShape(shape, 150, 100);
        assertEquals(150, shape.getWidth());
        assertEquals(100, shape.getHeight());
    }

    /**
     * Tests changing the color of a shape.
     * Verifies that the border and fill colors are updated correctly.
     */
    @Test
    public void testChangeColor() {
        ShapeInterface shape = new ConcreteRectangle(0, 0, 50, 50, Color.BLACK, Color.RED);
        shape.draw();
        model.changeShapeColor(shape, Color.GREEN, Color.BLUE);
        assertEquals(Color.GREEN, shape.getBorderColor());
        assertEquals(Color.BLUE, shape.getFillColor());
    }

    /**
     * Tests bringing a shape to the front and sending a shape to the back.
     * Verifies that the shape's position in the pane's children list is updated correctly.
     */
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

    /**
     * Tests adding a shape to the pane.
     * Verifies that the shape is present in the pane's children list.
     */
    @Test
    public void testAddShape() {
        ShapeInterface shape = new ConcreteRectangle(10, 10, 100, 100, Color.BLACK, Color.RED);
        shape.draw();
        model.addShape(shape);
        assertTrue(pane.getChildren().contains(shape.getNode()));
    }
}
