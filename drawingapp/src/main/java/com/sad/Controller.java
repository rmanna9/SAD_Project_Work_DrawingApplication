package com.sad;

import javafx.fxml.FXML;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
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
import com.sad.models.shapes.ConcreteText;
import com.sad.models.shapes.ShapeInterface;
import com.sad.models.state.DrawingShapeState;
import com.sad.models.state.PolygonDrawingState;
import com.sad.models.state.SelectingState;
import com.sad.models.state.StateInterface;
import com.sad.models.state.TextInsertState;

/**
 * Controller class for the Drawing Application.
 * Manages user interactions, shape selection, color picking, drawing logic,
 * grid display, zoom, context menu actions, and command execution.
 */
public class Controller {

    /** Root pane containing all UI elements */
    @FXML private Pane root;
    
    /** Color picker for selecting border color */
    @FXML private ColorPicker borderColorPicker;
    
    /** Color picker for selecting fill color */
    @FXML private ColorPicker fillColorPicker;
    
    /** ImageView for line tool selection */
    @FXML private ImageView lineImageView;
    
    /** ImageView for rectangle tool selection */
    @FXML private ImageView rectangleImageView;
    
    /** ImageView for ellipse tool selection */
    @FXML private ImageView ellipseImageView;
    
    /** ImageView for polygon tool selection */
    @FXML private ImageView polygonImageView;
    
    /** ImageView for selection tool */
    @FXML private ImageView selectImageView;
    
    /** ImageView for text tool selection */
    @FXML private ImageView textImageView;
    
    /** Context menu for shape operations */
    @FXML private ContextMenu contextMenu;
    
    /** Menu item in context menu */
    @FXML private MenuItem MenuItem;
    
    /** Text field for resize input */
    @FXML private TextField resizeTextField;
    
    /** Text field for text input */
    @FXML private TextField textInput;
    
    /** Text field for horizontal stretch input */
    @FXML private TextField stretchXTextField;
    
    /** Text field for vertical stretch input */
    @FXML private TextField stretchYTextField;
    
    /** Text field for rotation angle input */
    @FXML private TextField angleTextField;
    
    /** Checkbox to toggle grid visibility */
    @FXML private CheckBox gridCheckBox;
    
    /** Slider for zoom level adjustment */
    @FXML private Slider zoomSlider;
    
    /** Slider for grid spacing adjustment */
    @FXML private Slider gridSpacingSlider;
    
    /** Combo box for font size selection */
    @FXML private ComboBox<Integer> fontSizeMenu;

    /** Filter for decimal input in the resize text field. */
    private final UnaryOperator<TextFormatter.Change> decimalFilter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("^\\d*([,.]\\d{0,5})?$")) {
            return change;
        }
        return null;
    };

    /** Mouse X coordinate for context menu and paste actions. */
    private double mouseX, mouseY;
    
    /** Canvas for drawing the grid. */
    private Canvas gridCanvas;
    
    /** Scale transform for zooming the drawing area. */
    private Scale scaleTransform = new Scale(1, 1);
    
    /** Current grid spacing. */
    private double gridSpacing = 20; 
    
    /** Available grid spacing levels. */
    private final double[] GRID_LEVELS = {40, 80, 160};
    
    /** Index of the current grid spacing level. */
    private int currentGridLevelIndex = 0; 

    /** The application model managing shapes and commands. */
    private Model model;

    /** The stack of executed commands for undo functionality. */
    private Stack<CommandInterface> commandStack;

    /** State for selecting shapes */
    private SelectingState selectingState;
    
    /** State for drawing basic shapes */
    private DrawingShapeState drawingShapeState;
    
    /** State for drawing polygons */
    private PolygonDrawingState drawingPolygonState;
    
    /** State for inserting text */
    private TextInsertState insertTextState;
    
    /** Current application state */
    private StateInterface currentState; 
    
    /** Previous font size for change detection */
    private int oldFontSize;


    /**
     * Initializes the controller.
     * Sets up color pickers, mouse event handlers, grid, zoom, and context menu.
     */
    public void initialize() {
        highlightSelected(selectImageView);
        commandStack = new Stack<CommandInterface>();
        model = new Model(root);
        setupDrawingAreaClip(); 
        setupColorPickers(); 
        resizeTextField.setTextFormatter(new TextFormatter<>(decimalFilter));
        angleTextField.setTextFormatter(new TextFormatter<>(decimalFilter));
        stretchXTextField.setTextFormatter(new TextFormatter<>(decimalFilter));
        stretchYTextField.setTextFormatter(new TextFormatter<>(decimalFilter));

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

        textInput.setDisable(true);
        textInput.textProperty().addListener((obs, oldVal, newVal) -> {
            if (currentState instanceof TextInsertState) {
                ConcreteText currentText = ((TextInsertState) currentState).getCurrentText();
                if (currentText != null) {
                    currentText.setContent(newVal);
                }
            }
        });

        selectingState = new SelectingState(this, model);
        drawingShapeState = new DrawingShapeState(this, model, borderColorPicker, fillColorPicker);
        drawingPolygonState = new PolygonDrawingState(this, model, borderColorPicker, fillColorPicker);
        insertTextState = new TextInsertState(this, model);
        currentState = selectingState;

        initializeSizeComboBox();

        fontSizeMenu.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            if (isFocused) {
                oldFontSize = fontSizeMenu.getValue();
            } 
        });

        fontSizeMenu.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) return;

            if (newVal != oldFontSize) {
                applyFontSizeChange(newVal);
                oldFontSize = newVal;
            }
        });

    }

    /**
     * Sets the current application state.
     * @param newState The state to transition to
     */
    private void setCurrentState(StateInterface newState){
        if (this.currentState != null) {
            this.currentState.onExit(); 
        }
        this.currentState = newState;
    }

    /**
     * Gets the current application state.
     * @return Current state object
     */
    private StateInterface getCurrentState(){
        return this.currentState;
    }

    /**
     * Gets the root pane.
     * @return Root pane instance
     */
    public Pane getRoot(){
        return this.root;
    }

    /**
     * Gets the context menu.
     * @return Context menu instance
     */
    public ContextMenu getContextMenu(){
        return this.contextMenu;
    }

    /**
     * Gets the resize text field.
     * @return Resize text field
     */
    public TextField getResizeTexField(){
        return this.resizeTextField;
    }

    public TextField getStretchXField(){
        return this.stretchXTextField;
    }

    public TextField getStretchYField(){
        return this.stretchYTextField;
    }

    /**
     * Gets the text input field.
     * @return Text input field
     */
    public TextField getTextInputField(){
        return this.textInput;
    }

    /**
     * Gets the border color picker.
     * @return Border color picker
     */
    public ColorPicker getBordColorPicker() {
        return this.borderColorPicker;
    }

    /**
     * Gets the fill color picker.
     * @return Fill color picker
     */
    public ColorPicker getFillColorPicker() {
        return this.fillColorPicker;
    }

    /**
     * Gets the font size combo box.
     * @return Font size selection combo box
     */
    public ComboBox<Integer> getFontSizeMenuButton(){
        return fontSizeMenu;
    }

    /**
     * Sets mouse X coordinate for paste operations.
     * @param mouseX X coordinate value
     */
    public void setMouseX(double mouseX){
        this.mouseX = mouseX;
    }

    /**
     * Sets mouse Y coordinate for paste operations.
     * @param mouseY Y coordinate value
     */
    public void setMouseY(double mouseY){
        this.mouseY = mouseY;
    }

    /**
     * Applies font size change to selected text shape.
     * @param newFontSize New font size value
     */
    private void applyFontSizeChange(double newFontSize) {
        ShapeInterface selectedShape = model.getShapeFromNode(model.getSelectedShape());
        if (selectedShape instanceof ConcreteText) {
            ConcreteText textShape = (ConcreteText) selectedShape;
            double oldSize = textShape.getFontSize();

            if (newFontSize != oldSize) {
                CommandInterface cmd = new EditFontSizeCommand(model, textShape, newFontSize);
                executeCommand(cmd);
            }
        }
    }

    /**
     * Attaches keyboard handler to the scene.
     * @param scene JavaFX scene to attach handler to
     */
    public void attachKeyHandlerToScene(Scene scene) {
        scene.setOnKeyPressed(this::onKeyPressed);
    }

    /**
     * Initializes font size combo box with default values.
     */
    private void initializeSizeComboBox(){
        fontSizeMenu.getItems().addAll(12, 14, 16 , 18, 20, 24, 28, 32);
        fontSizeMenu.setValue(20);
        fontSizeMenu.setDisable(true);
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
        gridSpacingSlider.setDisable(!gridCanvas.isVisible());

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
     * @param canvas The canvas to draw the grid on
     * @param baseSpacing The base spacing between grid lines
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
     * Highlights the selected shape icon and removes highlight from others.
     * @param selected The ImageView to highlight
     */
    private void highlightSelected(ImageView selected) {
        lineImageView.setStyle("");
        rectangleImageView.setStyle("");
        ellipseImageView.setStyle("");
        selectImageView.setStyle("");
        polygonImageView.setStyle("");
        textImageView.setStyle("");

        selected.setStyle("-fx-effect: dropshadow(three-pass-box, #00bfff, 10, 0, 0, 0);");
    }
    
    /**
     * Sets up the color pickers for border and fill colors.
     * Updates the controller's color fields on user selection.
     */
    private void setupColorPickers() {
        borderColorPicker.setValue(Color.BLACK);
        fillColorPicker.setValue(Color.TRANSPARENT);
        
        borderColorPicker.setOnAction(event -> {
            updateShapeColorFromPicker();
        });

        fillColorPicker.setOnAction(event -> {
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
                CommandInterface command = new ChangeShapeColorCommand(model, shape, borderColorPicker.getValue(), fillColorPicker.getValue());
                command.execute();
                commandStack.push(command);
            }
        }
    }

    /**
     * Handles mouse click events.
     * @param event Mouse event object
     */
    @FXML
    private void leftMouseClick(MouseEvent event){
        getCurrentState().handleOnMouseClick(event);
    }

    /**
     * Handles context menu requests.
     * @param event Context menu event object
     */
    @FXML
    private void onContextMenuRequest(ContextMenuEvent event){
        getCurrentState().handleOnContextMenuRequest(event);
    }

    /**
     * Handles key press events.
     * @param event Key event object
     */
    private void onKeyPressed(KeyEvent event){
        getCurrentState().onKeyPressed(event);
    }

    /**
     * Handles the selection of the Line tool.
     * Sets the current factory to LineFactory and highlights the selected icon.
     */
    @FXML
    private void onSelectLine() {
        model.setCurrentFactory(new LineFactory());
        highlightSelected(lineImageView);
        model.deselectShape();
        setCurrentState(drawingShapeState);
    }

    /**
     * Handles the selection of the Rectangle tool.
     * Sets the current factory to RectangleFactory and highlights the selected icon.
     */
    @FXML
    private void onSelectRectangle() {
        model.setCurrentFactory(new RectangleFactory());
        highlightSelected(rectangleImageView);
        model.deselectShape();
        setCurrentState(drawingShapeState);
        fillColorPicker.setValue(Color.TRANSPARENT);
    }

    /**
     * Handles the selection of the Ellipse tool.
     * Sets the current factory to EllipseFactory and highlights the selected icon.
     */
    @FXML
    private void onSelectEllipse() {
        model.setCurrentFactory(new EllipseFactory());
        highlightSelected(ellipseImageView);
        model.deselectShape();
        setCurrentState(drawingShapeState);
        fillColorPicker.setValue(Color.TRANSPARENT);

    }

    /**
     * Handles the selection of the Polygon tool.
     * Highlights the polygon icon and transitions to polygon drawing state.
     */
    @FXML
    private void onSelectPolygon(){
        highlightSelected(polygonImageView);
        model.deselectShape();
        setCurrentState(drawingPolygonState);
        fillColorPicker.setValue(Color.TRANSPARENT);
    }

    /**
     * Handles the selection of the Text tool.
     * Enables font controls, highlights text icon, and transitions to text state.
     */
    @FXML
    private void onSelectText(){
        fontSizeMenu.setDisable(false);
        resizeTextField.setDisable(true);
        highlightSelected(textImageView);
        model.deselectShape();
        setCurrentState(insertTextState); 
        fillColorPicker.setValue(Color.TRANSPARENT);

    }

    /**
     * Deletes the currently selected shape using a DeleteShapeCommand.
     */
    @FXML
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
    @FXML
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
    @FXML
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
    @FXML
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
        String text = resizeTextField.getText();   
        if (text.isEmpty()) { return; }

        try {
            double scale = Double.parseDouble(text.replace(',', '.'));

            if (scale <= 0){ return; }
            if (scale >= 25) {scale = 25;}
            resizeTextField.setText(String.valueOf(scale));
            ShapeInterface shapeToResize = (ShapeInterface) model.getSelectedShape().getUserData();
            CommandInterface command = new ResizeShapeCommand(model, shapeToResize, scale);
            executeCommand(command);
            System.out.println(shapeToResize.getWidth());
            System.out.println(shapeToResize.getHeight());    
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for resize: " + text);
        }
    }

    /**
     * Handles text input confirmation.
     * Either creates new text or updates existing text based on current state.
     */
    @FXML
    private void onTextInputButtonClick(){
        if (currentState instanceof TextInsertState) {
            ConcreteText currentText = insertTextState.getCurrentText();
            if (currentText != null) {
                ShapeInterface shape = currentText.clone();
                shape.draw();
                executeCommand(new InsertTextCommand(model, shape));
                root.getChildren().remove(currentText.getNode());
                insertTextState.clearCurrentText();
                fontSizeMenu.setDisable(true);

            }
        } else {
            ShapeInterface selected = model.getShapeFromNode(model.getSelectedShape());
            if (selected instanceof ConcreteText) {
                ConcreteText textShape = (ConcreteText) selected;
                String oldText = textShape.getContent();
                String newText = textInput.getText();

                if (!newText.equals(oldText)) {
                    CommandInterface cmd = new EditTextCommand(model, textShape, oldText, newText);
                    executeCommand(cmd);
                }
            }
        }
        textInput.setDisable(true);
        textInput.clear();
    }    
    
    /**
     * Applies horizontal stretching to selected shape.
     */
    @FXML
    private void onStretchXButtonClick() {
        if(model.getSelectedShape() == null) {
            return;
        }
        String textX = stretchXTextField.getText(); 
        if (textX.isEmpty()) { return; }

        try {
            double scaleX = Double.parseDouble(textX.replace(',', '.'));
            double scaleY = 1;

            if (scaleX <= 0){ return; }
            if (scaleX >= 25){ scaleX = 25; }
            stretchXTextField.setText(String.valueOf(scaleX));
            ShapeInterface shapeToResize = (ShapeInterface) model.getSelectedShape().getUserData();
            CommandInterface command = new StretchShapeCommand(model, shapeToResize, scaleX, scaleY);
            executeCommand(command);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for stretch: ");
        }
    }

    /**
     * Applies vertical stretching to selected shape.
     */
    @FXML
    private void onStretchYButtonClick() {
        if(model.getSelectedShape() == null) {
            return;
        }
        String textY = stretchYTextField.getText(); 
        if (textY.isEmpty()) { return; }

        try {
            double scaleY = Double.parseDouble(textY.replace(',', '.'));
            double scaleX = 1;

            if (scaleY <= 0){ return; }
            if (scaleY >= 25){ scaleY = 25; }
            stretchYTextField.setText(String.valueOf(scaleY));
            ShapeInterface shapeToResize = (ShapeInterface) model.getSelectedShape().getUserData();
            CommandInterface command = new StretchShapeCommand(model, shapeToResize, scaleX, scaleY);
            executeCommand(command);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for stretch: ");
        }
    }
	
    /**
     * Applies rotation to selected shape.
     */
    @FXML
    public void onRotateButtonClick(){
        if(model.getSelectedShape() == null) {
            return;
        }
        String angle = angleTextField.getText(); 
        if (angle.isEmpty()) { return; }

        try {
            double angleR = Double.parseDouble(angleTextField.getText());

            if (angleR <= 0){ return; }
            ShapeInterface shapeToResize = (ShapeInterface) model.getSelectedShape().getUserData();
            CommandInterface command = new RotateShapeCommand(model, shapeToResize, angleR);
            executeCommand(command);
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for stretch: ");
        }
    }

    /**
     * Mirrors selected shape horizontally.
     */
    @FXML
    public void mirrorXShape(){
        Node selectedNode = model.getSelectedShape();
        if(selectedNode instanceof Shape) {
            ShapeInterface shape = model.getShapeFromNode((Shape) selectedNode);
            if (shape != null){
                CommandInterface command = new MirrorXShapeCommand(model, shape );
                command.execute();
                commandStack.push(command);
            }
        }
    }

    /**
     * Mirrors selected shape vertically.
     */
    @FXML
    public void mirrorYShape(){
        Node selectedNode = model.getSelectedShape();
        if(selectedNode instanceof Shape) {
            ShapeInterface shape = model.getShapeFromNode((Shape) selectedNode);
            if (shape != null){
                CommandInterface command = new MirrorYShapeCommand(model, shape );
                command.execute();
                commandStack.push(command);
            }
        }
    }

    /**
     * Brings the selected shape to the front using a BringToFrontCommand.
     */
    @FXML
    public void bringToFront() {
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
    @FXML
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
        highlightSelected(selectImageView);
        setCurrentState(selectingState);
    }

    /**
     * Executes a command and adds it to the command stack.
     * @param command Command to execute
     */
    public void executeCommand(CommandInterface command){
        command.execute();
        commandStack.push(command);
    }

    /**
     * Handles the Undo button click event.
     * Executes the UndoCommand to undo the last action.
     */
    @FXML
    private void onUndoButtonClick() {
        if(commandStack.isEmpty()) { return; }
        currentState.onExit();
        CommandInterface command = commandStack.pop();
        command.undo();
        ensureGridIsAtBack();
    }
}