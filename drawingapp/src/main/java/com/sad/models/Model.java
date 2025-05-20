package com.sad.models;

import java.util.ArrayList;
import java.util.List;

import com.sad.models.command.CommandInterface;

import javafx.scene.paint.Color;

/**
 * Model class for the Drawing Application.
 * Manages the current shape factory, the list of shapes, and command execution.
 */
public class Model {
    private ShapeFactory currentFactory;
    private final List<ShapeInterface> shapes;
    private CommandInterface currentCommand;

    /**
     * Constructs a new Model instance.
     * Initializes the shapes list and sets the current factory and command to null.
     */
    public Model() {
        this.shapes = new ArrayList<>();
        this.currentFactory = null;
        this.currentCommand = null;
    }

    /**
     * Creates a new shape using the current factory and adds it to the shapes list.
     *
     * @param x The x-coordinate for the shape.
     * @param y The y-coordinate for the shape.
     * @param width The width of the shape.
     * @param height The height of the shape.
     * @param border The border color of the shape.
     * @param fill The fill color of the shape.
     * @return The created ShapeInterface object, or null if no factory is set.
     */
    public ShapeInterface createShape(double x, double y, double width, double height, Color border, Color fill) {
        if (currentFactory != null) {
            ShapeInterface shape = currentFactory.createShape(x, y, width, height, border, fill);
            shapes.add(shape);
            return shape;
        }
        return null;
    }

    /**
     * Sets the current shape factory.
     * @param factory The ShapeFactory to set.
     */
    public void setCurrentFactory(ShapeFactory factory) {
        this.currentFactory = factory;
    }

    /**
     * Gets the current shape factory.
     * @return The current ShapeFactory.
     */
    public ShapeFactory getCurrentFactory() {
        return currentFactory;
    }

    /**
     * Gets the list of shapes.
     * @return The list of ShapeInterface objects.
     */
    public List<ShapeInterface> getShapes() {
        return shapes;
    }

    /**
     * Sets the current command.
     * @param command The CommandInterface to set.
     */
    public void setCommand(CommandInterface command) {
        this.currentCommand = command;
    }

    /**
     * Gets the current command.
     * @return The current CommandInterface.
     */
    public CommandInterface getCommand() {
        return currentCommand;
    }

    /**
     * Executes the current command if set.
     * @return The executed CommandInterface, or null if no command is set.
     */
    public CommandInterface executeCommand(){
        if (currentCommand != null) {
            currentCommand.execute();
            return currentCommand;
        }
        return null;
    }
}
