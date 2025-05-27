package com.sad;

import com.sad.models.Model;
import com.sad.models.ShapeInterface;
import com.sad.models.command.BringToFrontCommand;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class BringToFrontCommandTest {

    private Model modelMock;
    private ShapeInterface shapeMock;
    private BringToFrontCommand command;

    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        shapeMock = mock(ShapeInterface.class);
        command = new BringToFrontCommand(modelMock, shapeMock);
    }

    @Test
    void testExecuteBringsShapeToFront() {
        command.execute();
        verify(modelMock).bringShapeToFront(shapeMock);
    }

    @Test
    void testUndoSendsShapeToBack() {
        command.undo();
        verify(modelMock).sendShapeToBack(shapeMock);
    }
}
