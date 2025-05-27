package com.sad;

import com.sad.models.Model;
import com.sad.models.ShapeInterface;
import com.sad.models.command.ChangeShapeColorCommand;

import javafx.scene.paint.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ChangeShapeColorCommandTest {

    private Model modelMock;
    private ShapeInterface shapeMock;
    private ChangeShapeColorCommand command;

    private final Color oldBorderColor = Color.BLACK;
    private final Color oldFillColor = Color.WHITE;
    private final Color newBorderColor = Color.BLUE;
    private final Color newFillColor = Color.YELLOW;

    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        shapeMock = mock(ShapeInterface.class);

        // Comportamento simulato della shape
        when(shapeMock.getBorderColor()).thenReturn(oldBorderColor);
        when(shapeMock.getFillColor()).thenReturn(oldFillColor);

        command = new ChangeShapeColorCommand(modelMock, shapeMock, newBorderColor, newFillColor);
    }

    @Test
    void testExecuteChangesShapeColor() {
        command.execute();
        verify(modelMock).changeShapeColor(shapeMock, newBorderColor, newFillColor);
    }

    @Test
    void testUndoRestoresOldColors() {
        command.undo();
        verify(modelMock).changeShapeColor(shapeMock, oldBorderColor, oldFillColor);
    }
}
