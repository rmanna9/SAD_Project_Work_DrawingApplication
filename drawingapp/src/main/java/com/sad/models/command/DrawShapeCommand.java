package com.sad.models.command;

import com.sad.models.Model;
import javafx.scene.paint.Color;

/**
 * Command to draw a new shape in the model.
 * Implements the Command design pattern, allowing execution and undo operations.
 */
public class DrawShapeCommand implements CommandInterface {
    /** The model that acts as the receiver of the command. */
    private final Model receiver;
    /** The X coordinate where the shape will be drawn. */
    private final double x;
    /** The Y coordinate where the shape will be drawn. */
    private final double y;
    /** The border color of the new shape. */
    private final Color border;
    /** The fill color of the new shape. */
    private final Color fill;
    
    /**
     * Constructs a DrawShapeCommand.
     * @param receiver the model that will execute the command
     * @param x the X coordinate for the new shape
     * @param y the Y coordinate for the new shape
     * @param border the border color for the new shape
     * @param fill the fill color for the new shape
     */
    public DrawShapeCommand(Model receiver, double x, double y, Color border, Color fill) {
        this.receiver = receiver;
        this.x = x;
        this.y = y;
        this.border = border;
        this.fill = fill;
    }

    /**
     * Executes the command, creating a new shape in the model.
     */
    @Override
    public void execute() {
        receiver.createShape(this.x, this.y, 0, 0, this.border, this.fill);
    }

    /**
     * Undoes the command, removing the last added shape from the model.
     */
    @Override
    public void undo() {
        int count = receiver.getPane().getChildren().size();
        if (count > 0) {
            receiver.getPane().getChildren().remove(count - 1);
        }
    }

}
