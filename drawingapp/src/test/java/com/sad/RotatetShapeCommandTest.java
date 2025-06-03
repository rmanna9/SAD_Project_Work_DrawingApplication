package com.sad;

import com.sad.models.Model;
import com.sad.models.command.RotateShapeCommand;
import com.sad.models.shapes.ShapeInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class RotateShapeCommandTest {

    private Model modelMock;
    private ShapeInterface shapeMock;
    private RotateShapeCommand command;

    private final double oldAngle = 0.0;
    private final double newAngle = 90.0;

    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        shapeMock = mock(ShapeInterface.class);

        // Stub iniziale dell'angolo corrente
        when(shapeMock.getAngle()).thenReturn(oldAngle);

        command = new RotateShapeCommand(modelMock, shapeMock, newAngle);
    }

    @Test
    void testExecuteRotatesShapeToNewAngle() {
        command.execute();
        verify(modelMock).rotateShape(shapeMock, newAngle);
    }

    @Test
    void testUndoRotatesShapeBackToOldAngle() {
        command.execute(); // opzionale: simula un'esecuzione reale
        command.undo();
        verify(modelMock).rotateShape(shapeMock, oldAngle);
    }
}

