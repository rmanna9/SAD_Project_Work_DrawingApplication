package com.sad.models.command;

import com.sad.models.Model;
import com.sad.models.shapes.ShapeInterface;

/**
 * Command to stretch a shape in both X and Y dimensions in the model.
 * Implements the Command design pattern, allowing execution and undo operations.
 * Stores both the scale factors and original dimensions to support undo functionality.
 */
public class StretchShapeCommand implements CommandInterface {
    /** The model that acts as the receiver of the command. */
    private Model receiver;
    /** The shape to be stretched. */
    private final ShapeInterface shape;
    /** The scaling factor for the X dimension. */
    private final double scaleFactorX;
    /** The scaling factor for the Y dimension. */
    private final double scaleFactorY;
    /** The original width, stored for undo operations. */
    private final double originalWidth;
    /** The original height, stored for undo operations. */
    private final double originalHeight;

    /**
     * Constructs a StretchShapeCommand.
     * @param receiver the model that will execute the command
     * @param shape the shape to be stretched
     * @param scaleFactorX the scaling factor for the X dimension
     * @param scaleFactorY the scaling factor for the Y dimension
     */
    public StretchShapeCommand(Model receiver, ShapeInterface shape, double scaleFactorX, double scaleFactorY){
        this.receiver = receiver;
        this.shape = shape;
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
        this.originalWidth = shape.getWidth();
        this.originalHeight = shape.getHeight();
    }

    /**
     * Executes the command, stretching the shape by the specified scale factors.
     */
    @Override
    public void execute() {
        receiver.stretchShape(shape, originalWidth * scaleFactorX, originalHeight * scaleFactorY);
    }

    /**
     * Undoes the command, restoring the shape's original dimensions.
     */
    @Override
    public void undo() {
        receiver.stretchShape(shape, originalWidth, originalHeight);
    }
}

