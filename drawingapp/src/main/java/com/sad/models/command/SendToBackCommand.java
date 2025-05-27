package com.sad.models.command;

import com.sad.models.Model;
import com.sad.models.ShapeInterface;

/**
 * Command to send a specific shape to the back in the model.
 * Implements the Command design pattern, allowing execution and undo operations.
 */
public class SendToBackCommand implements CommandInterface {
    /** The model that acts as the receiver of the command. */
    private Model receiver;
    /** The shape to be sent to the back. */
    private ShapeInterface shape;

    /**
     * Constructs a SendToBackCommand.
     * @param receiver the model that will execute the command
     * @param shape the shape to send to the back
     */
    public SendToBackCommand(Model receiver, ShapeInterface shape) {
        this.receiver = receiver;
        this.shape = shape;
    }

    /**
     * Executes the command, sending the shape to the back.
     */
    @Override
    public void execute() {
        receiver.sendShapeToBack(shape);
    }

    /**
     * Undoes the command, bringing the shape to the front.
     */
    @Override
    public void undo() {
        receiver.bringShapeToFront(shape);
    }

}
