package com.sad;

import com.sad.models.Model;
import com.sad.models.command.DrawPolygonCommand;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DrawPolygonCommandTest {

    private Model modelMock;
    private Pane pane;
    private ColorPicker borderColorPicker;
    private ColorPicker fillColorPicker;
    private List<Double> points;
    private DrawPolygonCommand command;

    @BeforeEach
    void setUp() {
        pane = new Pane();
        modelMock = mock(Model.class);

        // Stub del metodo getPane() per restituire il pane reale
        when(modelMock.getPane()).thenReturn(pane);

        borderColorPicker = new ColorPicker(Color.RED);
        fillColorPicker = new ColorPicker(Color.GREEN);

        points = Arrays.asList(10.0, 10.0, 100.0, 10.0, 50.0, 80.0);

        command = new DrawPolygonCommand(modelMock, points, borderColorPicker, fillColorPicker);
    }

    @Test
    void testExecuteCallsCreatePolygon() {
        command.execute();

        verify(modelMock).createPolygon(points, Color.RED, Color.GREEN);
    }

    @Test
    void testUndoRemovesLastShapeIfPresent() {
        Polygon dummyPolygon = new Polygon();
        pane.getChildren().add(dummyPolygon);

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
