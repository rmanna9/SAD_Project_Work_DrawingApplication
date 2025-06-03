/**
 * Unit tests for the SendToBackCommand class.
 * Verifies the behavior of sending a shape to the back and undoing the action to bring the shape to the front.
 */
package com.sad;

import com.sad.models.Model;
import com.sad.models.command.SendToBackCommand;
import com.sad.models.shapes.ShapeInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Test class for SendToBackCommand.
 */
class SendToBackCommandTest {

    /** Mocked instance of the Model class. */
    private Model modelMock;
    /** Mocked instance of the ShapeInterface. */
    private ShapeInterface shapeMock;
    /** Instance of the SendToBackCommand being tested. */
    private SendToBackCommand command;

    /**
     * Sets up the test environment before each test.
     * Initializes mocked objects and the command instance.
     */
    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        shapeMock = mock(ShapeInterface.class);
        command = new SendToBackCommand(modelMock, shapeMock);
    }

    /**
     * Tests the execute method of SendToBackCommand.
     * Verifies that the sendShapeToBack method is called on the model with the correct shape.
     */
    @Test
    void testExecuteCallsSendShapeToBack() {
        command.execute();
        verify(modelMock, times(1)).sendShapeToBack(shapeMock);
    }

    /**
     * Tests the undo method of SendToBackCommand.
     * Verifies that the bringShapeToFront method is called on the model with the correct shape.
     */
    @Test
    void testUndoCallsBringShapeToFront() {
        command.undo();
        verify(modelMock, times(1)).bringShapeToFront(shapeMock);
    }
}
