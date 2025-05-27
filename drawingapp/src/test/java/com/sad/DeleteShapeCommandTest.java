package com.sad;

import com.sad.models.Model;
import com.sad.models.ShapeInterface;
import com.sad.models.command.DeleteShapeCommand;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class DeleteShapeCommandTest {

    private Model modelMock;
    private ShapeInterface shapeMock;
    private DeleteShapeCommand command;

    private final double originalX = 30.0;
    private final double originalY = 40.0;
    private final Color originalBorder = Color.BLACK;
    private final Color originalFill = Color.GREEN;
    private final Rectangle nodeStub = new Rectangle(); // Sostituisce mock(Node)

    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        shapeMock = mock(ShapeInterface.class);

        // Configura il comportamento del mock della forma
        when(shapeMock.getX()).thenReturn(originalX);
        when(shapeMock.getY()).thenReturn(originalY);
        when(shapeMock.getNode()).thenReturn(nodeStub);
        when(shapeMock.getBorderColor()).thenReturn(originalBorder);
        when(shapeMock.getFillColor()).thenReturn(originalFill);

        command = new DeleteShapeCommand(modelMock, shapeMock);
    }

    @Test
    void testExecuteDeletesShapeFromModel() {
        command.execute();
        verify(modelMock).deleteShape(shapeMock);
    }

    @Test
    void testUndoRestoresShapeAndAddsBackToModel() {
        command.execute(); // Simula cancellazione
        command.undo();    // Simula annullamento

        verify(shapeMock).setX(originalX);
        verify(shapeMock).setY(originalY);
        verify(shapeMock).setBorderColor(originalBorder);
        verify(shapeMock).setFillColor(originalFill);
        verify(shapeMock).setNode(nodeStub);
        verify(modelMock).addShape(shapeMock);
    }
}
