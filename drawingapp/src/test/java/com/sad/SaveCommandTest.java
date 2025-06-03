/**
 * Unit tests for the SaveCommand class.
 * Verifies the behavior of executing the save operation and ensures that the undo operation does nothing.
 */
package com.sad;

import com.sad.models.Model;
import com.sad.models.command.SaveCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Test class for SaveCommand.
 */
class SaveCommandTest {

    /** Mocked instance of the Model class. */
    private Model modelMock;
    /** Instance of the SaveCommand being tested. */
    private SaveCommand command;

    /**
     * Sets up the test environment before each test.
     * Initializes mocked objects and the command instance.
     */
    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        command = new SaveCommand(modelMock);
    }

    /**
     * Tests the execute method of SaveCommand.
     * Verifies that the save method is called on the model.
     */
    @Test
    void testExecuteCallsSaveOnModel() {
        command.execute();
        verify(modelMock, times(1)).save();
    }

    /**
     * Tests the undo method of SaveCommand.
     * Verifies that the undo operation does nothing and no interactions occur with the model.
     */
    @Test
    void testUndoDoesNothing() {
        command.undo();
        verifyNoInteractions(modelMock);
    }
}
