package com.sad.models.state;

import java.util.ArrayList;
import java.util.List;

import com.sad.Controller;
import com.sad.models.Model;
import com.sad.models.command.CommandInterface;
import com.sad.models.command.DrawPolygonCommand;

import javafx.scene.control.ColorPicker;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Represents the state of the application when the user is drawing polygons.
 * Implements the State design pattern, allowing different behaviors based on the current state.
 * Handles mouse clicks to define polygon vertices and key presses to finalize the polygon.
 */
public class PolygonDrawingState implements StateInterface {

    /** The controller managing the application logic. */
    private Controller controller;
    /** The model representing the application's data. */
    private Model model;
    /** The color picker for selecting the border color of polygons. */
    private ColorPicker borderColorPicker;
    /** The color picker for selecting the fill color of polygons. */
    private ColorPicker fillColorPicker;
    /** The list of current points defining the polygon vertices. */
    private final List<Double> currentPoints = new ArrayList<>();
    /** The list of preview points displayed on the screen. */
    private final List<Circle> previewPoints = new ArrayList<>();

    /**
     * Constructs a PolygonDrawingState.
     * @param controller The controller managing the application logic.
     * @param model The model representing the application's data.
     * @param borderColorPicker The color picker for selecting the border color of polygons.
     * @param fillColorPicker The color picker for selecting the fill color of polygons.
     */
    public PolygonDrawingState(Controller controller, Model model, ColorPicker borderColorPicker, ColorPicker fillColorPicker) {
        this.controller = controller;
        this.model = model;
        this.borderColorPicker = borderColorPicker;
        this.fillColorPicker = fillColorPicker;
    }

    /**
     * Handles mouse click events when the user is defining polygon vertices.
     * Adds the clicked point to the list of vertices and displays a preview point on the screen.
     * @param event The MouseEvent triggered by the user's click.
     */
    @Override
    public void handleOnMouseClick(MouseEvent event) {
        currentPoints.add(event.getX());
        currentPoints.add(event.getY());

        Circle point = new Circle(event.getX(), event.getY(), 2, Color.RED);
        controller.getRoot().getChildren().add(point);
        previewPoints.add(point);
    }

    /**
     * Handles key press events when the user is finalizing the polygon.
     * If the ENTER key is pressed and the polygon has at least 3 vertices, creates the polygon and executes the corresponding command.
     * Clears the preview points and the list of vertices after finalizing the polygon.
     * @param event The KeyEvent triggered by the user's key press.
     */
    @Override
    public void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            int numPoints = currentPoints.size() / 2;

            if (numPoints >= 3) {
                CommandInterface command = new DrawPolygonCommand(model, currentPoints, borderColorPicker, fillColorPicker);
                controller.executeCommand(command);
            }

            currentPoints.clear();
            for (Circle c : previewPoints) {
                controller.getRoot().getChildren().remove(c);
            }
            previewPoints.clear();
        }
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
     * Called when exiting the PolygonDrawingState.
     * Removes all preview points from the screen and clears the list of vertices.
     */
    @Override
    public void onExit() {
        for (Circle c : previewPoints) {
            controller.getRoot().getChildren().remove(c);
        }

        previewPoints.clear();
        currentPoints.clear();
    }
}

