/**
 * Unit tests for the DrawShapeCommand class.
 * Verifies the behavior of creating a shape and undoing the action to remove the shape.
 */
package com.sad;

import com.sad.models.Model;
import com.sad.models.command.DrawShapeCommand;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for DrawShapeCommand.
 */
class DrawShapeCommandTest {

    /** Mocked instance of the Model class. */
    private Model modelMock;
    /** Real instance of the Pane where shapes are drawn. */
    private Pane pane;
    /** Instance of the DrawShapeCommand being tested. */
    private DrawShapeCommand command;

    /** X coordinate for the shape's position. */
    private final double x = 100;
    /** Y coordinate for the shape's position. */
    private final double y = 150;
    /** Border color for the shape. */
    private final Color borderColor = Color.BLUE;
    /** Fill color for the shape. */
    private final Color fillColor = Color.YELLOW;

    /**
     * Sets up the test environment before each test.
     * Initializes mocked objects, a real Pane instance, and the command instance.
     */
    @BeforeEach
    void setUp() {
        pane = new Pane();
        modelMock = mock(Model.class);

        // Stub the getPane() method to return the real pane
        when(modelMock.getPane()).thenReturn(pane);

        command = new DrawShapeCommand(modelMock, x, y, borderColor, fillColor);
    }

    /**
     * Tests the execute method of DrawShapeCommand.
     * Verifies that the createShape method is called on the model with the correct parameters.
     */
    @Test
    void testExecuteCallsCreateShape() {
        command.execute();
        verify(modelMock).createShape(x, y, 0, 0, borderColor, fillColor);
    }

    /**
     * Tests the undo method of DrawShapeCommand.
     * Verifies that the last shape added to the pane is removed.
     */
    @Test
    void testUndoRemovesLastShapeIfPresent() {
        // Add a dummy shape to the Pane
        Rectangle dummyShape = new Rectangle();
        pane.getChildren().add(dummyShape);

        assertEquals(1, pane.getChildren().size());

        command.undo();

        assertEquals(0, pane.getChildren().size());
    }

    /**
     * Tests the undo method of DrawShapeCommand when the pane is empty.
     * Verifies that no exceptions are thrown and no changes occur.
     */
    @Test
    void testUndoDoesNothingIfPaneIsEmpty() {
        assertEquals(0, pane.getChildren().size());

        // Should not throw exceptions or remove anything
        command.undo();

        assertEquals(0, pane.getChildren().size());
    }
}
