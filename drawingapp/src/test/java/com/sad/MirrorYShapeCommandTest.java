package com.sad;

import com.sad.models.Model;
import com.sad.models.command.MirrorYShapeCommand;
import com.sad.models.shapes.ShapeInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class MirrorYShapeCommandTest {

    private Model modelMock;
    private ShapeInterface shapeMock;
    private MirrorYShapeCommand command;

    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        shapeMock = mock(ShapeInterface.class);

        command = new MirrorYShapeCommand(modelMock, shapeMock);
    }

    @Test
    void testExecuteMirrorsShapeOnY() {
        command.execute();
        verify(modelMock).mirrorYShape(shapeMock);
    }

    @Test
    void testUndoAlsoMirrorsShapeOnY() {
        command.undo();
        verify(modelMock).mirrorYShape(shapeMock);
    }
}
