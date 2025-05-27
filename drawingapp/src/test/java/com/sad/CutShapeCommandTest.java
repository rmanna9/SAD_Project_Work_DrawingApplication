package com.sad;

import com.sad.models.Model;
import com.sad.models.ShapeInterface;
import com.sad.models.command.CutShapeCommand;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class CutShapeCommandTest {

    private Model modelMock;
    private ShapeInterface shapeMock;
    private Node nodeStub;  // NON mockato!
    private Color borderColor;
    private Color fillColor;
    private CutShapeCommand command;

    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        shapeMock = mock(ShapeInterface.class);

        // Usa un nodo reale al posto del mock (risolve il crash con JavaFX e ByteBuddy)
        nodeStub = new Rectangle();

        borderColor = Color.BLACK;
        fillColor = Color.RED;

        // Stub del comportamento iniziale della shape
        when(shapeMock.getX()).thenReturn(50.0);
        when(shapeMock.getY()).thenReturn(100.0);
        when(shapeMock.getNode()).thenReturn(nodeStub);
        when(shapeMock.getBorderColor()).thenReturn(borderColor);
        when(shapeMock.getFillColor()).thenReturn(fillColor);

        command = new CutShapeCommand(modelMock, shapeMock);
    }

    @Test
    void testExecuteCallsCutShape() {
        command.execute();
        verify(modelMock).cutShape(shapeMock);
    }

    @Test
    void testUndoRestoresShapeAndAddsBackToModel() {
        command.execute(); // Simula l'esecuzione del taglio
        command.undo();    // Ripristina lo stato originale

        verify(shapeMock).setX(50.0);
        verify(shapeMock).setY(100.0);
        verify(shapeMock).setBorderColor(borderColor);
        verify(shapeMock).setFillColor(fillColor);
        verify(shapeMock).setNode(nodeStub);
        verify(modelMock).addShape(shapeMock);
    }
}
