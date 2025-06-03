package com.sad.models.command;

import com.sad.models.Model;
import com.sad.models.shapes.ShapeInterface;

import javafx.scene.Node;

/**
 * Command to cut a specific shape from the model.
 * Implements the Command design pattern, allowing execution and undo operations.
 * Stores the original state of the shape to allow restoration on undo.
 */
public class CutShapeCommand implements CommandInterface {
    /** The model that acts as the receiver of the command. */
    private final Model receiver;
    /** The shape to be cut. */
    private final ShapeInterface shape;
    
    /** The original X position of the shape. */
    private final double originalX;
    /** The original Y position of the shape. */
    private final double originalY;
    /** The original graphical node of the shape. */
    private final Node node;
    /** The original border color of the shape. */
    private final javafx.scene.paint.Color originalBorder;
    /** The original fill color of the shape. */
    private final javafx.scene.paint.Color originalFill;

    /**
     * Constructs a CutShapeCommand.
     * Stores the original state of the shape for undo functionality.
     * @param receiver the model that will execute the command
     * @param shape the shape to be cut
     */
    public CutShapeCommand(Model receiver, ShapeInterface shape) {
        this.receiver = receiver;
        this.shape = shape;
        this.originalX = shape.getX();
        this.originalY = shape.getY();
        this.node = shape.getNode();
        this.originalBorder = shape.getBorderColor();
        this.originalFill = shape.getFillColor();
    }

    /**
     * Executes the command, cutting the shape from the model.
     */
    @Override
    public void execute() {
        receiver.cutShape(shape);
    }

    /**
     * Undoes the command, restoring the shape to its original state and position.
     */
    @Override
    public void undo() {
        shape.setX(originalX);
        shape.setY(originalY);
        shape.setBorderColor(originalBorder);
        shape.setFillColor(originalFill);
        shape.setNode(node);

        receiver.addShape(shape);
    }
}
