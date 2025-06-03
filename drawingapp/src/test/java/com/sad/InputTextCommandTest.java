package com.sad;

import com.sad.models.Model;
import com.sad.models.command.InsertTextCommand;
import com.sad.models.shapes.ShapeInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class InsertTextCommandTest {

    private Model modelMock;
    private ShapeInterface textShapeMock;
    private InsertTextCommand command;

    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        textShapeMock = mock(ShapeInterface.class);

        command = new InsertTextCommand(modelMock, textShapeMock);
    }

    @Test
    void testExecuteAddsTextShape() {
        command.execute();
        verify(modelMock).addShape(textShapeMock);
    }

    @Test
    void testUndoDeletesTextShape() {
        command.execute(); // Simula inserimento
        command.undo();    // Annulla inserimento
        verify(modelMock).deleteShape(textShapeMock);
    }

    @Test
    void testUndoDoesNothingIfTextShapeIsNull() {
        InsertTextCommand nullCommand = new InsertTextCommand(modelMock, null);
        nullCommand.undo();

        // Assicura che deleteShape NON venga chiamato se il testo è null
        verify(modelMock, never()).deleteShape(any());
    }
}
