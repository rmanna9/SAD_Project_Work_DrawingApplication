package com.sad;

import com.sad.models.Model;
import com.sad.models.command.MirrorXShapeCommand;
import com.sad.models.shapes.ShapeInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class MirrorXShapeCommandTest {

    private Model modelMock;
    private ShapeInterface shapeMock;
    private MirrorXShapeCommand command;

    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        shapeMock = mock(ShapeInterface.class);

        command = new MirrorXShapeCommand(modelMock, shapeMock);
    }

    @Test
    void testExecuteMirrorsShapeOnX() {
        command.execute();
        verify(modelMock).mirrorXShape(shapeMock);
    }

    @Test
    void testUndoAlsoMirrorsShapeOnX() {
        command.undo();
        verify(modelMock).mirrorXShape(shapeMock);
    }
}
