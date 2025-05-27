package com.sad.models.command;

/**
 * Interface for command objects in the Command design pattern.
 * Provides methods to execute and undo a command.
 */
public interface CommandInterface {
    /**
     * Executes the command.
     */
    void execute();

    /**
     * Undoes the command.
     */
    void undo();
}
