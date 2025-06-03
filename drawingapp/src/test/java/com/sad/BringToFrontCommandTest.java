/**
 * Unit tests for the BringToFrontCommand class.
 * Verifies the behavior of bringing a shape to the front and undoing the action.
 */
package com.sad;

import com.sad.models.Model;
import com.sad.models.command.BringToFrontCommand;
import com.sad.models.shapes.ShapeInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Test class for BringToFrontCommand.
 */
class BringToFrontCommandTest {

    /** Mocked instance of the Model class. */
    private Model modelMock;
    /** Mocked instance of the ShapeInterface. */
    private ShapeInterface shapeMock;
    /** Instance of the BringToFrontCommand being tested. */
    private BringToFrontCommand command;

    /**
     * Sets up the test environment before each test.
     * Initializes mocked objects and the command instance.
     */
    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        shapeMock = mock(ShapeInterface.class);
        command = new BringToFrontCommand(modelMock, shapeMock);
    }

    /**
     * Tests the execute method of BringToFrontCommand.
     * Verifies that the shape is brought to the front using the model.
     */
    @Test
    void testExecuteBringsShapeToFront() {
        command.execute();
        verify(modelMock).bringShapeToFront(shapeMock);
    }

    /**
     * Tests the undo method of BringToFrontCommand.
     * Verifies that the shape is sent to the back using the model.
     */
    @Test
    void testUndoSendsShapeToBack() {
        command.undo();
        verify(modelMock).sendShapeToBack(shapeMock);
    }
}
