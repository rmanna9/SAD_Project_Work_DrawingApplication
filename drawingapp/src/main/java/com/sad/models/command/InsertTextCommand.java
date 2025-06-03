package com.sad.models.command;

import com.sad.models.Model;
import com.sad.models.shapes.ShapeInterface;

/**
 * Command to insert a text shape into the model.
 * Implements the Command design pattern, allowing execution and undo operations.
 */
public class InsertTextCommand implements CommandInterface {
    /** The model that acts as the receiver of the command. */
    private Model receiver;
    /** The new text shape to be inserted. */
    private ShapeInterface newText;

    /**
     * Constructs an InsertTextCommand.
     * @param receiver the model that will execute the command
     * @param newText the text shape to be inserted
     */
    public InsertTextCommand(Model receiver, ShapeInterface newText) {
        this.receiver = receiver;
        this.newText = newText;
    }

    /**
     * Executes the command, adding the text shape to the model.
     */
    @Override
    public void execute() {
        receiver.addShape(newText);
    }

    /**
     * Undoes the command, removing the inserted text shape from the model.
     */
    @Override
    public void undo() {
        if (newText != null) {
            receiver.deleteShape(newText);
        }
    }
}
