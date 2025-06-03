package com.sad.models.state;

import com.sad.Controller;
import com.sad.models.Model;
import com.sad.models.command.CommandInterface;
import com.sad.models.command.MoveShapeCommand;
import com.sad.models.shapes.ConcreteText;
import com.sad.models.shapes.ShapeInterface;

import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

/**
 * Represents the state of the application when the user is selecting shapes.
 * Implements the State design pattern, allowing different behaviors based on the current state.
 * Handles mouse clicks, context menu requests, and enables shape movement.
 */
public class SelectingState implements StateInterface {

    /** The controller managing the application logic. */
    private Controller controller;
    /** The model representing the application's data. */
    private Model model;

    /**
     * Constructs a SelectingState.
     * @param controller The controller managing the application logic.
     * @param model The model representing the application's data.
     */
    public SelectingState(Controller controller, Model model) {
        this.controller = controller;
        this.model = model;
    }

    /**
     * Handles mouse click events when the user is selecting shapes.
     * Selects the clicked shape, updates the UI, and enables movement for the selected shape.
     * If no shape is clicked, deselects the current shape.
     * @param event The MouseEvent triggered by the user's click.
     */
    @Override
    public void handleOnMouseClick(MouseEvent event) {
        controller.getContextMenu().hide();
        Object target = event.getTarget();

        if (target instanceof Shape) {
            Node clickedNode = (Node) target;
            ShapeInterface clickedShape = model.getShapeFromNode(clickedNode);

            if (clickedShape.getNode() != model.getSelectedShape()) {
                controller.getResizeTexField().setDisable(true);
                controller.getResizeTexField().setText("");
                model.deselectShape();
                model.setSelectedShape(clickedShape.getNode());
                model.setSelectedShapeStyle("-fx-effect: dropshadow(three-pass-box, #00bfff, 10, 0, 0, 0);");

                controller.getBordColorPicker().setValue(clickedShape.getBorderColor());
                controller.getFillColorPicker().setValue(clickedShape.getFillColor());
            }
            controller.getResizeTexField().setDisable(false);

            if (clickedShape instanceof ConcreteText) {
                ConcreteText textShape = (ConcreteText) clickedShape;

                controller.getTextInputField().setText(textShape.getContent());
                controller.getTextInputField().setDisable(false);

                int currentFontSize = (int) textShape.getFontSize();
                controller.getFontSizeMenuButton().setValue(currentFontSize);
                controller.getFontSizeMenuButton().setDisable(false);

            } else {
                controller.getTextInputField().setDisable(true);
                controller.getFontSizeMenuButton().setDisable(true);
            }

            enableMoveShape(clickedShape);

        } else {
            controller.getTextInputField().setDisable(true);
            controller.getFontSizeMenuButton().setDisable(true);
            model.deselectShape();
        }
    }

    /**
     * Handles context menu request events.
     * Displays the context menu at the mouse position and updates menu item states based on the current selection.
     * @param event The ContextMenuEvent triggered by the user's request.
     */
    @Override
    public void handleOnContextMenuRequest(ContextMenuEvent event) {
        controller.setMouseX(event.getX());
        controller.setMouseY(event.getY());

        Object target = event.getTarget();
        boolean isTargetSelectedShape = target instanceof Shape && target == model.getSelectedShape();

        for (MenuItem item : controller.getContextMenu().getItems()) {
            switch (item.getText()) {
                case "Delete":
                case "Copy":
                case "Cut":
                    item.setDisable(!isTargetSelectedShape);
                    break;
                case "Paste":
                    item.setDisable(model.getClipBoardShape() == null);
                    break;
                case "Bring to Front":
                    item.setDisable(model.isOnTheFront() || !isTargetSelectedShape);
                    break;
                case "Send to Back":
                    item.setDisable(model.isOnTheBack() || !isTargetSelectedShape);
                    break;
            }
        }

        controller.getContextMenu().show(controller.getRoot(), event.getScreenX(), event.getScreenY());
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
     * Called when exiting the SelectingState.
     * Currently does not perform any actions.
     */
    @Override
    public void onExit() {
        // No actions required on exit.
    }

    /**
     * Enables movement for the selected shape.
     * Handles mouse press, drag, and release events to update the shape's position and execute a move command.
     * @param shape The shape to enable movement for.
     */
    private void enableMoveShape(ShapeInterface shape) {
        final double[] delta = new double[2];
        final double[] initialPosition = new double[2];
        final double[] nodeInitialPosition = new double[2];

        Node node = shape.getNode();
        Node rotNode = controller.getRoot();

        node.setOnMousePressed(event -> {
            initialPosition[0] = shape.getX();
            initialPosition[1] = shape.getY();

            nodeInitialPosition[0] = node.getLayoutX();
            nodeInitialPosition[1] = node.getLayoutY();

            javafx.geometry.Point2D localPoint = rotNode.sceneToLocal(event.getSceneX(), event.getSceneY());
            delta[0] = localPoint.getX();
            delta[1] = localPoint.getY();

            event.consume();
        });

        node.setOnMouseDragged(event -> {
            javafx.geometry.Point2D localPoint = rotNode.sceneToLocal(event.getSceneX(), event.getSceneY());
            double offsetX = localPoint.getX() - delta[0];
            double offsetY = localPoint.getY() - delta[1];

            double newX = initialPosition[0] + offsetX;
            double newY = initialPosition[1] + offsetY;

            shape.moveTo(newX, newY);

            event.consume();
        });

        node.setOnMouseReleased(event -> {
            javafx.geometry.Point2D localPoint = rotNode.sceneToLocal(event.getSceneX(), event.getSceneY());
            double offsetX = localPoint.getX() - delta[0];
            double offsetY = localPoint.getY() - delta[1];

            double finalX = initialPosition[0] + offsetX;
            double finalY = initialPosition[1] + offsetY;

            double[] initial = {initialPosition[0], initialPosition[1]};
            double[] finalCoords = {finalX, finalY};

            if (initial[0] != finalCoords[0] || initial[1] != finalCoords[1]) {
                CommandInterface command = new MoveShapeCommand(model, shape, initial, finalCoords);
                controller.executeCommand(command);
            }

            event.consume();
        });
    }
}
