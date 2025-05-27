package com.sad;

import com.sad.models.Model;
import com.sad.models.ShapeInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sad.models.command.SendToBackCommand;

import static org.mockito.Mockito.*;

class SendToBackCommandTest {

    private Model modelMock;
    private ShapeInterface shapeMock;
    private SendToBackCommand command;

    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        shapeMock = mock(ShapeInterface.class);
        command = new SendToBackCommand(modelMock, shapeMock);
    }

    @Test
    void testExecuteCallsSendShapeToBack() {
        command.execute();
        verify(modelMock, times(1)).sendShapeToBack(shapeMock);
    }

    @Test
    void testUndoCallsBringShapeToFront() {
        command.undo();
        verify(modelMock, times(1)).bringShapeToFront(shapeMock);
    }
}
