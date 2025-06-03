/**
 * Unit tests for the CopyShapeCommand class.
 * Verifies the behavior of copying a shape and ensures that the undo operation does nothing.
 */
package com.sad;

import com.sad.models.Model;
import com.sad.models.command.CopyShapeCommand;
import com.sad.models.shapes.ShapeInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Test class for CopyShapeCommand.
 */
class CopyShapeCommandTest {

    /** Mocked instance of the Model class. */
    private Model modelMock;
    /** Mocked instance of the ShapeInterface. */
    private ShapeInterface shapeMock;
    /** Instance of the CopyShapeCommand being tested. */
    private CopyShapeCommand command;

    /**
     * Sets up the test environment before each test.
     * Initializes mocked objects and the command instance.
     */
    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        shapeMock = mock(ShapeInterface.class);

        command = new CopyShapeCommand(modelMock, shapeMock);
    }

    /**
     * Tests the execute method of CopyShapeCommand.
     * Verifies that the copyShape method is called on the model with the correct shape.
     */
    @Test
    void testExecuteCallsCopyShape() {
        command.execute();
        verify(modelMock).copyShape(shapeMock);
    }

    /**
     * Tests the undo method of CopyShapeCommand.
     * Verifies that the undo operation does nothing and no interactions occur.
     */
    @Test
    void testUndoDoesNothing() {
        command.undo();
        // No interactions should occur during undo
        verifyNoInteractions(modelMock);
        verifyNoInteractions(shapeMock);
    }
}