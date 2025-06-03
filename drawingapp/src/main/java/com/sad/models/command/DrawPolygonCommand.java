package com.sad.models.command;

import java.util.List;

import com.sad.models.Model;

import javafx.scene.control.ColorPicker;

/**
 * Command to draw a polygon in the model.
 * Implements the Command design pattern, allowing execution and undo operations.
 */
public class DrawPolygonCommand implements CommandInterface {
    /** The model that acts as the receiver of the command. */
    private final Model receiver;
    /** The list of points (x,y coordinates) that define the polygon vertices. */
    private List<Double> currentPoints;
    /** The color pickers for border and fill colors of the polygon. */
    private final ColorPicker borderColorPicker, fillColorPicker;

    /**
     * Constructs a DrawPolygonCommand.
     * @param receiver the model that will execute the command
     * @param currentPoints the list of coordinates defining the polygon vertices
     * @param borderColorPicker the color picker for the polygon border
     * @param fillColorPicker the color picker for the polygon fill
     */
    public DrawPolygonCommand(Model receiver, List<Double> currentPoints, ColorPicker borderColorPicker, ColorPicker fillColorPicker) {
        this.receiver = receiver;
        this.borderColorPicker = borderColorPicker;
        this.fillColorPicker = fillColorPicker;
        this.currentPoints = currentPoints;
    }

    /**
     * Executes the command, creating a new polygon in the model with the specified points and colors.
     */
    @Override
    public void execute() {
        receiver.createPolygon(currentPoints, borderColorPicker.getValue(), fillColorPicker.getValue());
    }

    /**
     * Undoes the command, removing the last added polygon from the model.
     */
    @Override
    public void undo() {
        int count = receiver.getPane().getChildren().size();
        if (count > 0) {
            receiver.getPane().getChildren().remove(count - 1);
        }
    }
}
