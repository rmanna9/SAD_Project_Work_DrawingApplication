package com.sad;

import com.sad.models.Model;
import com.sad.models.command.EditFontSizeCommand;
import com.sad.models.shapes.ConcreteText;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class EditFontSizeCommandTest {

    private Model modelMock;
    private ConcreteText textShapeMock;
    private EditFontSizeCommand command;

    private final double oldSize = 14.0;
    private final double newSize = 24.0;

    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        textShapeMock = mock(ConcreteText.class);

        // Stub: font size originale della shape
        when(textShapeMock.getFontSize()).thenReturn(oldSize);

        command = new EditFontSizeCommand(modelMock, textShapeMock, newSize);
    }

    @Test
    void testExecuteCallsEditFontSizeWithNewValue() {
        command.execute();
        verify(modelMock).editFontSize(textShapeMock, newSize);
    }

    @Test
    void testUndoRestoresOldFontSize() {
        command.execute(); // Simula l'esecuzione prima dell'annullamento
        command.undo();

        // Verifica che il font venga reimpostato al valore originale
        verify(modelMock).editFontSize(textShapeMock, oldSize);
    }
}
