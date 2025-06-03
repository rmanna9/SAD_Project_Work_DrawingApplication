package com.sad.models.state;

import com.sad.Controller;
import com.sad.models.Model;
import com.sad.models.shapes.ConcreteText;

import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Represents the state of the application when the user is inserting text.
 * Implements the State design pattern, allowing different behaviors based on the current state.
 * Handles mouse clicks to insert text and manages text input fields.
 */
public class TextInsertState implements StateInterface {

    /** The controller managing the application logic. */
    private Controller controller;
    /** The currently inserted text shape. */
    private ConcreteText currentText;

    /**
     * Constructs a TextInsertState.
     * @param controller The controller managing the application logic.
     * @param model The model representing the application's data (unused in this state).
     */
    public TextInsertState(Controller controller, Model model) {
        this.controller = controller;        
    }

    /**
     * Handles mouse click events when the user is inserting text.
     * Creates a new text shape at the clicked position and enables the text input field for editing.
     * @param event The MouseEvent triggered by the user's click.
     */
    @Override
    public void handleOnMouseClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            double x = event.getX();
            double y = event.getY();

            currentText = new ConcreteText(
                "",
                x,
                y,
                controller.getBordColorPicker().getValue(),
                controller.getFillColorPicker().getValue(),
                controller.getFontSizeMenuButton().getValue()
            );
            controller.getRoot().getChildren().add(currentText.draw());

            controller.getFontSizeMenuButton().setDisable(true);
            controller.getTextInputField().setDisable(false);
            controller.getTextInputField().setText("");
            controller.getTextInputField().requestFocus();
        }
        event.consume();
    }

    /**
     * Handles key press events.
     * Currently does not perform any actions.
     * @param event The KeyEvent triggered by the user's key press.
     */
    @Override
    public void onKeyPressed(KeyEvent event) {
        // No actions required for key press in this state.
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
     * Called when exiting the TextInsertState.
     * Removes the currently inserted text shape from the screen and resets the text input field.
     */
    @Override
    public void onExit() {
        if (currentText != null) {
            controller.getRoot().getChildren().remove(currentText.getNode());
            currentText = null;
        }

        controller.getFontSizeMenuButton().setDisable(true);
        controller.getTextInputField().setDisable(true);
        controller.getTextInputField().clear();
    }

    /**
     * Gets the currently inserted text shape.
     * @return The currently inserted ConcreteText, or null if no text is being inserted.
     */
    public ConcreteText getCurrentText() {
        return currentText;
    }

    /**
     * Clears the reference to the currently inserted text shape.
     * Sets the currentText field to null.
     */
    public void clearCurrentText() {
        currentText = null;
    }
}
