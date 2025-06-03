package com.sad.models.command;

import com.sad.models.Model;
import com.sad.models.shapes.ConcreteText;

/**
 * Command to edit the content of a text shape in the model.
 * Implements the Command design pattern, allowing execution and undo operations.
 * Stores both the new and old text content to support undo functionality.
 */
public class EditTextCommand implements CommandInterface {
    /** The model that acts as the receiver of the command. */
    private Model receiver;
    /** The text shape whose content will be modified. */
    private final ConcreteText textShape;
    /** The original text content, stored for undo operations. */
    private final String oldText;
    /** The new text content to apply. */
    private final String newText;

    /**
     * Constructs an EditTextCommand.
     * @param model the model that will execute the command
     * @param textShape the text shape to be modified
     * @param oldText the original text content
     * @param newText the new text content to apply
     */
    public EditTextCommand(Model model, ConcreteText textShape, String oldText, String newText) {
        this.receiver = model;
        this.textShape = textShape;
        this.oldText = oldText;
        this.newText = newText;
    }

    /**
     * Executes the command, changing the text shape's content to the new text.
     */
    @Override
    public void execute() {
        receiver.editText(textShape, newText);
    }

    /**
     * Undoes the command, restoring the text shape's original content.
     */
    @Override
    public void undo() {
        receiver.editText(textShape, oldText);
    }
}
