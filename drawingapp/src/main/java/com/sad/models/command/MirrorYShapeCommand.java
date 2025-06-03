package com.sad.models.command;

import com.sad.models.Model;
import com.sad.models.shapes.ShapeInterface;

/**
 * Command to mirror a shape vertically (along Y-axis) in the model.
 * Implements the Command design pattern, allowing execution and undo operations.
 */
public class MirrorYShapeCommand implements CommandInterface {
    /** The model that acts as the receiver of the command. */
    private final Model receiver;
    /** The shape to be mirrored. */
    private final ShapeInterface shape;
    
    /**
     * Constructs a MirrorYShapeCommand.
     * @param receiver the model that will execute the command
     * @param shape the shape to be mirrored vertically
     */
    public MirrorYShapeCommand(Model receiver, ShapeInterface shape){
        this.receiver=receiver;
        this.shape=shape;
    }

    /**
     * Executes the command, mirroring the shape vertically.
     */
    @Override
    public void execute() {
       receiver.mirrorYShape(shape);
    }

    /**
     * Undoes the command by mirroring the shape vertically again,
     * effectively restoring its original orientation.
     */
    @Override
    public void undo() {
       receiver.mirrorYShape(shape);
    }
}
