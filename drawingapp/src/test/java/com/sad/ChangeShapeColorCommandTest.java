/**
 * Unit tests for the ChangeShapeColorCommand class.
 * Verifies the behavior of changing a shape's color and undoing the action.
 */
package com.sad;

import com.sad.models.Model;
import com.sad.models.command.ChangeShapeColorCommand;
import com.sad.models.shapes.ShapeInterface;

import javafx.scene.paint.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Test class for ChangeShapeColorCommand.
 */
class ChangeShapeColorCommandTest {

    /** Mocked instance of the Model class. */
    private Model modelMock;
    /** Mocked instance of the ShapeInterface. */
    private ShapeInterface shapeMock;
    /** Instance of the ChangeShapeColorCommand being tested. */
    private ChangeShapeColorCommand command;

    /** The old border color of the shape. */
    private final Color oldBorderColor = Color.BLACK;
    /** The old fill color of the shape. */
    private final Color oldFillColor = Color.WHITE;
    /** The new border color to be applied to the shape. */
    private final Color newBorderColor = Color.BLUE;
    /** The new fill color to be applied to the shape. */
    private final Color newFillColor = Color.YELLOW;

    /**
     * Sets up the test environment before each test.
     * Initializes mocked objects and the command instance.
     */
    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        shapeMock = mock(ShapeInterface.class);

        // Simulated behavior of the shape
        when(shapeMock.getBorderColor()).thenReturn(oldBorderColor);
        when(shapeMock.getFillColor()).thenReturn(oldFillColor);

        command = new ChangeShapeColorCommand(modelMock, shapeMock, newBorderColor, newFillColor);
    }

    /**
     * Tests the execute method of ChangeShapeColorCommand.
     * Verifies that the shape's color is changed using the model.
     */
    @Test
    void testExecuteChangesShapeColor() {
        command.execute();
        verify(modelMock).changeShapeColor(shapeMock, newBorderColor, newFillColor);
    }

    /**
     * Tests the undo method of ChangeShapeColorCommand.
     * Verifies that the shape's original colors are restored using the model.
     */
    @Test
    void testUndoRestoresOldColors() {
        command.undo();
        verify(modelMock).changeShapeColor(shapeMock, oldBorderColor, oldFillColor);
    }
}
