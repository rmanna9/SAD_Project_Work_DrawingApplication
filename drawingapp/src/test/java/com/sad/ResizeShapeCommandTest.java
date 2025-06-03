/**
 * Unit tests for the ResizeShapeCommand class.
 * Verifies the behavior of resizing a shape and undoing the action to restore the original dimensions.
 */
package com.sad;

import com.sad.models.Model;
import com.sad.models.command.ResizeShapeCommand;
import com.sad.models.shapes.ShapeInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Test class for ResizeShapeCommand.
 */
class ResizeShapeCommandTest {

    /** Mocked instance of the Model class. */
    private Model modelMock;
    /** Mocked instance of the ShapeInterface. */
    private ShapeInterface shapeMock;
    /** Instance of the ResizeShapeCommand being tested. */
    private ResizeShapeCommand command;

    /**
     * Sets up the test environment before each test.
     * Initializes mocked objects and the command instance with a scaling factor.
     */
    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        shapeMock = mock(ShapeInterface.class);

        // Stub the initial dimensions of the shape
        when(shapeMock.getWidth()).thenReturn(100.0);
        when(shapeMock.getHeight()).thenReturn(50.0);

        // Create the command with a scaling factor of 2.0
        command = new ResizeShapeCommand(modelMock, shapeMock, 2.0);
    }

    /**
     * Tests the execute method of ResizeShapeCommand.
     * Verifies that the resizeShape method is called with scaled dimensions.
     */
    @Test
    void testExecuteCallsResizeWithScaledValues() {
        command.execute();
        verify(modelMock).resizeShape(shapeMock, 200.0, 100.0);
    }

    /**
     * Tests the undo method of ResizeShapeCommand.
     * Verifies that the resizeShape method is called with the original dimensions.
     */
    @Test
    void testUndoCallsResizeWithOriginalValues() {
        command.execute();  // Simulate the resize operation
        command.undo();     // Undo the resize operation
        verify(modelMock).resizeShape(shapeMock, 100.0, 50.0);
    }
}
