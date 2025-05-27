package com.sad;

import com.sad.models.Model;
import com.sad.models.command.SaveCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class SaveCommandTest {

    private Model modelMock;
    private SaveCommand command;

    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        command = new SaveCommand(modelMock);
    }

    @Test
    void testExecuteCallsSaveOnModel() {
        command.execute();
        verify(modelMock, times(1)).save();
    }

    @Test
    void testUndoDoesNothing() {
        // L’undo non deve fare nulla, quindi nessuna interazione col model
        command.undo();
        verifyNoInteractions(modelMock);
    }
}
