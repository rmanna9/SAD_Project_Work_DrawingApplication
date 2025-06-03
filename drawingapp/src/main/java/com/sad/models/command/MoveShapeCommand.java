package com.sad.models.command;

import com.sad.models.Model;
import com.sad.models.shapes.ShapeInterface;

/**
 * Command to move a specific shape within the model.
 * Implements the Command design pattern, allowing execution and undo operations.
 * Stores the initial and final coordinates to support undo functionality.
 */
public class MoveShapeCommand implements CommandInterface {
    /** The model that acts as the receiver of the command. */
    private Model receiver;
    /** The shape to be moved. */
    private ShapeInterface shape;
    /** The initial coordinates of the shape (used for undo). */
    private double[] initialCoords;
    /** The final coordinates of the shape (used for execute). */
    private double[] finalCoords;
    
    /**
     * Constructs a MoveShapeCommand.
     * @param receiver the model that will execute the command
     * @param shape the shape to be moved
     * @param initialCoords the initial coordinates of the shape
     * @param finalCoords the final coordinates of the shape
     */
    public MoveShapeCommand(Model receiver, ShapeInterface shape, double[] initialCoords, double[] finalCoords) {
        this.receiver = receiver;
        this.shape = shape;
        this.initialCoords = initialCoords;
        this.finalCoords = finalCoords;
    }

    /**
     * Executes the command, moving the shape to the final coordinates.
     */
    @Override
    public void execute() {
        if(shape == null) { return; }
        receiver.moveShape(shape, finalCoords);
    }

    /**
     * Undoes the command, moving the shape back to the initial coordinates.
     */
    @Override
    public void undo() {
        if(shape == null) { return; }
        receiver.moveShape(shape, initialCoords);
    }
}
