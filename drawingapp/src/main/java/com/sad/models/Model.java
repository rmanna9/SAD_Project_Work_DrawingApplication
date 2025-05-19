package com.sad.models;

import java.util.ArrayList;
import java.util.List;

import com.sad.models.command.CommandInterface;

import javafx.scene.paint.Color;


public class Model {
    private ShapeFactory currentFactory;
    private final List<ShapeInterface> shapes;
    private CommandInterface currentCommand;

    public Model() {
        this.shapes = new ArrayList<>();
        this.currentFactory = null;
        this.currentCommand = null;
    }

    public ShapeInterface createShape(double x, double y, double width, double height, Color border, Color fill) {
        if (currentFactory != null) {
            ShapeInterface shape = currentFactory.createShape(x, y, width, height, border, fill);
            shapes.add(shape);
            return shape;
        }
        return null;
    }

    public void setCurrentFactory(ShapeFactory factory) {
        this.currentFactory = factory;
    }

    public ShapeFactory getCurrentFactory() {
        return currentFactory;
    }

    public List<ShapeInterface> getShapes() {
        return shapes;
    }

    public void setCommand(CommandInterface command) {
        this.currentCommand = command;
    }

    public CommandInterface getCommand() {
        return currentCommand;
    }

    public CommandInterface executeCommand(){
        if (currentCommand != null) {
            currentCommand.execute();
            return currentCommand;
        }
        return null;
    }
}
