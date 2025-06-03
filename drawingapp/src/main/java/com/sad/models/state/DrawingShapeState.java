package com.sad.models.state;

import com.sad.Controller;
import com.sad.models.Model;
import com.sad.models.command.*;

import javafx.scene.control.ColorPicker;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Represents the state of the application when the user is drawing shapes.
 * Implements the State design pattern, allowing different behaviors based on the current state.
 */
public class DrawingShapeState implements StateInterface {
    /** The controller managing the application logic. */
    private Controller controller;
    /** The model representing the application's data. */
    private Model model;
    /** The color picker for selecting the border color of shapes. */
    private ColorPicker borderColorPicker;
    /** The color picker for selecting the fill color of shapes. */
    private ColorPicker fillColorPicker;

    /**
     * Constructs a DrawingShapeState.
     * @param controller The controller managing the application logic.
     * @param model The model representing the application's data.
     * @param borderColorPicker The color picker for selecting the border color of shapes.
     * @param fillColorPicker The color picker for selecting the fill color of shapes.
     */
    public DrawingShapeState(Controller controller, Model model, ColorPicker borderColorPicker, ColorPicker fillColorPicker) {
        this.controller = controller;
        this.model = model;
        this.borderColorPicker = borderColorPicker;
        this.fillColorPicker = fillColorPicker;
    }

    /**
     * Handles mouse click events when the user is drawing shapes.
     * Creates a new shape at the clicked position and executes the corresponding command.
     * @param event The MouseEvent triggered by the user's click.
     */
    @Override
    public void handleOnMouseClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            double x = event.getX();
            double y = event.getY();

            CommandInterface command = new DrawShapeCommand(model, x, y, borderColorPicker.getValue(), fillColorPicker.getValue());
            controller.executeCommand(command);
        }
        event.consume();
    }

    /**
     * Handles context menu request events.
     * Currently consumes the event without performing any action.
     * @param event The ContextMenuEvent triggered by the user's request.
     */
    @Override
    public void handleOnContextMenuRequest(ContextMenuEvent event) {
        event.consume();
    }

    /**
     * Handles key press events.
     * Currently consumes the event without performing any action.
     * @param event The KeyEvent triggered by the user's key press.
     */
    @Override
    public void onKeyPressed(KeyEvent event) {
        event.consume();
    }

    /**
     * Called when exiting the DrawingShapeState.
     * Currently performs no actions.
     */
    @Override
    public void onExit() {
        // No actions required on exit.
    }
}