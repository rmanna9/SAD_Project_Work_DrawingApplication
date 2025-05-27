package com.sad.models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

/**
 * Model class for the Drawing Application.
 * Manages the current shape factory, the drawing pane, clipboard, command stack,
 * and provides methods for creating, manipulating, serializing, and deserializing shapes.
 */
public class Model {
    /** The current shape factory used to create shapes. */
    private ShapeFactory currentFactory;
    /** The drawing pane where shapes are displayed. */
    private Pane pane;
    /** The currently selected shape node. */
    private Node selectedShape = null;
    /** The clipboard shape for cut/copy/paste operations. */
    private ShapeInterface clipBoardShape = null;

    /**
     * Constructs a new Model instance.
     * Initializes the shapes list and sets the current factory and command to null.
     * @param pane The drawing pane where shapes will be managed.
     */
    public Model(Pane pane) {
        this.currentFactory = null;
        this.pane = pane;
    }

    /**
     * Gets the shape currently stored in the clipboard.
     * @return The clipboard shape.
     */
    public ShapeInterface getClipBoardShape() {
        return clipBoardShape;
    }

    /**
     * Sets the clipboard shape.
     * @param clipBoardShape The shape to store in the clipboard.
     */
    public void setClipBoardShape(ShapeInterface clipBoardShape) {
        this.clipBoardShape = clipBoardShape;
    }

    /**
     * Gets the drawing pane.
     * @return The drawing pane.
     */
    public Pane getPane() { return pane; }
    
    /**
     * Gets the currently selected shape node.
     * @return The selected shape node.
     */
    public Node getSelectedShape() { return selectedShape; }

    /**
     * Sets the currently selected shape node.
     * @param selectedShape The node to select.
     */
    public void setSelectedShape(Node selectedShape) {
        this.selectedShape = selectedShape;
    }

    /**
     * Sets the style of the currently selected shape.
     * @param style The CSS style string.
     */
    public void setSelectedShapeStyle(String style) {
        if(selectedShape != null){
            selectedShape.setStyle(style);
        }
    }

    /**
     * Deselects the currently selected shape and removes its event handlers.
     */
    public void deselectShape() {
        if(selectedShape != null){
            selectedShape.setStyle("");
            selectedShape.setOnMousePressed(null);
            selectedShape.setOnMouseDragged(null);
            selectedShape.setOnMouseReleased(null);
            selectedShape = null;
        }
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
    public ShapeFactory getCurrentFactory() { return currentFactory; }

    /**
     * Creates a new shape using the current factory and adds it to the pane.
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
            Node node = shape.draw();
            pane.getChildren().add(node);
            return shape;
        }
        return null;
    }

    /**
     * Saves all shapes in the pane to a text file using a file chooser.
     * Serializes each shape to a string and writes it to the file.
     */
    public void save() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Shapes");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
        File file = fileChooser.showSaveDialog(pane.getScene().getWindow());

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Node node : pane.getChildren()) {
                    if (node.getUserData() instanceof ShapeInterface) {
                        ShapeInterface shape = (ShapeInterface) node.getUserData();
                        // Serialize the shape and write to file
                        String line = serializeShape(shape);
                        if (line != null) {
                            writer.write(line);
                            writer.newLine();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Serializes a shape into a string representation.
     * @param shape The shape to serialize.
     * @return The string representation of the shape, or null if the shape type is unsupported.
     */
    public String serializeShape(ShapeInterface shape) {
        if (shape instanceof ConcreteRectangle) {
            ConcreteRectangle rectangle = (ConcreteRectangle) shape;
            return String.format("Rectangle %f %f %f %f %s %s",
                    rectangle.getX(), rectangle.getY(),
                    rectangle.getWidth(), rectangle.getHeight(),
                    colorToString(rectangle.getBorderColor()),
                    colorToString(rectangle.getFillColor()));
        } else if (shape instanceof ConcreteEllipse) {
            ConcreteEllipse ellipse = (ConcreteEllipse) shape;
            return String.format("Ellipse %f %f %f %f %s %s",
                    ellipse.getX(), ellipse.getY(),
                    ellipse.getWidth(), ellipse.getHeight(),
                    colorToString(ellipse.getBorderColor()),
                    colorToString(ellipse.getFillColor()));
        } else if (shape instanceof ConcreteLine){
            ConcreteLine line = (ConcreteLine) shape;
            return String.format("Line %f %f %f %f %s",
                    line.getX(), line.getY(),
                    line.getX2(), line.getY2(),
                    colorToString(line.getBorderColor()));
        } else {
            return null;
        }
    }

    /**
     * Converts a JavaFX Color object to a string representation.
     * @param color The color to convert.
     * @return The string representation of the color (hex or "transparent").
     */
    private String colorToString(Color color) {
        if (color.getOpacity() == 0.0) {
            return "transparent";
        }
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    /**
     * Loads shapes from a text file using a file chooser.
     * Deserializes each line into a shape and adds it to the pane.
     * Clears the command stack and resets selection and factory.
     */
    public void load(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Shapes");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());

        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                pane.getChildren().clear();

                while ((line = reader.readLine()) != null) {
                    ShapeInterface shape = deserializeShape(line);
                    if(shape != null){
                        Node node = shape.draw();
                        if(node != null){
                            pane.getChildren().add(node);
                        }
                    }
                }

                this.selectedShape = null;
                this.currentFactory = null;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deserializes a shape from a string line.
     * @param line The string representing the shape.
     * @return The deserialized ShapeInterface object, or null if parsing fails.
     */
    public ShapeInterface deserializeShape(String line) {
        String[] parts = line.split(" ");
        String type = parts[0];

        try {
            switch (type) {
                case "Rectangle":
                    double xR = Double.parseDouble(parts[1].replace(",", "."));
                    double yR = Double.parseDouble(parts[2].replace(",", "."));
                    double wR = Double.parseDouble(parts[3].replace(",", "."));
                    double hR = Double.parseDouble(parts[4].replace(",", "."));
                    Color borderR = stringToColor(parts[5]);
                    Color fillR = stringToColor(parts[6]);
                    return new ConcreteRectangle(xR, yR, wR, hR, borderR, fillR);

                case "Ellipse":
                    double xE = Double.parseDouble(parts[1].replace(",", "."));
                    double yE = Double.parseDouble(parts[2].replace(",", "."));
                    double wE = Double.parseDouble(parts[3].replace(",", "."));
                    double hE = Double.parseDouble(parts[4].replace(",", "."));
                    Color borderE = stringToColor(parts[5]);
                    Color fillE = stringToColor(parts[6]);
                    return new ConcreteEllipse(xE, yE, wE, hE, borderE, fillE);

                case "Line":
                    double x1 = Double.parseDouble(parts[1].replace(",", "."));
                    double y1 = Double.parseDouble(parts[2].replace(",", "."));
                    double x2 = Double.parseDouble(parts[3].replace(",", "."));
                    double y2 = Double.parseDouble(parts[4].replace(",", "."));
                    Color stroke = stringToColor(parts[5]);
                    return new ConcreteLine(x1, y1, x2, y2, stroke);

                default:
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Converts a string to a JavaFX Color object.
     * @param str The string representing the color.
     * @return The corresponding Color object.
     */
    private Color stringToColor(String str) {
        if (str.equalsIgnoreCase("transparent")) {
            return Color.TRANSPARENT;
        }
        return Color.web(str);
    }

    /**
     * Gets the ShapeInterface object associated with a given node.
     * @param node The node to query.
     * @return The associated ShapeInterface, or null if not found.
     */
    public ShapeInterface getShapeFromNode(Node node) {
        if (node != null && node.getUserData() instanceof ShapeInterface) {
            return (ShapeInterface) node.getUserData();
        }
        return null;
    }

    /**
     * Moves a shape to a new position.
     * @param shape The shape to move.
     * @param newPosition The new coordinates [x, y].
     */
    public void moveShape(ShapeInterface shape, double[] newPosition){
        shape.setX(newPosition[0]);
        shape.setY(newPosition[1]);
        
        Node node = shape.getNode();
        if(node != null){
            node.setLayoutX(newPosition[0]);
            node.setLayoutY(newPosition[1 ]);
        }
    }

    /**
     * Changes the border and fill color of a shape.
     * @param shape The shape to modify.
     * @param newBorderColor The new border color.
     * @param newFillColor The new fill color.
     */
    public void changeShapeColor(ShapeInterface shape, Color newBorderColor, Color newFillColor) {
        if (shape != null) {
            shape.setBorderColor(newBorderColor);
            shape.setFillColor(newFillColor);
        }   
    }

    /**
     * Deletes a shape from the pane and resets its properties.
     * @param shape The shape to delete.
     */
    public void deleteShape(ShapeInterface shape){
        if(shape != null){
            shape.setX(0);
            shape.setY(0);
            shape.setBorderColor(null);
            shape.setFillColor(null);
            Node node = shape.getNode();
            pane.getChildren().remove(node);
        }

    }

    /**
     * Cuts a shape from the pane and stores a copy in the clipboard.
     * @param shape The shape to cut.
     */
    public void cutShape( ShapeInterface shape){
        if (shape != null) {
            setClipBoardShape(shape.clone());
            shape.setX(0);
            shape.setY(0);
            shape.setBorderColor(null);
            shape.setFillColor(null);
            Node node = shape.getNode();
            pane.getChildren().remove(node);
        }
    }

    /**
     * Copies a shape to the clipboard.
     * @param shape The shape to copy.
     */
    public void copyShape(ShapeInterface shape){
        if (shape != null) {
            setClipBoardShape(shape.clone());
        }
    }

    /**
     * Pastes the shape from the clipboard at the specified coordinates.
     * @param x The x-coordinate for the pasted shape.
     * @param y The y-coordinate for the pasted shape.
     * @return The pasted ShapeInterface object.
     */
    public ShapeInterface pasteShape(double x, double y) {
        ShapeInterface copiedShape = getClipBoardShape().clone();
        if (copiedShape !=null){
            copiedShape.setX(x);
            copiedShape.setY(y);
            Node node = copiedShape.draw();
            pane.getChildren().add(node);
        }
        return copiedShape;
    }

    /**
     * Resizes a shape to the specified width and height.
     * @param shape The shape to resize.
     * @param newWidth The new width.
     * @param newHeight The new height.
     */
    public void resizeShape(ShapeInterface shape, double newWidth, double newHeight) {
        if (shape != null) {
            shape.setWidth(newWidth);
            if (!(shape.getShapeFactory() instanceof LineFactory)) {
                shape.setHeight(newHeight);
            }
            
        }
    }

    /**
     * Brings a shape to the front of the pane.
     * @param shape The shape to bring to the front.
     */
    public void bringShapeToFront(ShapeInterface shape) {
        if (shape != null && pane.getChildren().contains(shape.getNode())) {
            shape.getNode().toFront();
        }
    }

    /**
     * Checks if the selected shape is at the front of the pane.
     * @return True if the selected shape is at the front, false otherwise.
     */
    public boolean isOnTheFront() {
        if (selectedShape != null && pane.getChildren().contains(selectedShape)) {
            return selectedShape.equals(pane.getChildren().get(pane.getChildren().size() - 1));
        }
        return false;
    }

    /**
     * Checks if the selected shape is at the back of the pane.
     * @return True if the selected shape is at the back, false otherwise.
     */
    public boolean isOnTheBack() {
        if (selectedShape != null && pane.getChildren().contains(selectedShape)) {
            return pane.getChildren().indexOf(selectedShape) == 1;
        }
        return false;
    }

    /**
     * Sends a shape to the back of the pane.
     * @param shape The shape to send to the back.
     */
    public void sendShapeToBack(ShapeInterface shape) {
        if (shape != null && pane.getChildren().contains(shape.getNode())) {
            shape.getNode().toBack();
        }
    }

    /**
     * Adds a shape to the pane.
     * @param shape The shape to add.
     */
    public void addShape(ShapeInterface shape) {
        if (shape != null) {
            Node node = shape.getNode();
            pane.getChildren().add(node);
        }
    }

}
