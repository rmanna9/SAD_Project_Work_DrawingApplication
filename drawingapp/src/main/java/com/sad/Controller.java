package com.sad;

import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.layout.Pane;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;

import java.util.Stack;
import java.util.function.UnaryOperator;

import com.sad.models.*;
import com.sad.models.command.*;

/**
 * Controller class for the Drawing Application.
 * Manages user interactions, shape selection, color picking, drawing logic,
 * grid display, zoom, context menu actions, and command execution.
 */
public class Controller {

    @FXML private Pane root;
    @FXML private ColorPicker borderColorPicker;
    @FXML private ColorPicker fillColorPicker;
    @FXML private ImageView lineImageView;
    @FXML private ImageView rectangleImageView;
    @FXML private ImageView ellipseImageView;
    @FXML private ImageView selectImageView;
    @FXML private ContextMenu contextMenu;
    @FXML private MenuItem MenuItem;
    @FXML private TextField resizeTextField;
    @FXML private CheckBox gridCheckBox;
    @FXML private Slider zoomSlider;
    @FXML private Slider gridSpacingSlider;

    /** Filter for decimal input in the resize text field. */
    private final UnaryOperator<TextFormatter.Change> decimalFilter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("^\\d*([,.]\\d{0,5})?$")) {
            return change;
        }
        return null;
    };

    /** Current border color for new shapes. */
    private Color borderColor = Color.BLACK;
    /** Current fill color for new shapes. */
    private Color fillColor = Color.TRANSPARENT;
    /** Current click mode ("draw" or "select"). */
    private String clickMode = null;
    /** Mouse X coordinate for context menu and paste actions. */
    private double mouseX, mouseY;
    /** Canvas for drawing the grid. */
    private Canvas gridCanvas;
    /** Scale transform for zooming the drawing area. */
    private Scale scaleTransform = new Scale(1, 1);
    /** Current grid spacing. */
    private double gridSpacing = 20; 
    /** Available grid spacing levels. */
    private final double[] GRID_LEVELS = {20, 40, 80};
    /** Index of the current grid spacing level. */
    private int currentGridLevelIndex = 1; 

    /** The application model managing shapes and commands. */
    private Model model;

    /** The stack of executed commands for undo functionality. */
    private Stack<CommandInterface> commandStack;

    /**
     * Initializes the controller.
     * Sets up color pickers, mouse event handlers, grid, zoom, and context menu.
     */
    public void initialize() {
        commandStack = new Stack<CommandInterface>();
        model = new Model(root);
        setupDrawingAreaClip(); 
        setupColorPickers(); 
        root.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleClickMode); 
        setupContextMenu();
        resizeTextField.setTextFormatter(new TextFormatter<>(decimalFilter));
        
        setupGridCanvas();
        ensureGridIsAtBack();
        setupGridToggle();
        setupResizeListeners();
        setupGridSpacingSlider();

        scaleTransform.setPivotX(0);
        scaleTransform.setPivotY(0);
        root.getTransforms().add(scaleTransform);

        // Listener to update zoom
        zoomSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double scale = newVal.doubleValue();
            scaleTransform.setX(scale);
            scaleTransform.setY(scale);

            if (gridCanvas.isVisible()) {
                drawGrid(gridCanvas, gridSpacing);
            }
        });

        // Snap slider to steps of 0.5
        zoomSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double snappedValue = Math.round(newVal.doubleValue() * 2) / 2.0;
            zoomSlider.setValue(snappedValue);
        });
    }

    /**
     * Sets up the slider for grid spacing selection.
     */
    private void setupGridSpacingSlider() {
        gridSpacingSlider.setMin(0);
        gridSpacingSlider.setMax(GRID_LEVELS.length - 1);
        gridSpacingSlider.setMajorTickUnit(1);
        gridSpacingSlider.setMinorTickCount(0);
        gridSpacingSlider.setSnapToTicks(true);
        gridSpacingSlider.setBlockIncrement(1);
        gridSpacingSlider.setValue(currentGridLevelIndex);

        gridSpacingSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            currentGridLevelIndex = newVal.intValue();
            if (gridCanvas.isVisible()) {
                double spacing = GRID_LEVELS[currentGridLevelIndex];
                drawGrid(gridCanvas, spacing);
            }
        });
    }

    /**
     * Initializes the grid canvas and adds it to the drawing area.
     */
    private void setupGridCanvas() {
        gridCanvas = new Canvas(root.getPrefWidth(), root.getPrefHeight());
        gridCanvas.setVisible(false);
        root.getChildren().add(0, gridCanvas); 
        drawGrid(gridCanvas, 20);
    }

    /**
     * Sets up the grid toggle checkbox to show/hide the grid and enable/disable the slider.
     */
    private void setupGridToggle() {
        gridCheckBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            gridCanvas.setVisible(isSelected);
            gridSpacingSlider.setDisable(!isSelected);
        });
    }

    /**
     * Sets up listeners to redraw the grid when the drawing area is resized.
     */
    private void setupResizeListeners() {
        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            gridCanvas.setWidth(newVal.doubleValue());
            drawGrid(gridCanvas, gridSpacing);
        });

        root.heightProperty().addListener((obs, oldVal, newVal) -> {
            gridCanvas.setHeight(newVal.doubleValue());
            drawGrid(gridCanvas, gridSpacing);
        });
    }

    /**
     * Ensures the grid canvas is always at the back of the drawing area.
     */
    private void ensureGridIsAtBack() {
        root.getChildren().remove(gridCanvas);
        root.getChildren().add(0, gridCanvas);
    }

    /**
     * Draws the grid on the specified canvas with the given spacing.
     * @param canvas The canvas to draw the grid on.
     * @param spacing The spacing between grid lines.
     */
    private void drawGrid(Canvas canvas, double baseSpacing) {
        gridSpacing = baseSpacing;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        double scale = scaleTransform.getX(); 

        double adjustedSpacing = baseSpacing * scale;

        if (adjustedSpacing < 4) return; 

        gc.setStroke(Color.GRAY);
        gc.setLineWidth(1);
        gc.setLineDashes(null);

        for (double x = 0; x < canvas.getWidth(); x += adjustedSpacing) {
            double px = Math.round(x) + 0.5;  
            gc.strokeLine(px, 0, px, canvas.getHeight());
        }
        for (double y = 0; y < canvas.getHeight(); y += adjustedSpacing) {
            double py = Math.round(y) + 0.5;
            gc.strokeLine(0, py, canvas.getWidth(), py);
        }
    }

    /**
     * Sets up a clipping rectangle for the drawing area to prevent drawing outside bounds.
     */
    private void setupDrawingAreaClip() {
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(root.widthProperty());
        clip.heightProperty().bind(root.heightProperty());
        root.setClip(clip);
    }

    /**
     * Handles mouse clicks based on the current click mode ("draw" or "select").
     * @param event The mouse event.
     */
    private void handleClickMode (MouseEvent event) {
        if(clickMode == null) {
            return; 
        }else if(clickMode.equals("draw")) {
            drawShape(event);
        }else if (clickMode.equals("select")) {
            shapeSelection(event);
        }
    }

    /**
     * Handles shape selection logic and updates UI accordingly.
     * @param event The mouse event.
     */
    private void shapeSelection(MouseEvent event) {
        contextMenu.hide(); 
        Object target = event.getTarget();
        
        if (target instanceof Shape) {
            Shape clickedShape = (Shape) target;

            if (clickedShape != model.getSelectedShape()) {
                resizeTextField.setDisable(true);
                resizeTextField.setText(""); 
                model.deselectShape();
                model.setSelectedShape(clickedShape);
                model.setSelectedShapeStyle("-fx-effect: dropshadow(three-pass-box, #00bfff, 10, 0, 0, 0);");

                ShapeInterface shapeModel = model.getShapeFromNode(clickedShape);
                if (shapeModel != null) {
                    borderColorPicker.setValue((Color) clickedShape.getStroke());
                    fillColorPicker.setValue((Color) clickedShape.getFill());
                    borderColor = borderColorPicker.getValue();
                    fillColor = fillColorPicker.getValue();
                }
            }
            resizeTextField.setDisable(false);
            enableMoveShape(clickedShape);

        } else {
            resizeTextField.setDisable(true);
            model.deselectShape();
        }
    }

    /**
     * Sets up the context menu for shape actions (delete, cut, copy, paste, bring to front, send to back).
     */
    private void setupContextMenu() {
        contextMenu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(e -> deleteShape());
        MenuItem cutItem = new MenuItem("Cut");
        cutItem.setOnAction(e -> cutShape());
        MenuItem copyItem = new MenuItem("Copy");
        copyItem.setOnAction(e -> copyShape());
        MenuItem pasteItem = new MenuItem("Paste");
        pasteItem.setOnAction(e -> pasteShape());
        MenuItem brtingToFront = new MenuItem("Bring to Front");
        brtingToFront.setOnAction(e -> brtingToFront());
        MenuItem sendToBack = new MenuItem("Send to Back");
        sendToBack.setOnAction(e -> sendToBack());

        contextMenu.getItems().add(deleteItem);
        contextMenu.getItems().add(cutItem);
        contextMenu.getItems().add(copyItem);
        contextMenu.getItems().add(pasteItem);
        contextMenu.getItems().add(brtingToFront);
        contextMenu.getItems().add(sendToBack);
        
        root.setOnContextMenuRequested(event -> {
            if (!"select".equals(clickMode)) {
                return;
            }

            mouseX = event.getX();
            mouseY = event.getY();

            Object target = event.getTarget();
            boolean isTargetSelectedShape = target instanceof Shape && target == model.getSelectedShape();
            deleteItem.setDisable(!isTargetSelectedShape);
            copyItem.setDisable(!isTargetSelectedShape);
            cutItem.setDisable(!isTargetSelectedShape);
            pasteItem.setDisable(model.getClipBoardShape() == null);
            brtingToFront.setDisable(model.isOnTheFront());
            sendToBack.setDisable(model.isOnTheBack());
            contextMenu.setAutoFix(true);

            contextMenu.show(root, event.getScreenX(), event.getScreenY());

            event.consume();
        });
    }

    /**
     * Enables moving a shape by dragging it with the mouse.
     * @param shape The shape to enable movement for.
     */
    private void enableMoveShape(Shape shape) {
        double[] delta = new double[2];
        double[] initialCoords = new double[2];

        shape.setOnMousePressed(event -> {
            initialCoords[0] = shape.getLayoutX();
            initialCoords[1] = shape.getLayoutY();

            delta[0] = event.getSceneX() - shape.getLayoutX();
            delta[1] = event.getSceneY() - shape.getLayoutY();

            event.consume();
        });

        shape.setOnMouseDragged(event -> {
            double x = event.getSceneX() - delta[0];
            double y = event.getSceneY() - delta[1];
            shape.setLayoutX(x);
            shape.setLayoutY(y);

            event.consume();
        });

        shape.setOnMouseReleased(event -> {
            double finalX = shape.getLayoutX();
            double finalY = shape.getLayoutY();
            
            ShapeInterface shapeModel = model.getShapeFromNode(shape);
            if (shapeModel != null) {
                double[] finalCoords = {finalX, finalY};

                if (initialCoords[0] == finalCoords[0] && initialCoords[1] == finalCoords[1]) {
                    return; 
                }
                CommandInterface move = new MoveShapeCommand(model, shapeModel, initialCoords, finalCoords);
                move.execute();
                
            }

            event.consume();
        });
    }

    /**
     * Handles the selection of the Line tool.
     * Sets the current factory to LineFactory and highlights the selected icon.
     */
    @FXML
    private void onSelectLine() {
        model.setCurrentFactory(new LineFactory());
        highlightSelected(lineImageView);
        clickMode = "draw";
        model.deselectShape();
    }

    /**
     * Handles the selection of the Rectangle tool.
     * Sets the current factory to RectangleFactory and highlights the selected icon.
     */
    @FXML
    private void onSelectRectangle() {
        model.setCurrentFactory(new RectangleFactory());
        highlightSelected(rectangleImageView);
        clickMode = "draw";
        model.deselectShape();
    }

    /**
     * Handles the selection of the Ellipse tool.
     * Sets the current factory to EllipseFactory and highlights the selected icon.
     */
    @FXML
    private void onSelectEllipse() {
        model.setCurrentFactory(new EllipseFactory());
        highlightSelected(ellipseImageView);
        clickMode = "draw";
        model.deselectShape();
    }

    /**
     * Highlights the selected shape icon and removes highlight from others.
     * @param selected The ImageView to highlight.
     */
    private void highlightSelected(ImageView selected) {
        lineImageView.setStyle("");
        rectangleImageView.setStyle("");
        ellipseImageView.setStyle("");
        selectImageView.setStyle("");

        selected.setStyle("-fx-effect: dropshadow(three-pass-box, #00bfff, 10, 0, 0, 0);");
    }

    /**
     * Draws a shape at the mouse click position using the current factory and colors.
     * @param event MouseEvent containing the click coordinates.
     */
    private void drawShape(MouseEvent event){
        if (event.getButton() == MouseButton.PRIMARY){
            double x = event.getX();
            double y = event.getY();
        
            CommandInterface command = new DrawShapeCommand(model, x, y, borderColor, fillColor);
            command.execute();
            commandStack.push(command);
        }
        event.consume();
    }
    
    /**
     * Sets up the color pickers for border and fill colors.
     * Updates the controller's color fields on user selection.
     */
    private void setupColorPickers() {
        borderColorPicker.setValue(borderColor);
        fillColorPicker.setValue(fillColor);
        
        borderColorPicker.setOnAction(event -> {
            borderColor = borderColorPicker.getValue();
            updateShapeColorFromPicker();
        });

        fillColorPicker.setOnAction(event -> {
            fillColor = fillColorPicker.getValue();
            updateShapeColorFromPicker();

        });
    }

    /**
     * Updates the color of the selected shape based on the color pickers.
     */
    private void updateShapeColorFromPicker(){
        Node selectedNode = model.getSelectedShape();
        if(selectedNode instanceof Shape) {
            ShapeInterface shape = model.getShapeFromNode((Shape) selectedNode);
            if (shape != null) {
                CommandInterface command = new ChangeShapeColorCommand(model, shape, borderColor, fillColor);
                command.execute();
                commandStack.push(command);
            }
        }
    }

    /**
     * Deletes the currently selected shape using a DeleteShapeCommand.
     */
    private void deleteShape(){
        Node selectedNode = model.getSelectedShape();
        if(selectedNode instanceof Shape) {
            ShapeInterface shape = model.getShapeFromNode((Shape) selectedNode);
            if (shape != null){
                CommandInterface command = new DeleteShapeCommand(model, shape );
                command.execute();
                commandStack.push(command);
            }
        }
    }

    /**
     * Cuts the currently selected shape using a CutShapeCommand.
     */
    private void cutShape() {
        Node selectedNode = model.getSelectedShape();
        if (selectedNode instanceof Shape) {
            ShapeInterface shape = model.getShapeFromNode((Shape) selectedNode);
            if (shape != null) {
                CommandInterface command = new CutShapeCommand(model, shape);
                command.execute();
                commandStack.push(command);
            }
        }
    }

    /**
     * Copies the currently selected shape using a CopyShapeCommand.
     */
    private void copyShape() {
        Node selectedNode = model.getSelectedShape();
        if (selectedNode instanceof Shape) {
            ShapeInterface shape = model.getShapeFromNode((Shape) selectedNode);
            if (shape != null) {
                CommandInterface command = new CopyShapeCommand(model, shape);
                command.execute();
            }
        }
    }

    /**
     * Pastes the shape from the clipboard at the last right-click position.
     */
    private void pasteShape(){
        ShapeInterface shapeToPaste = model.getClipBoardShape();
        if(shapeToPaste == null) { return; }
        CommandInterface command = new PasteShapeCommand(model, mouseX, mouseY);
        command.execute();
        commandStack.push(command);
    }

    /**
     * Handles the resize button click event.
     * Applies resizing to the selected shape.
     */
    @FXML
    private void onResizeButtonClick() {
        if(model.getSelectedShape() == null) {
            return;
        }
        applyResize();
    }

    /**
     * Applies resizing to the selected shape using the value from the text field.
     */
    private void applyResize(){
        String text = resizeTextField.getText();   
        if (text.isEmpty()) { return; }

        try {
            double scale = Double.parseDouble(text.replace(',', '.'));

            if (scale <= 0){ return; }
            ShapeInterface shapeToResize = (ShapeInterface) model.getSelectedShape().getUserData();
            CommandInterface command = new ResizeShapeCommand(model, shapeToResize, scale);
            command.execute();
            commandStack.push(command);
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for resize: " + text);
        }
    }

    /**
     * Brings the selected shape to the front using a BringToFrontCommand.
     */
    public void brtingToFront() {
        Node selectNode = model.getSelectedShape();
        if (selectNode instanceof Shape) {
            ShapeInterface shape = model.getShapeFromNode((Shape) selectNode);
            if (shape != null) {
                CommandInterface command = new BringToFrontCommand(model, shape);
                command.execute();
                commandStack.push(command);
                ensureGridIsAtBack();
            }
        }

    }

    /**
     * Sends the selected shape to the back using a SendToBackCommand.
     */
    public void sendToBack() {
        Node selectNode = model.getSelectedShape();
        if (selectNode instanceof Shape) {
            ShapeInterface shape = model.getShapeFromNode((Shape) selectNode);
            if (shape != null) {
                CommandInterface command = new SendToBackCommand(model, shape);
                command.execute();
                commandStack.push(command);
                ensureGridIsAtBack();
            }
        }
    }

    /**
     * Handles the Save button click event.
     * Executes the SaveCommand to persist the current shapes.
     */
    @FXML
    private void onSaveButtonClick() {
        CommandInterface command = new SaveCommand(model);
        command.execute();
    }

    /**
     * Handles the Load button click event.
     * Executes the LoadCommand to load shapes into the pane.
     */
    @FXML
    private void onLoadButtonClick() {
        CommandInterface command = new LoadCommand(model);
        command.execute();

        ensureGridIsAtBack();
        gridCheckBox.setSelected(false);
        commandStack.clear();
    }

    /**
     * Handles the Select tool button click event.
     * Switches to selection mode and highlights the select icon.
     */
    @FXML
    private void onSelectButtonClick() {
        clickMode = "select";
        highlightSelected(selectImageView);
    }

    /**
     * Handles the Undo button click event.
     * Executes the UndoCommand to undo the last action.
     */
    @FXML
    private void onUndoButtonClick() {
        if(commandStack.isEmpty()) { return; }
        CommandInterface command = commandStack.pop();
        command.undo();
        ensureGridIsAtBack();
    }
}
