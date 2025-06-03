package com.sad.models.state;

import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Interface representing the state of the application.
 * Implements the State design pattern, allowing different behaviors based on the current state.
 * Defines methods for handling user interactions and state transitions.
 */
public interface StateInterface {

    /**
     * Handles mouse click events.
     * Defines the behavior when the user clicks within the application.
     * @param event The MouseEvent triggered by the user's click.
     */
    void handleOnMouseClick(MouseEvent event);

    /**
     * Handles context menu request events.
     * Defines the behavior when the user requests a context menu.
     * @param event The ContextMenuEvent triggered by the user's request.
     */
    void handleOnContextMenuRequest(ContextMenuEvent event);

    /**
     * Handles key press events.
     * Defines the behavior when the user presses a key.
     * @param event The KeyEvent triggered by the user's key press.
     */
    void onKeyPressed(KeyEvent event);

    /**
     * Called when exiting the current state.
     * Defines cleanup or transition logic when the state is exited.
     */
    void onExit();
}
