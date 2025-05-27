package com.sad.models.command;

import com.sad.models.Model;
import com.sad.models.ShapeInterface;

/**
 * Command to resize a specific shape in the model.
 * Implements the Command design pattern, allowing execution and undo operations.
 * Stores the original dimensions to support undo functionality.
 */
public class ResizeShapeCommand implements CommandInterface {
    /** The model that acts as the receiver of the command. */
    private Model receiver;
    /** The shape to be resized. */
    private final ShapeInterface shape;
    /** The scale factor to apply to the shape. */
    private final double scaleFactor;
    /** The original width of the shape, used for undo. */
    private final double originalWidth;
    /** The original height of the shape, used for undo. */
    private final double originalHeight;

    /**
     * Constructs a ResizeShapeCommand.
     * Stores the original dimensions for undo functionality.
     * @param receiver the model that will execute the command
     * @param shape the shape to be resized
     * @param scaleFactor the scale factor to apply
     */
    public ResizeShapeCommand(Model receiver, ShapeInterface shape, double scaleFactor) {
        this.receiver = receiver;
        this.shape = shape;
        this.scaleFactor = scaleFactor;
        this.originalWidth = shape.getWidth();
        this.originalHeight = shape.getHeight();
    }

    /**
     * Executes the command, resizing the shape by the scale factor.
     */
    @Override
    public void execute() {
        receiver.resizeShape(shape, originalWidth * scaleFactor, originalHeight * scaleFactor);
    }

    /**
     * Undoes the command, restoring the shape to its original dimensions.
     */
    @Override
    public void undo() {
        receiver.resizeShape(shape, originalWidth, originalHeight);
    }

}
