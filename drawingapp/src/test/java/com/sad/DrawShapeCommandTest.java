package com.sad;

import com.sad.models.Model;
import com.sad.models.command.DrawShapeCommand;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DrawShapeCommandTest {

    private Model modelMock;
    private Pane pane;
    private DrawShapeCommand command;

    private final double x = 100;
    private final double y = 150;
    private final Color borderColor = Color.BLUE;
    private final Color fillColor = Color.YELLOW;

    @BeforeEach
    void setUp() {
        pane = new Pane();
        modelMock = mock(Model.class);

        // Stub del metodo getPane() per restituire il pane reale
        when(modelMock.getPane()).thenReturn(pane);

        command = new DrawShapeCommand(modelMock, x, y, borderColor, fillColor);
    }

    @Test
    void testExecuteCallsCreateShape() {
        command.execute();
        verify(modelMock).createShape(x, y, 0, 0, borderColor, fillColor);
    }

    @Test
    void testUndoRemovesLastShapeIfPresent() {
        // Aggiungiamo un nodo fittizio al Pane
        Rectangle dummyShape = new Rectangle();
        pane.getChildren().add(dummyShape);

        assertEquals(1, pane.getChildren().size());

        command.undo();

        assertEquals(0, pane.getChildren().size());
    }

    @Test
    void testUndoDoesNothingIfPaneIsEmpty() {
        assertEquals(0, pane.getChildren().size());

        // Non deve lanciare eccezioni né rimuovere nulla
        command.undo();

        assertEquals(0, pane.getChildren().size());
    }
}
