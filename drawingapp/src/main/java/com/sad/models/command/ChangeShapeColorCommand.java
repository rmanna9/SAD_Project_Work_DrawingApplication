package com.sad.models.command;

import com.sad.models.Model;
import com.sad.models.ShapeInterface;

import javafx.scene.paint.Color;

/**
 * Command to change the border and fill color of a shape.
 * Implements the Command design pattern, allowing execution and undo operations.
 */
public class ChangeShapeColorCommand implements CommandInterface {
    /** The model that acts as the receiver of the command. */
    private final Model receiver;
    /** The previous border color of the shape, used for undo. */
    private final Color oldBorderColor;
    /** The previous fill color of the shape, used for undo. */
    private final Color oldFillColor;
    /** The new border color to set. */
    private final Color newBorderColor;
    /** The new fill color to set. */
    private final Color newFillColor;
    /** The shape whose color will be changed. */
    private ShapeInterface shape;

    /**
     * Constructs a ChangeShapeColorCommand.
     * @param receiver the model that will execute the command
     * @param shape the shape whose color will be changed
     * @param newBorderColor the new border color to set
     * @param newFillColor the new fill color to set
     */
    public ChangeShapeColorCommand(Model receiver, ShapeInterface shape, Color newBorderColor, Color newFillColor) {
        this.receiver = receiver;
        this.shape = shape;
        this.oldBorderColor = shape.getBorderColor();
        this.oldFillColor = shape.getFillColor();
        this.newBorderColor = newBorderColor;
        this.newFillColor = newFillColor;
    }

    /**
     * Executes the command, changing the shape's color to the new values.
     */
    @Override
    public void execute() {
        receiver.changeShapeColor(shape, newBorderColor, newFillColor);
    }

    /**
     * Undoes the command, restoring the shape's previous colors.
     */
    @Override
    public void undo() {
        receiver.changeShapeColor(shape, oldBorderColor, oldFillColor);
    }

}
