/**
 * Unit tests for the DeleteShapeCommand class.
 * Verifies the behavior of deleting a shape and undoing the action to restore the shape.
 */
package com.sad;

import com.sad.models.Model;
import com.sad.models.command.DeleteShapeCommand;
import com.sad.models.shapes.ShapeInterface;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Test class for DeleteShapeCommand.
 */
class DeleteShapeCommandTest {

    /** Mocked instance of the Model class. */
    private Model modelMock;
    /** Mocked instance of the ShapeInterface. */
    private ShapeInterface shapeMock;
    /** Instance of the DeleteShapeCommand being tested. */
    private DeleteShapeCommand command;

    /** Original X coordinate of the shape before deletion. */
    private final double originalX = 30.0;
    /** Original Y coordinate of the shape before deletion. */
    private final double originalY = 40.0;
    /** Original border color of the shape before deletion. */
    private final Color originalBorder = Color.BLACK;
    /** Original fill color of the shape before deletion. */
    private final Color originalFill = Color.GREEN;
    /** Stubbed instance of a Rectangle node (not mocked). */
    private final Rectangle nodeStub = new Rectangle();

    /**
     * Sets up the test environment before each test.
     * Initializes mocked objects, a real Node instance, and the command instance.
     */
    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        shapeMock = mock(ShapeInterface.class);

        // Configure the behavior of the mocked shape
        when(shapeMock.getX()).thenReturn(originalX);
        when(shapeMock.getY()).thenReturn(originalY);
        when(shapeMock.getNode()).thenReturn(nodeStub);
        when(shapeMock.getBorderColor()).thenReturn(originalBorder);
        when(shapeMock.getFillColor()).thenReturn(originalFill);

        command = new DeleteShapeCommand(modelMock, shapeMock);
    }

    /**
     * Tests the execute method of DeleteShapeCommand.
     * Verifies that the shape is deleted from the model.
     */
    @Test
    void testExecuteDeletesShapeFromModel() {
        command.execute();
        verify(modelMock).deleteShape(shapeMock);
    }

    /**
     * Tests the undo method of DeleteShapeCommand.
     * Verifies that the shape's original state is restored and added back to the model.
     */
    @Test
    void testUndoRestoresShapeAndAddsBackToModel() {
        command.execute(); // Simulate deletion
        command.undo();    // Simulate undo

        verify(shapeMock).setX(originalX);
        verify(shapeMock).setY(originalY);
        verify(shapeMock).setBorderColor(originalBorder);
        verify(shapeMock).setFillColor(originalFill);
        verify(shapeMock).setNode(nodeStub);
        verify(modelMock).addShape(shapeMock);
    }
}
