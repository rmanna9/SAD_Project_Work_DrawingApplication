package com.sad.models.command;

import com.sad.models.Model;
import com.sad.models.ShapeInterface;

/**
 * Command to copy a specific shape in the model.
 * Implements the Command design pattern, allowing execution and (optionally) undo operations.
 */
public class CopyShapeCommand implements CommandInterface {
    /** The model that acts as the receiver of the command. */
    private final Model receiver;
    /** The shape to be copied. */
    private final ShapeInterface shape;

    /**
     * Constructs a CopyShapeCommand.
     * @param receiver the model that will execute the command
     * @param shape the shape to be copied
     */
    public CopyShapeCommand(Model receiver, ShapeInterface shape) {
        this.receiver = receiver;
        this.shape = shape;
    }

    /**
     * Executes the command, copying the shape.
     */
    @Override
    public void execute() {
        receiver.copyShape(shape);
    }

    /**
     * Undoes the command. (Not implemented for copy operation.)
     */
    @Override
    public void undo() {
        
    }

}
