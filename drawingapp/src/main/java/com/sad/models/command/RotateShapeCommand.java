package com.sad.models.command;

import com.sad.models.Model;
import com.sad.models.shapes.ShapeInterface;

/**
 * Command to rotate a shape by a specified angle in the model.
 * Implements the Command design pattern, allowing execution and undo operations.
 * Stores both the new and old rotation angles to support undo functionality.
 */
public class RotateShapeCommand implements CommandInterface {
    /** The model that acts as the receiver of the command. */
    private final Model receiver;
    /** The shape to be rotated. */
    private final ShapeInterface shape;
    /** The new rotation angle to apply. */
    private final double newAngle;
    /** The original rotation angle, stored for undo operations. */
    private final double oldAngle;

    /**
     * Constructs a RotateShapeCommand.
     * @param receiver the model that will execute the command
     * @param shape the shape to be rotated
     * @param angle the new rotation angle to apply
     */
    public RotateShapeCommand(Model receiver, ShapeInterface shape, double angle) {
        this.receiver = receiver;
        this.shape = shape;
        this.newAngle = angle;   
        this.oldAngle = shape.getAngle();
    }

    /**
     * Executes the command, rotating the shape to the new angle.
     */
    @Override
    public void execute() {
        receiver.rotateShape(shape, newAngle);
    }

    /**
     * Undoes the command, restoring the shape's original rotation angle.
     */
    @Override
    public void undo() {
        receiver.rotateShape(shape, oldAngle);
    }
}
