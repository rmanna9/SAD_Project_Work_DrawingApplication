package com.sad;

import com.sad.models.Model;
import com.sad.models.command.MoveShapeCommand;
import com.sad.models.shapes.ShapeInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the MoveShapeCommand class.
 * Verifies the behavior of moving a shape to new coordinates and undoing the action to restore the original coordinates.
 */
public class MoveShapeCommandTest {

    /** Mocked instance of the Model class. */
    private Model mockModel;
    /** Mocked instance of the ShapeInterface. */
    private ShapeInterface mockShape;
    /** Instance of the MoveShapeCommand being tested. */
    private MoveShapeCommand command;

    /** Initial coordinates of the shape before moving. */
    private double[] initialCoords;
    /** Final coordinates of the shape after moving. */
    private double[] finalCoords;

    /**
     * Sets up the test environment before each test.
     * Initializes mocked objects, coordinates, and the command instance.
     */
    @BeforeEach
    void setUp() {
        mockModel = mock(Model.class);
        mockShape = mock(ShapeInterface.class);

        initialCoords = new double[]{50.0, 60.0};
        finalCoords = new double[]{100.0, 120.0};

        command = new MoveShapeCommand(mockModel, mockShape, initialCoords, finalCoords);
    }

    /**
     * Tests the execute method of MoveShapeCommand.
     * Verifies that the shape is moved to the final coordinates using the model.
     */
    @Test
    void testExecuteMovesShapeToFinalCoords() {
        command.execute();
        verify(mockModel).moveShape(mockShape, finalCoords);
    }

    /**
     * Tests the undo method of MoveShapeCommand.
     * Verifies that the shape is moved back to the initial coordinates using the model.
     */
    @Test
    void testUndoMovesShapeBackToInitialCoords() {
        command.undo();
        verify(mockModel).moveShape(mockShape, initialCoords);
    }

    /**
     * Tests the execute method when the shape is null.
     * Verifies that no action is performed and no interactions occur.
     */
    @Test
    void testExecuteDoesNothingIfShapeIsNull() {
        command = new MoveShapeCommand(mockModel, null, initialCoords, finalCoords);
        command.execute();
        verify(mockModel, never()).moveShape(any(), any());
    }

    /**
     * Tests the undo method when the shape is null.
     * Verifies that no action is performed and no interactions occur.
     */
    @Test
    void testUndoDoesNothingIfShapeIsNull() {
        command = new MoveShapeCommand(mockModel, null, initialCoords, finalCoords);
        command.undo();
        verify(mockModel, never()).moveShape(any(), any());
    }
}
