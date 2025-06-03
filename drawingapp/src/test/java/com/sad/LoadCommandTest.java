/**
 * Unit tests for the LoadCommand class.
 * Verifies the behavior of executing the load operation and ensures that the undo operation does nothing.
 */
package com.sad;

import com.sad.models.Model;
import com.sad.models.command.LoadCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Test class for LoadCommand.
 */
class LoadCommandTest {

    /** Mocked instance of the Model class. */
    private Model modelMock;
    /** Instance of the LoadCommand being tested. */
    private LoadCommand loadCommand;

    /**
     * Sets up the test environment before each test.
     * Initializes mocked objects and the command instance.
     */
    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        loadCommand = new LoadCommand(modelMock);
    }

    /**
     * Tests the execute method of LoadCommand.
     * Verifies that the load method is called on the model.
     */
    @Test
    void testExecuteCallsLoadOnReceiver() {
        loadCommand.execute();
        verify(modelMock, times(1)).load();
    }

    /**
     * Tests the undo method of LoadCommand.
     * Verifies that the undo operation does nothing and no exceptions are thrown.
     */
    @Test
    void testUndoDoesNothing() {
        // undo() should do nothing, so just call it to verify no exceptions
        loadCommand.undo();
        // No verification needed, just ensure no exceptions thrown
    }
}
