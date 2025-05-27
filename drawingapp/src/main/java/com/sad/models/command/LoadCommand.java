package com.sad.models.command;

import com.sad.models.*;

/**
 * Command to load shapes from a text file and display them on the drawing pane.
 * Implements the CommandInterface.
 */
public class LoadCommand implements CommandInterface {
    /** The model that acts as the receiver of the command. */
    private final Model receiver;

    /**
     * Constructs a LoadCommand.
     * @param receiver the model that will execute the load operation
     */
    public LoadCommand(Model receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the load operation.
     * Opens a file chooser, reads shapes from the selected file, deserializes them,
     * adds them to the shapes list, and draws them on the pane.
     */
    @Override
    public void execute() {
        receiver.load();
    }

    /**
     * Undoes the load command.
     * No revert action is implemented for the load command.
     */
    @Override
    public void undo() {
        // No revert action for load command
    }
    
}
