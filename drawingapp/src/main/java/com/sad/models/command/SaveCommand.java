package com.sad.models.command;

import com.sad.models.Model;

/**
 * Command to save shapes to a text file from the drawing pane.
 * Implements the CommandInterface.
 */
public class SaveCommand implements CommandInterface {
    /** The model that acts as the receiver of the command. */
    private final Model receiver;

    /**
     * Constructs a SaveCommand.
     * @param receiver the model that will execute the save operation
     */
    public SaveCommand(Model receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the save operation.
     * Opens a file chooser, serializes all shapes, and writes them to the selected file.
     */
    @Override
    public void execute() {
        receiver.save();
    }

    /**
     * Undoes the save command.
     * No revert action is implemented for the save command.
     */
    @Override
    public void undo() {
        // No revert action for save command
    }
}
