package com.sad;

import com.sad.models.Model;
import com.sad.models.command.StretchShapeCommand;
import com.sad.models.shapes.ShapeInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class StretchShapeCommandTest {

    private Model modelMock;
    private ShapeInterface shapeMock;
    private StretchShapeCommand command;

    private final double originalWidth = 100.0;
    private final double originalHeight = 50.0;
    private final double scaleFactorX = 1.5;
    private final double scaleFactorY = 2.0;

    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        shapeMock = mock(ShapeInterface.class);

        // Stub iniziale dimensioni originali
        when(shapeMock.getWidth()).thenReturn(originalWidth);
        when(shapeMock.getHeight()).thenReturn(originalHeight);

        command = new StretchShapeCommand(modelMock, shapeMock, scaleFactorX, scaleFactorY);
    }

    @Test
    void testExecuteStretchesShapeWithScaledDimensions() {
        double expectedWidth = originalWidth * scaleFactorX;
        double expectedHeight = originalHeight * scaleFactorY;

        command.execute();

        verify(modelMock).stretchShape(shapeMock, expectedWidth, expectedHeight);
    }

    @Test
    void testUndoRestoresOriginalDimensions() {
        command.execute(); // simula l'esecuzione iniziale
        command.undo();    // esegue l'annullamento

        verify(modelMock).stretchShape(shapeMock, originalWidth, originalHeight);
    }
}
