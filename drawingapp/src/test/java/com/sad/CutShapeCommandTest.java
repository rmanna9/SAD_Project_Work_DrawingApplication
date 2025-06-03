/**
 * Unit tests for the CutShapeCommand class.
 * Verifies the behavior of cutting a shape and undoing the action to restore the shape.
 */
package com.sad;

import com.sad.models.Model;
import com.sad.models.command.CutShapeCommand;
import com.sad.models.shapes.ShapeInterface;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Test class for CutShapeCommand.
 */
class CutShapeCommandTest {

    /** Mocked instance of the Model class. */
    private Model modelMock;
    /** Mocked instance of the ShapeInterface. */
    private ShapeInterface shapeMock;
    /** Stubbed instance of a Node (not mocked). */
    private Node nodeStub;
    /** The border color of the shape before cutting. */
    private Color borderColor;
    /** The fill color of the shape before cutting. */
    private Color fillColor;
    /** Instance of the CutShapeCommand being tested. */
    private CutShapeCommand command;

    /**
     * Sets up the test environment before each test.
     * Initializes mocked objects, a real Node instance, and the command instance.
     */
    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        shapeMock = mock(ShapeInterface.class);

        // Use a real Node instance instead of a mock (avoids JavaFX and ByteBuddy issues)
        nodeStub = new Rectangle();

        borderColor = Color.BLACK;
        fillColor = Color.RED;

        // Stub the initial behavior of the shape
        when(shapeMock.getX()).thenReturn(50.0);
        when(shapeMock.getY()).thenReturn(100.0);
        when(shapeMock.getNode()).thenReturn(nodeStub);
        when(shapeMock.getBorderColor()).thenReturn(borderColor);
        when(shapeMock.getFillColor()).thenReturn(fillColor);

        command = new CutShapeCommand(modelMock, shapeMock);
    }

    /**
     * Tests the execute method of CutShapeCommand.
     * Verifies that the cutShape method is called on the model with the correct shape.
     */
    @Test
    void testExecuteCallsCutShape() {
        command.execute();
        verify(modelMock).cutShape(shapeMock);
    }

    /**
     * Tests the undo method of CutShapeCommand.
     * Verifies that the shape's original state is restored and added back to the model.
     */
    @Test
    void testUndoRestoresShapeAndAddsBackToModel() {
        command.execute(); // Simulate the cut operation
        command.undo();    // Restore the original state

        verify(shapeMock).setX(50.0);
        verify(shapeMock).setY(100.0);
        verify(shapeMock).setBorderColor(borderColor);
        verify(shapeMock).setFillColor(fillColor);
        verify(shapeMock).setNode(nodeStub);
        verify(modelMock).addShape(shapeMock);
    }
}
