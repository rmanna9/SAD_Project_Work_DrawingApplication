package com.sad;

import com.sad.models.Model;
import com.sad.models.ShapeInterface;
import com.sad.models.command.CopyShapeCommand;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class CopyShapeCommandTest {

    private Model modelMock;
    private ShapeInterface shapeMock;
    private CopyShapeCommand command;

    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        shapeMock = mock(ShapeInterface.class);

        command = new CopyShapeCommand(modelMock, shapeMock);
    }

    @Test
    void testExecuteCallsCopyShape() {
        command.execute();
        verify(modelMock).copyShape(shapeMock);
    }

    @Test
    void testUndoDoesNothing() {
        command.undo();
        // Nessuna interazione dovrebbe avvenire durante undo
        verifyNoInteractions(modelMock);
        verifyNoInteractions(shapeMock);
    }
}