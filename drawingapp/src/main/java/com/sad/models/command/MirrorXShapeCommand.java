package com.sad.models.command;

import com.sad.models.Model;
import com.sad.models.shapes.ShapeInterface;

/**
 * Command to mirror a shape horizontally (along X-axis) in the model.
 * Implements the Command design pattern, allowing execution and undo operations.
 */
public class MirrorXShapeCommand implements CommandInterface {
    /** The model that acts as the receiver of the command. */
    private final Model receiver;
    /** The shape to be mirrored. */
    private final ShapeInterface shape;

    
    /**
     * Constructs a MirrorXShapeCommand.
     * @param receiver the model that will execute the command
     * @param shape the shape to be mirrored horizontally
     */
    public MirrorXShapeCommand(Model receiver, ShapeInterface shape){
        this.receiver=receiver;
        this.shape=shape;
    }

    /**
     * Executes the command, mirroring the shape horizontally.
     */
    @Override
    public void execute() {
       receiver.mirrorXShape(shape);
    }

    /**
     * Undoes the command by mirroring the shape horizontally again,
     * effectively restoring its original orientation.
     */
    @Override
    public void undo() {
       receiver.mirrorXShape(shape);
    }
}
