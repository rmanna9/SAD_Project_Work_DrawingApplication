package com.sad.models.command;

import com.sad.models.Model;
import com.sad.models.ShapeInterface;

/**
 * Command to paste a shape at a specific position in the model.
 * Implements the Command design pattern, allowing execution and undo operations.
 */
public class PasteShapeCommand implements CommandInterface {
    /** The model that acts as the receiver of the command. */
    private Model receiver;
    /** The X coordinate where the shape will be pasted. */
    private double x, y;
    /** The newly pasted shape, used for undo. */
    private ShapeInterface newShape;

    /**
     * Constructs a PasteShapeCommand.
     * @param receiver the model that will execute the command
     * @param x the X coordinate where the shape will be pasted
     * @param y the Y coordinate where the shape will be pasted
     */
    public PasteShapeCommand(Model receiver, double x, double y) {
        this.receiver = receiver;
        this.x = x;
        this.y = y;
    }

    /**
     * Executes the command, pasting the shape at the specified coordinates.
     */
    @Override
    public void execute() {
        newShape = receiver.pasteShape(x, y);       
    }

    /**
     * Undoes the command, removing the pasted shape from the model.
     */
    @Override
    public void undo() {
        if (newShape != null) {
            receiver.deleteShape(newShape);
        }
    }
}

