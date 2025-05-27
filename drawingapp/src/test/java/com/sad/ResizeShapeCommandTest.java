package com.sad;

import com.sad.models.Model;
import com.sad.models.ShapeInterface;
import com.sad.models.command.ResizeShapeCommand;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ResizeShapeCommandTest {

    private Model modelMock;
    private ShapeInterface shapeMock;
    private ResizeShapeCommand command;

    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        shapeMock = mock(ShapeInterface.class);

        when(shapeMock.getWidth()).thenReturn(100.0);
        when(shapeMock.getHeight()).thenReturn(50.0);

        command = new ResizeShapeCommand(modelMock, shapeMock, 2.0);
    }

    @Test
    void testExecuteCallsResizeWithScaledValues() {
        command.execute();
        verify(modelMock).resizeShape(shapeMock, 200.0, 100.0);
    }

    @Test
    void testUndoCallsResizeWithOriginalValues() {
        command.execute();  // optional, to simulate a real flow
        command.undo();
        verify(modelMock).resizeShape(shapeMock, 100.0, 50.0);
    }
}
