/**
 * Unit tests for the PasteShapeCommand class.
 * Verifies the behavior of pasting a shape at specific coordinates and undoing the action to delete the pasted shape.
 */
package com.sad;

import com.sad.models.Model;
import com.sad.models.command.PasteShapeCommand;
import com.sad.models.shapes.ShapeInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Test class for PasteShapeCommand.
 */
class PasteShapeCommandTest {

    /** Mocked instance of the Model class. */
    private Model modelMock;
    /** Mocked instance of the ShapeInterface representing the pasted shape. */
    private ShapeInterface pastedShapeMock;
    /** Instance of the PasteShapeCommand being tested. */
    private PasteShapeCommand command;

    /**
     * Sets up the test environment before each test.
     * Initializes mocked objects and the command instance with arbitrary coordinates.
     */
    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        pastedShapeMock = mock(ShapeInterface.class);

        // When pasteShape is called, return the mocked shape
        when(modelMock.pasteShape(anyDouble(), anyDouble())).thenReturn(pastedShapeMock);

        // Construct the command with arbitrary coordinates
        command = new PasteShapeCommand(modelMock, 10.0, 20.0);
    }

    /**
     * Tests the execute method of PasteShapeCommand.
     * Verifies that the pasteShape method is called on the model with the correct coordinates.
     */
    @Test
    void testExecuteCallsPasteShape() {
        command.execute();
        verify(modelMock).pasteShape(10.0, 20.0);
    }

    /**
     * Tests the undo method of PasteShapeCommand after execute is called.
     * Verifies that the deleteShape method is called on the pasted shape.
     */
    @Test
    void testUndoCallsDeleteShapeAfterExecute() {
        command.execute();
        command.undo();
        verify(modelMock).deleteShape(pastedShapeMock);
    }

    /**
     * Tests the undo method of PasteShapeCommand when execute is not called.
     * Verifies that the deleteShape method is not called.
     */
    @Test
    void testUndoDoesNothingIfExecuteNotCalled() {
        command.undo();
        verify(modelMock, never()).deleteShape(any());
    }
}
