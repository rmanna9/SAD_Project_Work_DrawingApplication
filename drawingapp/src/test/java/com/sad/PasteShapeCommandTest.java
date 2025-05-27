package com.sad;

import com.sad.models.Model;
import com.sad.models.ShapeInterface;
import com.sad.models.command.PasteShapeCommand;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PasteShapeCommandTest {

    private Model modelMock;
    private ShapeInterface pastedShapeMock;
    private PasteShapeCommand command;

    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        pastedShapeMock = mock(ShapeInterface.class);
        
        // Quando pasteShape viene chiamato, restituisci la forma mockata
        when(modelMock.pasteShape(anyDouble(), anyDouble())).thenReturn(pastedShapeMock);

        // Costruisci il comando con coordinate arbitrarie
        command = new PasteShapeCommand(modelMock, 10.0, 20.0);
    }

    @Test
    void testExecuteCallsPasteShape() {
        command.execute();
        // Verifica che pasteShape sia stato chiamato con le coordinate giuste
        verify(modelMock).pasteShape(10.0, 20.0);
    }

    @Test
    void testUndoCallsDeleteShapeAfterExecute() {
        command.execute();
        command.undo();
        // Verifica che deleteShape sia stato chiamato sulla forma incollata
        verify(modelMock).deleteShape(pastedShapeMock);
    }

    @Test
    void testUndoDoesNothingIfExecuteNotCalled() {
        // Chiamo solo undo senza eseguire prima execute
        command.undo();
        // Verifica che deleteShape non venga mai chiamato (perché newShape è null)
        verify(modelMock, never()).deleteShape(any());
    }
}
