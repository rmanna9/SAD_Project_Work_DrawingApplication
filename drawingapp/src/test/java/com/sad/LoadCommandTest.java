package com.sad;

import com.sad.models.Model;
import com.sad.models.command.LoadCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class LoadCommandTest {

    private Model modelMock;
    private LoadCommand loadCommand;

    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        loadCommand = new LoadCommand(modelMock);
    }

    @Test
    void testExecuteCallsLoadOnReceiver() {
        loadCommand.execute();
        verify(modelMock, times(1)).load();
    }

    @Test
    void testUndoDoesNothing() {
        // undo() should do nothing, so just call it to verify no exceptions
        loadCommand.undo();
        // No verification needed, just ensure no exceptions thrown
    }
}
