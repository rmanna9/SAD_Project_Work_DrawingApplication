package com.sad.models.command;

import com.sad.models.Model;
import com.sad.models.ShapeInterface;

/**
 * Command to bring a specific shape to the front in the model.
 * Implements the Command design pattern, allowing execution and undo operations.
 */
public class BringToFrontCommand implements CommandInterface {
    /** The model that acts as the receiver of the command. */
    private Model receiver;
    /** The shape to be brought to the front. */
    private ShapeInterface shape;

    /**
     * Constructs a BringToFrontCommand.
     * @param receiver the model that will execute the command
     * @param shape the shape to bring to the front
     */
    public BringToFrontCommand(Model receiver, ShapeInterface shape) {
        this.receiver = receiver;
        this.shape = shape;
    }

    /**
     * Executes the command, bringing the shape to the front.
     */
    @Override
    public void execute() {
        receiver.bringShapeToFront(shape);
    }

    /**
     * Undoes the command, sending the shape to the back.
     */
    @Override
    public void undo() {
        receiver.sendShapeToBack(shape);
    }
}
