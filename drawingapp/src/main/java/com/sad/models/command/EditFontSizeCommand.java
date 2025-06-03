package com.sad.models.command;

import com.sad.models.Model;
import com.sad.models.shapes.ConcreteText;

/**
 * Command to edit the font size of a text shape in the model.
 * Implements the Command design pattern, allowing execution and undo operations.
 * Stores both the new and old font sizes to support undo functionality.
 */
public class EditFontSizeCommand implements CommandInterface {
    /** The model that acts as the receiver of the command. */
    private Model receiver;
    /** The text shape whose font size will be modified. */
    private final ConcreteText textShape;
    /** The new font size to apply. */
    private final double newSize;
    /** The original font size, stored for undo operations. */
    private final double oldSize;

    /**
     * Constructs an EditFontSizeCommand.
     * @param receiver the model that will execute the command
     * @param textShape the text shape to be modified
     * @param newSize the new font size to apply
     */
    public EditFontSizeCommand(Model receiver, ConcreteText textShape, double newSize) {
        this.receiver = receiver;
        this.textShape = textShape;
        this.newSize = newSize;
        this.oldSize = textShape.getFontSize();
    }

    /**
     * Executes the command, changing the text shape's font size to the new size.
     */
    @Override
    public void execute() {
        receiver.editFontSize(textShape, newSize);
    }

    /**
     * Undoes the command, restoring the text shape's original font size.
     */
    @Override
    public void undo() {
        receiver.editFontSize(textShape, oldSize);
    }
}
