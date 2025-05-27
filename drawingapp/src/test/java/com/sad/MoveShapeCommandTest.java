package com.sad;

import com.sad.models.Model;
import com.sad.models.ShapeInterface;
import com.sad.models.command.MoveShapeCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the MoveShapeCommand class.
 */
public class MoveShapeCommandTest {

    private Model mockModel;
    private ShapeInterface mockShape;
    private MoveShapeCommand command;

    private double[] initialCoords;
    private double[] finalCoords;

    @BeforeEach
    void setUp() {
        mockModel = mock(Model.class);
        mockShape = mock(ShapeInterface.class);

        initialCoords = new double[]{50.0, 60.0};
        finalCoords = new double[]{100.0, 120.0};

        command = new MoveShapeCommand(mockModel, mockShape, initialCoords, finalCoords);
    }

    @Test
    void testExecuteMovesShapeToFinalCoords() {
        command.execute();
        verify(mockModel).moveShape(mockShape, finalCoords);
    }

    @Test
    void testUndoMovesShapeBackToInitialCoords() {
        command.undo();
        verify(mockModel).moveShape(mockShape, initialCoords);
    }

    @Test
    void testExecuteDoesNothingIfShapeIsNull() {
        command = new MoveShapeCommand(mockModel, null, initialCoords, finalCoords);
        command.execute();
        verify(mockModel, never()).moveShape(any(), any());
    }

    @Test
    void testUndoDoesNothingIfShapeIsNull() {
        command = new MoveShapeCommand(mockModel, null, initialCoords, finalCoords);
        command.undo();
        verify(mockModel, never()).moveShape(any(), any());
    }
}
